<%@page import="com.lvtulife.base.utils.ConfigUtil" %>
<%@page import="com.lvtulife.base.utils.DateUtil" %>
<%@page import="com.lvtulife.system.component.vo.SessionInfo" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String contextPath = request.getContextPath();
    SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
%>

<!DOCTYPE html>
<html>
<head>
    <title>个人信息</title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">
        $(function () {
            parent.$.messager.progress({
                text: '数据加载中....'
            });

            //取出资源树
            $('#tree').tree({
                url: sysExt.apiHeadSystem + '/resource/getMenusNoType',
                parentField: 'pid',
                onLoadSuccess: function (data) {
                    parent.$.messager.progress('close');
                }
            });
            parent.$.messager.progress('close');
            /*//取出资源权限点并添加资源权限点
             $.post(sysExt.contextPath + '/sys/rolerespri!doNotNeedSecurity_getSessionRoleRespri', function(result) {
             if (result) {
             for (var i = 0; i < result.length; i++) {
             var node = $('#tree').tree('find', result[i].resId);
             //增加除‘访问’权限外的其它权限
             if (node && result[i].priId != 3) {
             $('#tree').tree('append', {
             parent : node.target,
             data : [ {
             id : result[i].resId + "_" + result[i].priId,
             text : result[i].priName,
             iconCls : 'ext-icon-bullet_blue',
             "attributes" : {
             "respriId" : result[i].respriId,
             "typeId" : ''
             }
             } ]
             });
             }
             }
             }
             parent.$.messager.progress('close');
             //设定权限的的显示样式
             $(".ext-icon-bullet_blue").parent().parent().css({
             "float" : "left",
             "width" : "260px"
             })
             $(".tree-expanded").parent().parent().css({
             "clear" : "both"
             });
             $(".tree-collapsed").parent().parent().css({
             "clear" : "both"
             });

             }, 'json');*/
        });
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'center',fit:true,border:false">
    <table style="width: 100%;">
        <tr>
            <td>
                <fieldset>
                    <legend>用户信息</legend>
                    <table class="table2" style="width: 100%;">
                        <tr>
                            <th>用户ID</th>
                            <td><%=sessionInfo.getUser().getId()%>
                            </td>
                            <th>使用IP</th>
                            <td><%=sessionInfo.getUser().getIp()%>
                            </td>
                        </tr>
                        <tr>
                            <th>登录名</th>
                            <td><%=sessionInfo.getUser().getLoginName()%>
                            </td>
                            <th>姓名</th>
                            <td><%=sessionInfo.getUser().getUserName()%>
                            </td>
                        </tr>
                        <tr>
                            <th>性别</th>
                            <td>
                                <%
                                    if (sessionInfo.getUser().getSex() != null && sessionInfo.getUser().getSex().equals("1")) {
                                        out.print("男");
                                    } else {
                                        out.print("女");
                                    }
                                %>
                            </td>
                            <th>年龄</th>
                            <td>
                                <%
                                    if (sessionInfo.getUser().getAge() != null) {
                                        out.print(sessionInfo.getUser().getAge());
                                    }
                                %>
                            </td>
                        </tr>
                        <tr>
                            <th>创建时间</th>
                            <td><%=DateUtil.dateToString(sessionInfo.getUser().getCreatedDt())%>
                            </td>
                            <th></th>
                            <td></td>
                        </tr>
                    </table>
                </fieldset>
            </td>
        </tr>
        <tr>
            <td>
                <fieldset>
                    <legend>用户角色信息</legend>
                    <table class="table2" style="width: 100%;">
                        <thead>
                        <tr>
                            <th>角色</th>
                        </tr>
                        </thead>
                        <tr>
                            <td valign="top">
                                <ul>
                                    <c:forEach items="${userRoles}" var="m">
                                        <li title='${m}'>${m}</li>
                                    </c:forEach>
                                </ul>
                                <%--<%
                                    //第二种方式
                                    List<String> userDetails = (List<String>)request.getAttribute("userRoles");
                                    out.println("<ul>");
                                    for (String role : userDetails) {
                                        out.println(StringUtil.formateString("<li title='{0}'>{0}</li>", role));
                                    }
                                    out.println("</ul>");
                                %>--%>
                            </td>
                        </tr>
                    </table>
                </fieldset>
            </td>
        </tr>
        <tr>
            <td>
                <fieldset>
                    <legend>资源权限信息</legend>
                    <table class="table2" style="width: 100%;">
                        <thead>
                        <tr>
                            <th>权限</th>
                        </tr>
                        </thead>
                        <tr>
                            <td valign="top">
                                <ul id="tree"></ul>
                            </td>
                        </tr>
                    </table>
                </fieldset>
            </td>
        </tr>
    </table>
</div>
</body>
</html>