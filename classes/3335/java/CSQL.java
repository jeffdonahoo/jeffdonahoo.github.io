import java.sql.*;

/**
 *  <p>
 *
 *  A command-line based sql executer. It uses the jdbc-odbc bridge to access
 *  the datasource. The odbc source is specified as the first parameter. It
 *  supports SQL DML statements like "select", "insert", "update", "delete",
 *  SQL DDL statements like "create table", "drop table" and also a "show schema
 *   table TABLENAME" statement. <p>
 *
 *  <b>Note:</b> in the command line, use ' instead of " to present strings.
 */
public class CSQL {
  // the jdbc connection object
  private Connection conn = null;

  {
    try {
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    } catch (ClassNotFoundException e) {
      die("Unable to load driver");
    }
  }

  /**
   *  Constructor for the CSQL object
   *
   *@param  driverName  Name of the odbc data source
   */
  public CSQL(String driverName) {
    try {
      conn = DriverManager.getConnection("jdbc:odbc:" + driverName); // open a connection
    } catch (SQLException e) {
      die("Unable to get connection");
    }
  }

  /**
   *  Gets the metadata for the table
   */
  public void getSchema(String tablename) throws SQLException {
    // Get DatabaseMetaData
    DatabaseMetaData dbmd = conn.getMetaData();

    // Get all column types for the table "sysforeignkeys", in schema
    // "dbo" and catalog "test".
    ResultSet rs = dbmd.getColumns(null, null, tablename, null);

    // Printout table data
    while(rs.next()) {
      // Get dbObject metadata
      String dbColumnName = rs.getString(4); // column name
      String dbColumnTypeName = rs.getString(6); // data source dependent type name
      int    dbColumnSize = rs.getInt(7); // column size
      String dbColumnDefault = rs.getString(13); // default value
      int    dbOrdinalPosition = rs.getInt(17); // index of column in table (starting at 1)
      String dbColumnIsNullable = rs.getString(18); // if allow NULL values

      // Printout
      System.out.println("Col(" + dbOrdinalPosition + "): " + dbColumnName
        + " (" + dbColumnTypeName + " " + dbColumnSize + ")"
        + "   Nullable: " + dbColumnIsNullable + ", default: "
        + dbColumnDefault);
    }

    // Free database resources
    rs.close();
  }

