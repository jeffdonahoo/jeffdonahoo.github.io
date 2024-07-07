<%@ page language="java" import="java.sql.*" errorPage="error.jsp" %>
<html>
<head>
<title>Personnel List</title>
</head>

<body>

<%
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
Connection connect = DriverManager.getConnection("jdbc:odbc:personel");
Statement statement = connect.createStatement();

String sql = "select e.employee_id AS foo, H.employee_id AS bar from employees E, Job_history H";
ResultSet rs=statement.executeQuery(sql);
%>

<TABLE border="1">
  <TR><TH>ID<TH>Name

  <%
  while (rs.next()) {
    out.println("<tr><td>" + rs.getString("foo"));
    out.println("<td>" + rs.getString("bar"));
  }
  connect.close();
  %>

</TABLE>

</body>
</html>
