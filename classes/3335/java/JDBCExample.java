import java.sql.*;

public class JDBCExample {

  public static void main(String[] args) throws Exception {

    // Get employee ID from command-line
    if (args.length != 1) {
      System.out.println("Parameter(s):  <ID>");
      System.exit(1);
    }

    String id = args[0];  // Employee ID

    Class.forName("com.mysql.jdbc.Driver");  // Load the MySQL driver
    // Get a database connection from the driver manager based on the URL
    Connection conn = DriverManager.getConnection(
      "jdbc:mysql://localhost/personnel?user=root&password=3335rocks");

    // Get a statement from the connection
    Statement stmt = conn.createStatement();

    // Create the SQL query
    String sql = "SELECT last_name, first_name FROM employees " + 
      "WHERE employee_id = \'" + id + "\'";

    ResultSet rs = stmt.executeQuery(sql);  // Execute the query
    if (rs.next()) {  // If a row is returned, print the name
      System.out.println(rs.getString("last_name") + " " + rs.getString("first_name"));
    } else {  // If no row is returned, print an error
      System.out.println("No such employee ID");
    }
    rs.close();

    // Create an SQL insert
    sql = "INSERT INTO Colleges " +
          "(college_code, college_name, city, state, postal_code) " +
          "VALUES ('BU', 'Baylor University', 'Waco', 'TX', '76798')";

    // Execute the insert
    try {
      int rowCt = stmt.executeUpdate(sql);
      System.out.println("Inserted " + rowCt + " rows");
    } catch (SQLException e) {
      System.err.println("Unable to insert: " + e.getMessage());
    }

    stmt.close();  // Close the statement
    conn.close();  // Close the connection
  }
}
