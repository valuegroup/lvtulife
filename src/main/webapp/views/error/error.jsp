<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head><title>Exception!</title></head>
<body>
<%
    Exception e = (Exception) request.getAttribute("ex");
    if (e == null) {
        Object info = request.getAttribute("javax.servlet.error.exception");
        e = new Exception(info.toString());
    }
%>
<H2>未知错误: <%=e.getClass().getSimpleName()%>
</H2>
<hr/>
<P>错误描述：</P>
<%= e.getMessage()%>
<P>错误信息：</P>
<% e.printStackTrace(new java.io.PrintWriter(out)); %>
</body>
</html>