import java.sql.*;

public class JDBCExample2 {

  public static void main(String[] args) throws Exception {

    // Get employee last name from command-line
    if (args.length != 1) {
      System.out.println("Parameter(s):  <Last Name>");
      System.exit(1);
    }

    String lastname = args[0];  // Employee last name

    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection(
      "jdbc:mysql://localhost/personnel?user=root&password=3335rocks");

    Statement empstmt = conn.createStatement();  // Statement for employee query

    // Find employees query
    String sql = "SELECT employee_id, last_name, first_name " +
      "FROM employees WHERE last_name = \'" + lastname + "\'";
    ResultSet emprs = empstmt.executeQuery(sql);

    if (!emprs.next()) {  // Any matching employees?
      System.out.println("No matching employees");
    } else {

      // Executing a statement closes the Statement's associated ResultSet
      // (see java.sql.Statement API documentation).  Therefore, since we are 
      // nesting queries, we must have separate Statement instances for 
      // employees and salaries.
      Statement salstmt = conn.createStatement();  // Statement for salary query

      // Loop until no more employees
      do {
        // Print employee information
        String employeeid = emprs.getString("employee_id");  // Get the next employee ID
        System.out.println(emprs.getString("last_name") + " " + emprs.getString("first_name"));
        System.out.println("**********************************");

        // Find an print employee salary information
        sql = "SELECT salary_amount, salary_start, salary_end " +
          "FROM salary_history WHERE employee_id = " + employeeid + " ORDER BY salary_start";
        ResultSet salrs = salstmt.executeQuery(sql);
        while (salrs.next()) {
          String line = "$" + salrs.getString("salary_amount") + " - " + salrs.getString("salary_start") + "  ";
          String end = salrs.getString("salary_end");
          line += (salrs.wasNull()) ? "present" : end;
          System.out.println(line);
        }
        salrs.close();
        System.out.println();
      } while (emprs.next());

      salstmt.close();  // Close the salary statement
    }
    emprs.close();

    empstmt.close();  // Close the employee statement
    conn.close();     // Close the connection
  }
}
