<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <title>404-找不到界面</title>
</head>
<body>
<h4>未找寻到页面：<%=request.getAttribute("javax.servlet.error.message")%></h4>
<a href="<%=request.getContextPath()%>/system/page/main">返回主页</a>
<pre>
<%--<%
    Enumeration<String> attributeNames = request.getAttributeNames();
    while (attributeNames.hasMoreElements())
    {
        String attributeName = attributeNames.nextElement();
        Object attribute = request.getAttribute(attributeName);
        out.println("request.attribute['" + attributeName + "'] = " + attribute);
    }
//    输出以下内容：
//    request.attribute['javax.servlet.forward.request_uri'] = /paas/
//    request.attribute['javax.servlet.forward.context_path'] = /paas
//    request.attribute['javax.servlet.forward.servlet_path'] = /index.jsp
//    request.attribute['org.springframework.web.context.request.async.WebAsyncManager.WEB_ASYNC_MANAGER'] = org.springframework.web.context.request.async.WebAsyncManager@4b481df1
//    request.attribute['javax.servlet.error.status_code'] = 404
//    request.attribute['org.springframework.web.context.request.RequestContextListener.REQUEST_ATTRIBUTES'] = org.apache.catalina.connector.Request@707b2ea3
//    request.attribute['javax.servlet.error.message'] = /paas/view/layout/main.jsp
//    request.attribute['javax.servlet.error.servlet_name'] = jsp
//    request.attribute['javax.servlet.error.request_uri'] = /paas/
//    request.attribute['javamelody.request'] = / GET
%>--%>
</pre>
</body>
</html>
