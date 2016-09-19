<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title>用户登录已超时</title>
    <style type="text/css">
        body {
            text-align: center;
        }

        #sessionOut {
            margin-top: 50px;
            padding: 15px 50px;
            width: 500px;
            border: 2px solid green;
            background-color: yellow;
            text-align: center;
        }

        a {
            font-weight: bold;
            font-family: "宋体";
            font-size: 18px;
        }
    </style>
</head>
<body>
<c:url value="/login.jsp" var="loginUrl"/>
<div id="sessionOut">
    您长时间未操作系统，为确保您的资料及数据信息安全，
    系统自动超时退出，请重新<a href="${loginUrl}">登录</a>系统！
</div>
</body>
<script type="text/javascript">
    if (self != top) {
        window.top.location = window.location;
    }
</script>
</html>