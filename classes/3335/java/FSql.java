import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 *  A swing based sql executer. It uses the jdbc-odbc bridge to access
 *  the datasource. The odbc source is specified as the first parameter. It
 *  supports SQL DML statements like "select", "insert", "update", "delete",
 *  SQL DDL statements like "create table", "drop table" and also a "show schema
 *   table TABLENAME" command to show table metadata and a "show schema" command
 *   to show database schema. <p>
 *
 *  <b>Note:</b> in the command line, use ' instead of " to present strings.
 */
public class FSQL extends JFrame {
  private Connection connection;   // the jdbc connection object
  private Statement statement; // sql statement
  private ResultSet resultSet; // result set from the sql statement
  private ResultSetMetaData rsMetaData; // metadata from the result set
  private JScrollPane queryResult = new JScrollPane(); // the place to show the result
  private JTextArea inputQuery; // text area for input
  private JButton submit; // the submit button

  // load jdbc-odbc driver at the class load time
  {
    try {
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    } catch (ClassNotFoundException e) {
      System.err.println("Failed to load JDBC/ODBC driver.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   *  Constructor for the FSQL object
   *
   *@param  driverName  Name of the jdbc-odbc driver
   */
  public FSQL(String driverName) {
    super("Use \"show schema\" to show database schema");

    String url = "jdbc:odbc:" + driverName;

    // Load the driver to allow connection to the database
    try {
      connection = DriverManager.getConnection(url);
    } catch (SQLException e1) {
      System.err.println("Unable to connect");
      e1.printStackTrace();
      System.exit(1); // terminate program
    }

    // handle the close window event inside our program
    addWindowListener(
      new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          shutDown(); // close the connetion
          System.exit(0);
        }
      }
    );

    // If connected to database, set up GUI
    inputQuery = new JTextArea("", 4, 30);
    submit = new JButton("Submit");
    submit.addActionListener( // do the sql statement when the submit button is clicked
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          getTable();
        }
      }
        );

