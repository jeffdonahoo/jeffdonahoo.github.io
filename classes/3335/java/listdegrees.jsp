<%@ page language="java" import="java.sql.*" %>
<html>
<head>
<title>Degrees</title>
</head>

<body>

<%
String ID = (String) session.getAttribute("employeeID");

System.out.println(ID);

Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
Connection connect = DriverManager.getConnection("jdbc:odbc:personnel");
Statement statement = connect.createStatement();

String sql = "SELECT degree, degree_field, college_name " +
"FROM Degrees D, Colleges C WHERE D.college_code = C.college_code AND " +
"employee_id = '" + ID + "'";
ResultSet rs = statement.executeQuery(sql);

while (rs.next()) {
  out.print(rs.getString("degree") + " in " + rs.getString("degree_field"));
  out.println(" from " + rs.getString("college_name"));
}

connect.close();
%>

</body>
</html>
