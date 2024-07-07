import java.sql.*;

public class JDBCExample {

  public static void main(String[] args) throws Exception {

    if (args.length != 1) {
      System.out.println("Parameter:  <State>");
      System.exit(1);
    }

    String state = args[0];

    // Load JDBC-ODBC Bridge Driver
    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

    // Get connection to personnel database
    Connection conn = DriverManager.getConnection("jdbc:odbc:personnel");

    // Create a statement to execute on the database
    Statement stmt = conn.createStatement();

    // Create an SQL query
    String sql = "SELECT college_name, city, state FROM Colleges " +
      "WHERE state = \'" + state + "\'";

    // Execute the SQL query
    ResultSet rs = stmt.executeQuery(sql);
    while (rs.next()) {  // Iterate over rows of result
      System.out.println(rs.getString("college_name") + "\t" +
        rs.getString("city") + "\t" + rs.getString("state"));
    }
    rs.close();  // Close result set

    // Create an SQL insert
    sql = "INSERT INTO Colleges " +
          "(college_code, college_name, city, state, postal_code) " +
          "VALUES ('BU', 'Baylor University', 'Waco', 'TX', '76798')";

    // Execute the insert
    int rowCt = stmt.executeUpdate(sql);
    System.out.println("Inserted " + rowCt + " rows");

    stmt.close();  // Close the statement
    conn.close();  // Close the connection
  }
}
