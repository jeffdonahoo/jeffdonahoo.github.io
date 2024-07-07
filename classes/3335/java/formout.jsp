<html>
<head>
<title>Form Output</title>
</head>

<body>

Name: <%= request.getParameter("username") %> <br>
Password: <%= request.getParameter("password") %> <br>
Comment: <br>
<%= request.getParameter("comment") %> <p>
Gender: <%= request.getParameter("gender") %><br>
Call me: <%= (request.getParameter("call") == null) ? "No" :  request.getParameter("call") %><br>
Customer Type:  <%= request.getParameter("type") %>

</body>
</html>
