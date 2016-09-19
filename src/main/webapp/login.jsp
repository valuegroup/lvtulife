<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title>旅途生活</title>
    <security:csrfMetaTags/>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">
        $(function () {

            var loginFun = function () {
                if ($('form').form('validate')) {
                    $('#loginBtn').linkbutton('disable');
                    $('form').submit();
                    // 这种方式不会跳转页面
                    /*$.post(sysExt.contextPath + '/login', $('form').serialize(), function (result) {
                        if (result.success) {
                            location.replace(sysExt.contextPath + '/index.jsp');
                        } else {
                            $('#loginBtn').linkbutton('enable');
                            $.messager.alert('提示', '代码:' + result.code + '</br>内容:' + result.info + '</br>详细:' + result.detail, 'error');
                        }
                     }, 'json');*/
                }
            };

            $('#loginDialog').show().dialog({
                modal: false,
                closable: false,
                iconCls: 'ext-icon-lock_open',
                buttons: [<%--{
				text : '注册',
				handler : function() {
					location.replace(sysExt.contextPath + '/reg.jsp');
				}
			}, --%>{
                    id: 'loginBtn',
                    text: '登录',
                    handler: function () {
                        loginFun();
                    }
                }],
                onOpen: function () {
                    $('form :input:first').focus();
                    $('form :input').keyup(function (event) {
                        if (event.keyCode == 13) {
                            loginFun();
                        }
                    });
                }
            });
        });
    </script>
    <style type="text/css">
    </style>
</head>
<body>
<%--<body style="background: url('<%=contextPath%>/style/images/bg_aliyun.png')  no-repeat fixed top;">--%>
<div id="loginDialog" title="系统登录" style="display: none; width: 320px; height: 240px; overflow: hidden;">
    <%--<c:url value="/login" var="loginUrl"/>--%>
    <form action="<%=contextPath%>/login" method="post" class="form">
        <table class="table" style="margin-top: 10px;">
            <tr style="height: 20px;">
                <th width="50">登录名</th>
                <td><input name="username" class="easyui-validatebox" data-options="required:true"
                           value="admin"/><%--默认用户名密码 admin q --%></td>
            </tr>
            <tr>
                <th>密码</th>
                <td><input name="password" type="password" class="easyui-validatebox"
                           data-options="required:true" value="q"/></td>
            </tr>
            <tr>
                <th>验证码</th>
                <td>
                    <input name="verifyCode" type="text" class="easyui-validatebox">
                </td>
            </tr>
            <tr>
                <th></th>
                <td>
                    <img src="<%=contextPath%>/system/page/captcha"
                         onclick="this.src='<%=contextPath%>/system/page/captcha?t='+new Date()*1" height="20"
                         width="70"
                         alt="点击我刷新"
                    />
                    <span>${SPRING_SECURITY_LAST_EXCEPTION.message}</span>
                </td>
            </tr>
        </table>
        <security:csrfInput/>
    </form>
</div>
<div style="width:100%;text-align:center;position:fixed;_position:absolute;bottom:10px;_bottom:10px;_margin-top:expression(this.style.pixelHeight+document.documentElement.scrollTop)">
    <a href="http://www.miitbeian.gov.cn/" style="text-decoration: none;color: #999">Copyright 2014-2019 lvtulife.com
        All Rights Reserved 备案号：粤ICP备14085955号</a>
</div>
</body>
</html>