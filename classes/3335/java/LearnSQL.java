/*
 * 1.	Add File (New Connection..., Separator, Exit), Edit (Copy, Cut,
 * Paste, Undo), and Database (Show Schema [Tree representation],
 * Auto-Commit [check]).
 * 2.	Add commit and rollback commands.
 */
import java.sql.*;

import java.awt.*;
import java.awt.event.*;

import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import java.net.URL;
import javax.swing.tree.*;
import javax.swing.JTree;
/**
 *  A swing based sql executer. It uses the jdbc-odbc bridge to access the
 *  datasource. The odbc source is specified as the first parameter. It supports
 *  SQL DML statements like "select", "insert", "update", "delete", SQL DDL
 *  statements like "create table", "drop table" and also a "show schema table
 *  TABLENAME" command to show table metadata and a "show schema" command to
 *  show database schema. <p>
 *
 *  <b>Note:</b> in the command line, use ' instead of " to present strings.
 */
public class LearnSQL extends JFrame implements ActionListener, MenuListener {

	/**
	 *  JDBC objects
	 */
	private Connection connection; // the jdbc connection object
	private Statement statement; // sql statement
	private ResultSet resultSet; // result set from the sql statement
	private ResultSetMetaData rsMetaData; // metadata from the result set
	private String drivername;
	/**
	 *  Objects for the Dialog to accept the user JDBC:ODBC connection driver.
	 *  Objects to Display the Query in a Table
	 *
	 */
	private JScrollPane queryResult = new JScrollPane();
	private JTextArea inputQuery; // text area for input
	private JButton submit; // the submit button

	/**
	 *  Objects for the schema tree
	 */
	private JEditorPane schemaPane;
	private JTree schemaTree;
	private DefaultMutableTreeNode top;
	private JScrollPane queryView;
	private JScrollPane schemaView;
	private JTable resultTable;

	/**
	 *  Objects for the menu & menu bar
	 */
	private JMenuBar menubar;
	private JMenu menu;

	private String clipboard = new String();

