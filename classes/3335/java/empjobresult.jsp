<%@ page language="java" import="java.sql.*" %>
<html>
<head>
<title>Employee Jobs</title>
</head>

<body>

<%
String ID = request.getParameter("ID");

Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
Connection connect = DriverManager.getConnection("jdbc:odbc:personnel");
Statement statement = connect.createStatement();

String sql = "SELECT job_title FROM Employees E, Job_History H, Jobs J " +
"WHERE E.employee_id = H.employee_id AND H.job_code = J.job_code AND " +
"E.employee_id = '" + ID + "'";
ResultSet rs = statement.executeQuery(sql);

while (rs.next()) {
  out.println(rs.getString("job_title") + "<BR>");
}

connect.close();

%>

</body>
</html>
