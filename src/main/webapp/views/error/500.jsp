<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>500-应用程序错误</title>
</head>
<body>
<h4>应用程序错误:</h4>
<pre>
<%=request.getAttribute("javax.servlet.error.message")%>
<%--    <%
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements())
        {
            String attributeName = attributeNames.nextElement();
            Object attribute = request.getAttribute(attributeName);
            out.println("request.attribute['" + attributeName + "'] = " + attribute);
        }
    %>--%>
</pre>
<a href="<%=request.getContextPath()%>/system/page/main">返回主页</a>
</body>
</html>