    // topPanel contains the input text area and the submit button
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new BorderLayout());
    topPanel.add(new JScrollPane(inputQuery), BorderLayout.CENTER);
    topPanel.add(submit, BorderLayout.SOUTH);

    // set the topPanel in NORTH and the sql result in CENTER
    Container c = getContentPane();
    c.setLayout(new BorderLayout());
    c.add(topPanel, BorderLayout.NORTH);
    c.add(queryResult, BorderLayout.CENTER);

    setSize(700, 500);
    show();
  }

  /**
   *  Close the database connection
   */
  public void shutDown() {
    try {
      connection.close();
    } catch (SQLException sqlex) {
      popUpErrorMsg("Unable to disconnect");
      sqlex.printStackTrace();
      System.exit(1);
    }
  }

  /**
   *  Get the metadata for the table and display them
   *
   *@param  tablename         The table of which the schema is going to be shown
   */
  public void showSchema(String tablename) throws SQLException {
    // Get DatabaseMetaData
    DatabaseMetaData dbmd = connection.getMetaData();

    Vector columnHeads = new Vector(); // field names of the table
    Vector row = new Vector(); // content to be shown as a row
    Vector rows = new Vector(); // vector of row, in order to initialize JTable,
                                // it has to be a vector of vector

    // get the specific result set for the table
    ResultSet rs = dbmd.getColumns(null, null, tablename, null);

    // prepare the table metadata
    while (rs.next()) {
      // Get dbObject metadata
      String dbColumnName = rs.getString(4); // column name
      String dbColumnTypeName = rs.getString(6); // data source dependent type name
      int dbColumnSize = rs.getInt(7); // column size
      String dbColumnIsNullable = rs.getString(18); // if allow NULL values

      columnHeads.addElement(dbColumnName); // prepare the head columns
      // prepare the row content
      row.addElement(dbColumnTypeName + " " + dbColumnSize + ", "
           + (dbColumnIsNullable.equalsIgnoreCase("yes") ? "Nullable" : "Not nullable"));
    }

    // Free database resources
    rs.close();

    // display the result metadata in the JTable
    rows.addElement(row);
    queryResult.setViewportView(new JTable(rows, columnHeads));
  }

  /**
   * Get the metadata for all the tables in the database
   */
  public void showSchema() throws SQLException {
    // the result string, which will be displayed in a JTextArea
    String dbMetaData = "";
    // Get DatabaseMetaData
    DatabaseMetaData dbmd = connection.getMetaData();

    // Get all tables in the database. (if the last parameter is null it will
    // retriev all db Objects.
    String[] objectCategories = {"TABLE"};
    ResultSet rs = dbmd.getTables(null, null, "%", objectCategories);

    // form the string
    while(rs.next())
    {
      // Get dbObject metadata, table name here
      String dbObjectName = rs.getString(3).toLowerCase();
      dbMetaData = dbMetaData + "Table: " + dbObjectName + "\n";

      // get the specific result set for the table
      ResultSet rsTable = dbmd.getColumns(null, null, dbObjectName, null);

      // prepare the table metadata
      while (rsTable.next()) {
        // Get dbObject metadata
        String dbColumnName = rsTable.getString(4).toLowerCase(); // column name
        String dbColumnTypeName = rsTable.getString(6).toLowerCase(); // data source dependent type name
        int dbColumnSize = rsTable.getInt(7); // column size

        dbMetaData = dbMetaData + dbColumnName + " " + dbColumnTypeName + "("
                    + dbColumnSize + "), ";
      }

      dbMetaData = dbMetaData + "\n\n";

      // Free table result set
      rsTable.close();
    }

    // Close database resources
    rs.close();

    // Display the result string
    queryResult.setViewportView(new JTextArea(dbMetaData));
  }

  /**
   *  Pop up a window to display error message
   *
   *@param  message  Error message to be displayed
   */
  public void popUpErrorMsg(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   *  Execute the sql statement from the input text area and display the result
   *  in a JTable
   */
  public void getTable() {
    try {
      // get the sql statement from the input text area
      String query = inputQuery.getText();

      // create a statement
      statement = connection.createStatement();

      if (query.trim().toLowerCase().startsWith("select")) { // if it is a query
        // execute and display
        resultSet = statement.executeQuery(query);
        displayResultSet(resultSet);
      } else if (query.trim().toLowerCase().startsWith("show schema table ")) { // to show table schema
        showSchema(query.trim().substring(18).replace(';', ' ')); // get the table name, table name should begin at 18
      } else if (query.trim().compareToIgnoreCase("show schema") == 0) { // show metadata for all the tables
        showSchema();
      } else { // it is either the DML("insert", "update", "delete") statement or DDL("create", "drop") statement
        statement.executeUpdate(query);
      }
    } catch (SQLException sqlex) {
      popUpErrorMsg("Error fetching metadata: " + sqlex.getMessage());
    }
  }

  /**
   *  Gets the NextRow attribute of the FSQL object
   *
   *@param  rs                The result set from the sql statement
   *@param  rsmd              The metadata from the result set
   *@return                   The vector of values in the result set
   */
  private Vector getNextRow(ResultSet rs, ResultSetMetaData rsmd)
       throws SQLException {
    Vector currentRow = new Vector(); // the vector to hold values of rs

    for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
      currentRow.addElement(rs.getString(i));
    }
    return currentRow;
  }

  /**
   *  Description of the Method
   *
   *@param  rs                The result set of the sql statement
   */
  public void displayResultSet(ResultSet rs) throws SQLException {
    boolean moreRecords = rs.next();

    // If there are no records, display a message
    if (!moreRecords) {
      JOptionPane.showMessageDialog(this,
          "ResultSet contained no records");
      setTitle("No records to display");
      return;
    }

    Vector columnHeads = new Vector();
    Vector rows = new Vector();

    try {
      // get column heads
      ResultSetMetaData rsmd = rs.getMetaData();

      for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
        columnHeads.addElement(rsmd.getColumnLabel(i));
      }

      // get row data
      do {
        rows.addElement(getNextRow(rs, rsmd));
      } while (rs.next());

      // display the result in a JTable
      queryResult.setViewportView(new JTable(rows, columnHeads));
    } catch (SQLException sqlex) {
      popUpErrorMsg("Error fetching metadata: " + sqlex.getMessage());
    }
  }

  /**
   *  The main program for the FSQL class
   *
   *@param  args  The command line arguments
   */
  public static void main(String args[]) {
    if (args.length != 1) {
      System.err.println("Parameters: <ODBC Driver Name>");
      System.exit(1);
    }

    final FSQL app = new FSQL(args[0]);
  }
}