	{ // load jdbc-odbc driver at the class load time

		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e) {
			popUpErrorMsg("Failed to load JDBC/ODBC driver.");
			System.exit(1);
		}
	}

	/**
	 *  Constructor for the LearnSQL class
	 */
	public LearnSQL() {
		//super("Use \"show schema\" to show database schema");
		super(" Learn SQL ");

		// handle the close window event inside our program
		addWindowListener(
			new WindowAdapter() {
				/**
				 *  Description of the Method
				 *
				 *@param  e  Description of Parameter
				 */
				public void windowClosing(WindowEvent e) {
					// shutDown(); // close the connetion
					System.exit(0);
				}
			}
				);

		// If connected to database, set up GUI
		inputQuery = new JTextArea("", 4, 30);
		// do the sql statement when the user press enter

		submit = new JButton("Submit");
		// do the sql statement when the submit button is clicked
		submit.addActionListener(
			new ActionListener() {

				/**
				 *  Description of the Method
				 *
				 *@param  e  Description of Parameter
				 */
				public void actionPerformed
						(ActionEvent e) {
					getTable();
				}
			});

		// topPanel contains the input text area and the submit button
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());

		//topPanel.add(submit, BorderLayout.SOUTH);
		JPanel text = new JPanel();
		JLabel label = new JLabel(" Enter SQL Statement : ");

		topPanel.add(label, BorderLayout.NORTH);
		topPanel.add(new JScrollPane(inputQuery), BorderLayout.CENTER);
		topPanel.add(submit, BorderLayout.SOUTH);
		// set the topPanel in NORTH and the sql result in CENTER
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.add(topPanel, BorderLayout.NORTH);
		//c.add(queryResult, BorderLayout.CENTER);


		// create the menubar
		menubar = new JMenuBar();
		setJMenuBar(menubar);

		// Create file menu
		JMenu fileMenu = new JMenu("  File  ");
		menubar.add(fileMenu);

		// Create the menuItems for File
		JMenuItem openConnection = new JMenuItem(" Open Connection ");
		openConnection.addActionListener(this);
		/*
		 * These statements add the "Close Connection"
		 * menu to the file menu.
		 *
		 * JMenuItem closeConnection =
		 * new JMenuItem ( " Close Connection " );
		 * closeConnection.addActionListener( this );
		 */
		JMenuItem exitItem = new JMenuItem(" Exit ");
		exitItem.addActionListener(this);

		fileMenu.add(openConnection);
		/*
		 * fileMenu.add ( closeConnection );
		 */
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		fileMenu.addMenuListener(this);
		//fileMenu .add ( MenuListener ( this ) ) ;

		// Add the menu 'Edit'
		JMenu editMenu = new JMenu("  Edit  ");
		menubar.add(editMenu);

		// Add the menu Items for Edit
		JMenuItem cutItem = new JMenuItem(" Cut ");
		cutItem.addActionListener(this);
		JMenuItem copyItem = new JMenuItem(" Copy ");
		copyItem.addActionListener(this);
		JMenuItem pasteItem = new JMenuItem(" Paste ");
		pasteItem.addActionListener(this);

		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.addMenuListener(this);

		// Add the Menu ' Database '
		JMenu dataBaseMenu = new JMenu("  Database  ");
		menubar.add(dataBaseMenu);

		//JMenuItem
		// Add the ' Auto Commit ' Check Box to the Data base menu .
		JCheckBoxMenuItem autoCommitItem =
				new JCheckBoxMenuItem(" Auto Commit ");
		//	undoItem.addActionListener( this );

		// Set AutoCommit to false as default vale
		autoCommitItem.setSelected(false);
		autoCommitItem.addActionListener(this);

		dataBaseMenu.add(autoCommitItem);
		dataBaseMenu.addMenuListener(this);

		// Create the tree and add the tree Listener
		top = new DefaultMutableTreeNode(" Schema ");
		schemaTree = new JTree(top);

		schemaTree.getSelectionModel().setSelectionMode
				(TreeSelectionModel.SINGLE_TREE_SELECTION);

		//Listen for when the selection changes.
		schemaTree.addTreeSelectionListener(
			new TreeSelectionListener() {

				/**
				 *  Description of the Method
				 *
				 *@param  e  Description of Parameter
				 */
				public void valueChanged(TreeSelectionEvent e) {
					DefaultMutableTreeNode node =
							(DefaultMutableTreeNode)

					schemaTree.getLastSelectedPathComponent();

					if (node == null) {
						return;
					}

					Object nodeInfo = node.getUserObject();
					if (node.isLeaf()) {

						String leaf = (String) nodeInfo;
						try {
							URL u = new URL(leaf);
							displayURL(u);
						} catch (Exception urle) {

							// Do not print the stack trace
							// here we know we are at the
							// leaf nodes of the schema tree
							// urle.printStackTrace();
							//System.out.println ( " At Leaf Node " );

						}
					}
				}
			});

		schemaView = new JScrollPane(schemaTree);

		schemaPane = new JEditorPane();
		schemaPane.setEditable(false);

		// Create a scroll pane for the query result table
		queryView = new JScrollPane(queryResult);

		// Create a split between the Schema View and the Query view
		// The schema is displayed to the left of the Split pane
		// The query results ( table ) is shown in the Right split pane.

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		split.setRightComponent(queryView);
		split.setLeftComponent(schemaView);
		split.setDividerLocation(200); //XXX: ignored in some releases

		setSize(700, 500);

		c.add(split, BorderLayout.CENTER);

		// Show all the entire application
		show();

	}

	/**
	 *  Execute the sql statement from the input text area and display the result
	 *  in a JTable
	 */
	public void getTable() {

		try {
			// get the sql statement from the input text area

			String query = inputQuery.getText();
			// create a statement, do nothing if there is no connection
			if (connection == null) {
				popUpErrorMsg("Make a connection first!");
				return;
		  }
			statement = connection.createStatement();

			if (query.trim().toLowerCase().startsWith("select")) {
				// if it is a query
				// execute and display
				resultSet = statement.executeQuery(query);
				displayResultSet(resultSet);
			} else if (query.trim().toLowerCase().
					startsWith("show schema table ")) {
				// to show table schema
				showSchema(query.trim().substring(18).
						replace(';', ' '));
				// get the table name, table name
				// should begin at 18
			} else {
				// it is either the DML("insert",
				// "update", "delete")
				// statement or DDL("create", "drop")
				//  statement
				statement.executeUpdate(query);
				// blank out the output result
				// table so that the user knows the
				// resultset is no longer valid
				if (resultTable != null) {
					resultTable.setBackground(Color.gray);
					resultTable.setEnabled(false);
				}
			}

		} catch (SQLException sqlex) {
			popUpErrorMsg("Error fetching metadata: "
					 + sqlex.getMessage());
		}
	}

	/**
	 *  Description of the Method
	 *
	 *@param  evt  Description of Parameter
	 */
	public void actionPerformed(ActionEvent evt) {
		String arg;

		if (evt.getSource() instanceof JCheckBoxMenuItem) {
			// the status of auto commit has been changed
			try {
				// autocommit changed from false to true,
				//  autocommit can not be changed
				// from true to false while some transactions
				// already began
				if (((JCheckBoxMenuItem) evt.getSource())
						.isSelected()) {
					if (connection != null) {
						connection.setAutoCommit(true);
					}
				} else {
					((JCheckBoxMenuItem)
							evt.getSource()).setSelected(true);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (evt.getSource() instanceof JMenuItem) {
			arg = evt.getActionCommand();
			if (arg.equals(" Open Connection ")) {
				createNewConnection();
			} else if (arg.equals(" Exit ")) {
				System.exit(0);
			} else if (arg.equals(" Copy ")) {
			  inputQuery.copy();
			} else if (arg.equals(" Cut " )) {
				inputQuery.cut();
			} else if (arg.equals(" Paste ")) {
			  inputQuery.paste();
			}
		}
	}

	/**
	 *  Implement the abstract methods for the menu events
	 *
	 *@param  evt  Description of Parameter
	 */
	public void menuSelected(MenuEvent evt) {
	}

	/**
	 *  Description of the Method
	 *
	 *@param  evt  Description of Parameter
	 */
	public void menuDeselected(MenuEvent evt) {

	}

	/**
	 *  Description of the Method
	 *
	 *@param  evt  Description of Parameter
	 */
	public void menuCanceled(MenuEvent evt) {

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
	 *@exception  SQLException  Description of Exception
	 */

	public void showSchema(String tablename) throws SQLException {
		// Get DatabaseMetaData
		DatabaseMetaData dbmd = connection.getMetaData();

		Vector columnHeads = new Vector(); // field names of the table
		Vector row = new Vector(); // content to be shown as a row
		Vector rows = new Vector();
		// vector of row, in order to initialize JTable,
		// it has to be a vector of vector
		// get the specific result set for the table
		ResultSet rs = dbmd.getColumns(null, null, tablename, null);

		// prepare the table metadata
		while (rs.next()) {
			// Get dbObject metadata
			String dbColumnName = rs.getString(4); // column name
			String dbColumnTypeName = rs.getString(6);
			// data source dependent type name
			int dbColumnSize = rs.getInt(7); // column size
			String dbColumnIsNullable = rs.getString(18);
			// if allow NULL values

			columnHeads.addElement(dbColumnName); // prepare the head columns
			// prepare the row content
			row.addElement(dbColumnTypeName + " " + dbColumnSize + ", "
					 + (dbColumnIsNullable.equalsIgnoreCase("yes") ? "Nullable" :
					"Not nullable"));
		}

		// Free database resources
		rs.close();

		// display the result metadata in the JTable
		rows.addElement(row);
		queryResult.setViewportView(new JTable(rows, columnHeads));
	}

	/**
	 *  Get the metadata for all the tables in the database
	 *
	 *@exception  SQLException  Description of Exception
	 */
	public void showSchema() throws SQLException {

		schemaTree.setRootVisible(true);

		// Tree nodes to show the schema;
		DefaultMutableTreeNode table;
		DefaultMutableTreeNode field;

		// the result string, which will be displayed in a JTextArea
		String dbMetaData = "";
		// Get DatabaseMetaData
		DatabaseMetaData dbmd = connection.getMetaData();

		// Get all tables in the database.
		//(if the last parameter is null it  will
		// retriev all db Objects.
		String[] objectCategories = {"TABLE"};
		ResultSet rs = dbmd.getTables(null, null, "%", objectCategories);

		// form the string
		while (rs.next()) {
			// Get dbObject metadata, table name here
			String dbObjectName = rs.getString(3).toLowerCase();

			table = new DefaultMutableTreeNode(dbObjectName);
			top.add(table);

			// dbMetaData = dbMetaData + "Table: " + dbObjectName + "\n";

			// get the specific result set for the table
			ResultSet rsTable = dbmd.getColumns(null, null, dbObjectName,
					null);

			// prepare the table metadata
			while (rsTable.next()) {
				// Get dbObject metadata
				String dbColumnName = rsTable.getString(4).toLowerCase();
				//column name
				String dbColumnTypeName = rsTable.getString(6).
						toLowerCase();
				//data source dependent type name
				int dbColumnSize = rsTable.getInt(7); // column size

				dbMetaData = new String(dbColumnName + " "
						 + dbColumnTypeName
						 + "("
						 + dbColumnSize + ")");
				field = new DefaultMutableTreeNode(dbMetaData);
				table.add(field);
			}

			// Free table result set
			rsTable.close();
		}

		// Close database resources
		rs.close();

		// Display the result string
		// queryResult.setViewportView(new JTextArea(dbMetaData));
	}


	/**
	 *  Pop up a window to display error message
	 *
	 *@param  message  Error message to be displayed
	 */
	public void popUpErrorMsg(String message) {
		JOptionPane.showMessageDialog(this, message, "Error",
				JOptionPane.ERROR_MESSAGE);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  rs                The result set of the sql statement
	 *@exception  SQLException  Description of Exception
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
		  resultTable = new JTable(rows, columnHeads);
			queryResult.setViewportView(resultTable);
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
	 *@exception  SQLException  Description of Exception
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
	 */
	private void createNewConnection() {

		ConnectionDialog cd = new ConnectionDialog(this);
		drivername = cd.showDialog();

		String url = "jdbc:odbc:" + drivername;

		// Load the driver to allow connection to the database
		try {
			if (drivername != null) {
				connection = DriverManager.getConnection(url);
			} else {
				popUpErrorMsg(" No Connection Opened. ");
			}
		} catch (SQLException e1) {
			popUpErrorMsg("Unable to connect");
			System.exit(1); // terminate program
		}

		// Display the metadata of the date source
		try {
			showSchema();
			// Change the name of the schema to the data source.
			top.setUserObject(drivername);
			// Redraw the tree.
			schemaTree.repaint();
		} catch (SQLException sqlex) {
			popUpErrorMsg("Error fetching metadata: "
					 + sqlex.getMessage());
		}

		// Connection's autocommit is set to false by default
		if (connection != null) {
			try {
				connection.setAutoCommit(false);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 *@param  url  Description of Parameter
	 */
	private void displayURL(URL url) {
		try {
			schemaPane.setPage(url);
		} catch (Exception e) {
			popUpErrorMsg("Attempted to read a bad URL: " + url);
		}
	}

	/**
	 *  Method to execute LearnSQL application
	 *
	 *@param  args  The command line arguments
	 */
	public static void main(String args[]) {

		final LearnSQL app = new LearnSQL();
	}

}
/**
 *  This class is designed for the pop-up window that appears when the user
 *  chooses the New Connection menu .
 *
 *@author     wangjju
 *@created    May 1, 2002
 */
class ConnectionDialog extends JDialog
		 implements ActionListener {

	JTextField drivername;
	String dname;
	boolean ok;

	/**
	 *  Create a Panel and add a text field to get the name of the odbc Driver
	 *  name.
	 *
	 *@param  parent  Description of Parameter
	 */
	public ConnectionDialog(JFrame parent) {
		super(parent, " Connection ", true);

		Container contentPane = getContentPane();

		// Create a panel to contain the
		// label and the text box for the driver
		JPanel p1 = new JPanel();
		// set the layout of this panel as a 2 by 2 panel
		p1.setLayout(new GridLayout(2, 2));
		// add a label
		p1.add(new JLabel(" ODBC Driver Name : "));

		// define a jtext field which accepts the ODBC Driver name.
		drivername = new JTextField();

		// add this textfield to the JPanel
		p1.add(drivername);

		// add it to the center of the dialog
		contentPane.add("Center", p1);

		// create another panel to enclose the
		// 'ok' button and the 'cancel' button
		Panel p2 = new Panel();

		JButton okButton = addButton(p2, " Ok ");
		JButton cancelButton = addButton(p2, " Cancel ");

		contentPane.add("South", p2);

		setSize(300, 150);
	}

	/**
	 *  Over write the action performed method for the new connection dialog.
	 *
	 *@param  evt  Description of Parameter
	 */
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();

		if (source instanceof JButton) {

			String arg = evt.getActionCommand();
			if (arg.equals(" Ok ")) {
				ok = true;
				setVisible(false);
			} else if (arg.equals(" Cancel ")) {
				setVisible(false);
			}
		}
	}

	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public String showDialog() {

		dname = new String();
		drivername.setText(dname);
		// accept user key stroke actions for the text field
		drivername.addKeyListener(
			new KeyListener() {
				/**
				 *  Description of the Method
				 *
				 *@param  e  Description of Parameter
				 */
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						ok = true;
						setVisible(false);
					}
				}

				/**
				 *  Description of the Method
				 *
				 *@param  e  Description of Parameter
				 */
				public void keyTyped(KeyEvent e) {
				}

				/**
				 *  Description of the Method
				 *
				 *@param  e  Description of Parameter
				 */
				public void keyReleased(KeyEvent e) {
				}
			}
				);

		ok = false;
		show();

		if (ok) {
			return drivername.getText();
		} else {
			return null;
		}
	}

	/**
	 *  This method adds a new Button to the container c.
	 *
	 *@param  c      The feature to be added to the Button attribute
	 *@param  bname  The feature to be added to the Button attribute
	 *@return        Description of the Returned Value
	 */
	JButton addButton(Container c, String bname) {
		JButton button = new JButton(bname);
		button.addActionListener(this);
		c.add(button);
		return button;
	}

}

