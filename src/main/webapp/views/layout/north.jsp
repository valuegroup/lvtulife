<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.lvtulife.system.component.vo.SessionInfo" %>
<%@ page import="com.lvtulife.base.utils.OsType" %>
<%
    String contextPath = request.getContextPath();
    SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
    boolean isWindows = OsType.isWindows();
%>
<script type="text/javascript" charset="utf-8">
    /*var lockWindowFun = function () {
     $.post(sysExt.apiHeadSystem + '/sysUser/logout', function (result) {
            $('#loginDialog').dialog('open');
        }, 'json');
     };*/
    var logoutFun = function () {
        /*$.post(sysExt.apiHeadSystem + '/sysUser/logout', function (result) {
         location.replace(sysExt.contextPath + '/index.jsp');
         }, 'json');*/
        location.replace(sysExt.contextPath + '/logout');
    };
    var showMyInfoFun = function () {
        var dialog = parent.sysExt.modalDialog({
            title: '我的信息',
            url: sysExt.apiHeadSystem + '/user/userInfo',
            buttons: [{
                text: '确定',
                handler: function () {
                    dialog.dialog('close');
                }
            }]
        });
    };
    var showSystemInfoFun = function () {
        var dialog = parent.sysExt.modalDialog({
            width: 520,
            height: 320,
            title: '关于旅途生活',
            url: sysExt.apiHeadSystem + '/page/info',
            buttons: [{
                text: '确定',
                handler: function () {
                    dialog.dialog('close');
                }
            }]
        });
    };
    var updateInfoFun = function () {
        var dialog = parent.sysExt.modalDialog({
            title: '更新日志',
            width: 900,
            height: 620,
            url: sysExt.apiHeadSystem + '/page/version',
            buttons: [{
                text: '确定',
                handler: function () {
                    dialog.dialog('close');
                }
            }]
        });
    };
    $(function () {
        var urlStr = '<%=sessionInfo.getUser().getPhoto() %>';
        if (urlStr == 'null' || urlStr == '') {
            urlStr = '/static/style/images/userPhotoDefult.jpg';
        }
        $('#photo').attr('src', sysExt.contextPath + urlStr);
    });

    function toolsCmd(name) {
        var url = sysExt.contextPath + '/views/include/toolsCmd.jsp';
        $.post(url, {'toolName': name});
    }
</script>
<!-- 头像 -->
<%--<div id="userPhoto" style="position:absolute;right:2px;top:4px;border:1px #F8F8F8 solid">
	<img id="photo" src="" style="width: 57px; height: 57px;">
</div>
<div style="position: absolute; right: 64px; top: 0px;">--%>
<div style="position: absolute; right: 2px; top: 0px;">
    <a href="javascript:void(0);" class="easyui-menubutton" style="color:#0763C1;" data-options="menu:'#layout_north_Menu',iconCls:'ext-icon-status_online',menuAlign:'right'">
        <%
            if (sessionInfo != null) {
                out.print(com.lvtulife.base.utils.StringUtil.formateString("{0}[{1}]", sessionInfo.getUser().getUserName(), sessionInfo.getUser().getLoginName()));
            }
        %>
    </a>
</div>
<div id="layout_north_Menu" style="width: 160px; display: none;">
    <div data-options="iconCls:'ext-icon-color_swatch',menuAlign:'right'">
        <span>更换皮肤</span>

        <div>
            <div onclick="sysExt.changeTheme('default');" title="default">default</div>
            <div onclick="sysExt.changeTheme('gray');" title="gray">gray</div>
            <div onclick="sysExt.changeTheme('metro');" title="metro">metro</div>
            <div onclick="sysExt.changeTheme('bootstrap');" title="bootstrap">bootstrap</div>
            <div onclick="sysExt.changeTheme('black');" title="black">black</div>
            <%--
            //jquery-easyui-1.4版本中没有这些样式
            <div class="menu-sep"></div>
            <div onclick="sysExt.changeTheme('metro-blue');" title="metro-blue">metro-blue</div>
            <div onclick="sysExt.changeTheme('metro-gray');" title="metro-gray">metro-gray</div>
            <div onclick="sysExt.changeTheme('metro-green');" title="metro-green">metro-green</div>
            <div onclick="sysExt.changeTheme('metro-orange');" title="metro-orange">metro-orange</div>
            <div onclick="sysExt.changeTheme('metro-red');" title="metro-red">metro-red</div>
            --%>
        </div>
    </div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'ext-icon-information'" onclick="showMyInfoFun();">我的信息</div>
    <div data-options="iconCls:'ext-icon-key'" onclick="$('#passwordDialog').dialog('open');">修改密码</div>
    <div data-options="iconCls:'ext-icon-book'" onclick="">云笔记</div>
    <c:if test="<%=isWindows%>">
        <div data-options="iconCls:'ext-icon-computer'">
            <span>Windows本地工具</span>

            <div>
                <div data-options="iconCls:'ext-icon-calculator'" onclick="toolsCmd('calc');">计算器</div>
                <div onclick="toolsCmd('write');">写字板</div>
                <div onclick="toolsCmd('notepad');">记事本</div>
                <div onclick="toolsCmd('Magnify');">放大镜</div>
                <div onclick="toolsCmd('mspaint');">画板</div>
                <div onclick="toolsCmd('StikyNot');">便签</div>
                <div onclick="toolsCmd('mstsc');">远程桌面连接</div>
                <div onclick="toolsCmd('services.msc');">本地服务设置</div>
                <div onclick="toolsCmd('taskmgr');">任务管理器</div>
                <div onclick="toolsCmd('explorer');">打开资源管理器</div>
            </div>
        </div>
    </c:if>
    <div data-options="iconCls:'ext-icon-date'" onclick="">日历</div>
    <div data-options="iconCls:'ext-icon-cog'" onclick="">设置..</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'ext-icon-help'">
        <span>帮助</span>

        <div>
            <div onclick="" title="">帮助中心</div>
            <div class="menu-sep"></div>
            <div onclick="" title="">客服热线</div>
            <div onclick="" title="">邮件反馈</div>
            <div>
                <span>官方微博</span>

                <div>
                    <div onclick="" title="">腾讯微博</div>
                    <div onclick="" title="">新浪微博</div>
                    <div onclick="" title="">微信</div>
                </div>
            </div>
        </div>
    </div>
    <div data-options="iconCls:'ext-icon-tag_blue'" onclick="showSystemInfoFun();">关于旅途生活</div>
    <div data-options="iconCls:'ext-icon-comments'" onclick="updateInfoFun();">更新日志</div>
    <%--
    <!--直接显示的方式-->
        <div data-options="iconCls:'ext-icon-vector'">
            <span>关于旅途生活</span>
            <div class="menu-content" style="background:#f0f0f0;padding:10px;text-align:left">
                <img src="<%=contextPath %>/style/images/vsLogo.png" style="width:160px;height:60px">
                <p style="font-size:14px;color:#444;text-align:center;">价值工作室出品</p>
                <p style="font-size:14px;color:#444;">http://www.lvtulife.com</p>
            </div>
        </div>
     --%>
    <div class="menu-sep"></div>
    <%--<div data-options="iconCls:'ext-icon-lock'" onclick="lockWindowFun();">锁定窗口</div>--%>
    <div data-options="iconCls:'ext-icon-disconnect'" onclick="logoutFun();">退出系统</div>
</div>