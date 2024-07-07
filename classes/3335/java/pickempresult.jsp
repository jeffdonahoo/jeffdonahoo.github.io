<%@ page language="java" import="java.sql.*" %>
<html>
<head>
<title>Employee Selection</title>
</head>

<body>

<%
String ID = request.getParameter("ID");

Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
Connection connect = DriverManager.getConnection("jdbc:odbc:personnel");
Statement statement = connect.createStatement();

String sql = "SELECT last_name, first_name FROM Employees " +
"WHERE employee_id = '" + ID + "'";
ResultSet rs = statement.executeQuery(sql);

if (!rs.next()) {
%>
<H1>No such employee</H1><P>
<A HREF="pickemp.html">Pick Employee</A>
<%
} else {
%>
<H1> Employee Found </H1><BR>
<%= rs.getString("last_name") %>, <%= rs.getString("first_name") %> <P>

<A HREF="listdegrees.jsp">List Degrees</A>

<%
session.setAttribute("employeeID", ID);
connect.close();
}
%>

</body>
</html>
