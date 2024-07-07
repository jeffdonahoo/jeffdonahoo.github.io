import java.sql.*;
import java.io.*;

public class Programmer extends Employee {
  private String project;

  public Programmer(String EID, String name, Connection c) throws Exception {
    super(EID, name, c);

    // Get Phone info
    Statement stmt = c.createStatement();
    String query = "SELECT Project FROM Programmer " +
      "WHERE EID = \'" + EID + "\'";

    ResultSet rs = stmt.executeQuery(query);
    if (rs.next()) {
      project = rs.getString("Project");
    } else {
      project = null;
    }
    rs.close();
    stmt.close();
  }

  public void print() {
    super.print();
    System.out.println("Programmer");
  }
}