  /**
   * Get the metadata for all the tables in the database
   */
  public void showSchema() throws SQLException {
    // the result string, which will be displayed in a JTextArea
    String dbMetaData = "";
    // Get DatabaseMetaData
    DatabaseMetaData dbmd = conn.getMetaData();

    // Get all tables in the database. (if the last parameter is null it will
    // retriev all db Objects.
    String[] objectCategories = {"TABLE"};
    ResultSet rs = dbmd.getTables(null, null, "%", objectCategories);

    // form the string
    while(rs.next()) {
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
    System.out.println(dbMetaData);
  }

  /**
   *  Close the connection. This method needs to be called when a sql exception
   *  occurs so that a connection needs to be closed.
   */
  public void close() {
    try {
      conn.close(); // close a connection
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Close a statement
   *@param  s  The statement to be closed
   */
  private void closeStatement(Statement s) {
    try {
      s.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   *  Execute the sql statement. The statements can be "select", "insert",
   *  "update", "delete", "create table" or "drop table", etc.
   *
   *@param  stmt  The sql statement
   */
  public void execute(String stmt) {
    Statement s = null;
    try {
      s = conn.createStatement(); // get our Statement instance from the connection
    } catch (SQLException e) {
      die("Unable to create statement");
    }

    // excute the statement
    if (stmt.trim().toLowerCase().startsWith("select")) { // it is a sql query statement
      ResultSet rs = null;
      try {
        rs = s.executeQuery(stmt); // excute the query and get the result set
      } catch (SQLException e) {
        printMessage("Query failed: " + e.getMessage());
        closeStatement(s);
        return;
      }

      ResultSetMetaData rsmd = null;
      try {
        rsmd = rs.getMetaData(); // get metadata from the result set
        for (int col = 1; col <= rsmd.getColumnCount(); col++) {
          System.out.print(rsmd.getColumnLabel(col) + "\t"); // print out the column label
        }
        System.out.println();
      } catch (SQLException e) {
        printMessage("Unable to retrieve metadata: " + e.getMessage());
        closeStatement(s);
        return;
      }

      try {
        // read through the result set
        while (rs.next()) {
          for (int col = 1; col <= rsmd.getColumnCount(); col++) {
            System.out.print(rs.getString(col) + "\t"); // print out data by columns
          }
          System.out.println(); // change line for a new row
        }
      } catch (SQLException e) {
        printMessage("Error processing results: " + e.getMessage());
        closeStatement(s); //close the statement, but not the connection
      }
    } else if (stmt.trim().toLowerCase().startsWith("show schema table ")) { // to show table schema
      try {
        getSchema(stmt.trim().substring(18).replace(';',' ')); // get the table name, table name should begin at 18
      } catch (SQLException e) {
        printMessage("Error getting table schema: " + e.getMessage());
        closeStatement(s);
      }
    } else if (stmt.trim().compareToIgnoreCase("show schema") == 0) { // show metadata for all the tables
      try {
        showSchema();
      } catch (SQLException e) {
        printMessage("Error getting database schema: " + e.getMessage());
        closeStatement(s);
      }
    } else { // it is either the DML("insert", "update", "delete") statement or DDL("create", "drop") statement
      try {
        s.executeUpdate(stmt); // execute the statement
      } catch (SQLException e) {
        printMessage("sql failed: " + e.getMessage());
        closeStatement(s); //close the statement, but not the connection
      }
    }
  }

  /**
   *  Print out an error message and abort.
   *
   *@param  error  the error message
   */
  private void die(String error) {
    System.err.println("Unable to load driver");
    if (conn != null) {
      close();
    }
    System.exit(1);
  }

  /**
   *  Read a line from the command line, quit when it reads a '\n'.
   *
   *@return    the line which is read from the command line
   */
  public static String readLine() {
    int ch;
    String r = "";
    boolean done = false;
    while (!done) {
      try {
        ch = System.in.read();
        if (ch < 0 || (char) ch == '\n') { //read until a RETURN char is met or an error occurs
          done = true;
        } else if ((char) ch != '\r') { // ignore '\r'
          r = r + (char) ch;
        }
      } catch (java.io.IOException e) {
        done = true;
      }
    }
    return r;
  }

  /**
   *  Print out a message
   *
   *@param  message  the message to be printed
   */
  public static void printMessage(String message) {
    System.out.println(message);
    System.out.flush();
  }

  /**
   *  Print out a prompt
   *
   *@param  prompt  the prompt to be printed
   */
  public static void printPrompt(String prompt) {
    System.out.print(prompt + " ");
    System.out.flush();
  }

  /**
   *  Print a prompt and read a line from the command line
   *
   *@param  prompt  The prompt to be printed
   *@return         The line read from the command line
   */
  public static String readLine(String prompt) {
    printPrompt(prompt);
    return readLine();
  }

  /**
   * Prompt for which command to key in
   */
  public static void printUsage() {
    System.out.println("*********************************************************");
    System.out.println("Use sql sentence to manupulate database.");
    System.out.println("Use \"show schema\" to show the metadata of all the tables.");
    System.out.println("Use \"show schema table TABLENAME\" to show table schema.");
    System.out.println("Use \"quit\" to quit.");
    System.out.println("*********************************************************");
  }

  /**
   *  The main program for the CSQL class
   *
   *@param  args  The command line arguments
   */
  public static void main(String[] args) {

    if (args.length != 1) {
      System.err.println("Parameters: <ODBC Driver Name>");
      System.exit(1);
    }

    CSQL sql = new CSQL(args[0]);

    // print out usage
    printUsage();

    String stmt = CSQL.readLine("> ");
    // loop until user keys in "quit" to quit
    while (stmt.compareToIgnoreCase("quit") != 0) {
      sql.execute(stmt);
      stmt = CSQL.readLine("> ");
    }
  }

}

