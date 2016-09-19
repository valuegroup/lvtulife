<%--
  Created by IntelliJ IDEA.
  User: lvtulife
  Date: 2016-04-23 
  Time: 12:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="/views/include/incBootstrap.jsp"/>
</head>
<body>
<h1>OAuth Approval</h1>

<p>Do you authorize '${authorizationRequest.clientId}' to access your protected resources?</p>

<form id='confirmationForm' name='confirmationForm' action='${pageContext.request.contextPath}/oauth/authorize'
      method='post'>
    <input name='user_oauth_approval' value='true' type='hidden'/>
    <label> <input name='authorize' value='Authorize' type='submit' class="btn btn-success"/></label>
</form>
<form id='denialForm' name='denialForm' action='${pageContext.request.contextPath}/oauth/authorize' method='post'>
    <input name='user_oauth_approval' value='false' type='hidden'/>
    <label><input name='deny' value='Deny' type='submit' class="btn btn-warning"/></label>
</form>
</body>
</html>
