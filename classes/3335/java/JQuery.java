

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.sql.*;

public class JQuery {

  private Connection conn = null;

  {
    try {
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    } catch (ClassNotFoundException e) {
      die("Unable to load driver");
    }
  }

  public void close() {
    try {
      conn.close();
    } catch (SQLException e) {}
  }

  private void die(String error) {
    System.err.println("Unable to load driver");
    if (conn != null) {
      close();
    }
    System.exit(1);
  }

  public JQuery(String driverName) {
    try {
      conn = DriverManager.getConnection("jdbc:odbc:" + driverName);
    } catch (SQLException e) {
      die("Unable to get connection");
    }
  }

  public void execute(String stmt) {
    Statement s = null;
    try {
      s = conn.createStatement();
    } catch (SQLException e) {
      die("Unable to create statement");
    }

    ResultSet rs = null;
    try {
      rs = s.executeQuery(stmt);
    } catch (SQLException e) {
      try {
        s.close();
      } catch (SQLException e1) {}
      printMessage("Query failed: " + e.getMessage());
      return;
    }

    ResultSetMetaData rsmd = null;
    try {
      rsmd = rs.getMetaData();
      for (int col = 1; col <= rsmd.getColumnCount(); col++) {
        System.out.print(rsmd.getColumnLabel(col) + "\t");
      }
      System.out.println();
    } catch (SQLException e) {
      try {
        s.close();
      } catch (SQLException e1) {}
      printMessage("Unable to retrieve metadata: " + e.getMessage());
    }

    try {
      while (rs.next()) {
        for (int col = 1; col <= rsmd.getColumnCount(); col++) {
          System.out.print(rs.getString(col) + "\t");
        }
        System.out.println();
      }
    } catch (SQLException e) {
      printMessage("Error processing results: " + e.getMessage());
    } finally {
      try {
        s.close();
      } catch (SQLException e) {}
    }
  }

  public static String readLine() {
    int ch;
    String r = "";
    boolean done = false;
    while (!done) {
      try {
        ch = System.in.read();
        if (ch < 0 || (char)ch == '\n') {
          done = true;
        } else if ((char)ch != '\r') {
          r = r + (char) ch;
        }
      } catch(java.io.IOException e) {
        done = true;
      }
    }
    return r;
  }

  public static void printMessage(String message) {
    System.out.println(message);
    System.out.flush();
  }

  public static void printPrompt(String prompt) {
    System.out.print(prompt + " ");
    System.out.flush();
  }

  public static String readLine(String prompt) {
    printPrompt(prompt);
    return readLine();
  }

  public static void main(String[] args) {

    if (args.length != 1) {
      System.err.println("Parameters: <ODBC Driver Name>");
      System.exit(1);
    }

    JQuery query = new JQuery(args[0]);

    String stmt = JQuery.readLine("> ");
    while (stmt.compareToIgnoreCase("quit") != 0) {
      query.execute(stmt);
      stmt = JQuery.readLine("> ");
    }
  }
}