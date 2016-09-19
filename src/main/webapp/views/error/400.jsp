<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>400-服务端异常</title>
</head>
<body>
<h4>服务端异常:<%=request.getAttribute("javax.servlet.error.message")%></h4>
<a href="<%=request.getContextPath()%>/system/page/main">返回主页</a>
</body>
</html>
