<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.Map,java.util.HashMap,org.apache.commons.lang3.StringUtils,com.lvtulife.base.component.constants.SysConstants" %>
<% String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    String path = request.getContextPath();
    String version = "50";
    String easyUIVersion = "1.4.5";
    Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
    Cookie[] cookies = request.getCookies();
    if (null != cookies) {
        for (Cookie cookie : cookies)
            cookieMap.put(cookie.getName(), cookie);
    }
    String easyuiTheme = "metro";//指定如果用户未选择样式，那么初始化一个默认样式
    if (cookieMap.containsKey("easyuiTheme")) {
        Cookie cookie = (Cookie) cookieMap.get("easyuiTheme");
        easyuiTheme = cookie.getValue();
    } %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="Generator" content="lvtulife">
<meta name="Author" content="价值工作室">
<meta name="Keywords" content="旅途生活,lvtulife">
<meta name="Description" content="旅途生活！">
<meta name="MSSmartTagsPreventParsing" content="True" />
<meta http-equiv="MSThemeCompatible" content="Yes"/>
<link href="<%=path%>/static/style/images/travel.ico" rel="icon" type="image/x-icon"/><%-- 浏览器左上角图标 --%>
<link href="<%=path%>/static/style/images/travel.ico" rel="shortcut icon" type="image/x-icon" />
<script type="text/javascript">
    var sysExt = sysExt || {};
    sysExt.contextPath = '<%=path%>';
    sysExt.basePath = '<%=basePath%>';
    sysExt.version = '<%=version%>';
    sysExt.pixel_0 = '<%=path%>/static/style/images/pixel_0.gif';//0像素的背景，一般用于占位
    sysExt.apiHeadBase = '<%=path+SysConstants.URL_HEAD_BASE%>';
    sysExt.apiHeadSystem = '<%=path+SysConstants.URL_HEAD_SYSTEM%>';
    sysExt.apiHeadGtd = '<%=path+SysConstants.URL_HEAD_GTD%>';
</script>
<script src="<%=path%>/static/jslib/My97DatePicker4.8Beta3/My97DatePicker/WdatePicker.js" type="text/javascript"  charset="utf-8"></script><%-- 引入my97日期时间控件 http://www.my97.net/--%>
<%
    // 引入jQuery
    String User_Agent = request.getHeader("User-Agent");
    if (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE") > -1 && (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 6") > -1 || StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 7") > -1 || StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 8") > -1)) {
        out.println("<script src='" + path + "/static/jslib/jquery-1.12.3.min.js' type='text/javascript' charset='utf-8'></script>");
    } else {
        out.println("<script src='" + path + "/static/jslib/jquery-2.2.3.min.js' type='text/javascript' charset='utf-8'></script>");
    }
%>
<script src="<%=path%>/static/jslib/extension/extJquery.js" type="text/javascript" charset="utf-8"></script><%--图片延迟加载插件--%>
<script src="<%=path%>/static/jslib/jquery.lazyload.min.js" type="text/javascript" charset="utf-8"></script><%-- 引入jquery扩展 --%>
<link href="<%=path%>/static/jslib/jquery-easyui-1.4.5/themes/<%=easyuiTheme%>/easyui.css" id="easyuiTheme" rel="stylesheet" type="text/css"><%-- 引入EasyUI --%>
<link href="<%=path%>/static/jslib/jquery-easyui-1.4.5/themes/icon.css" rel="stylesheet" type="text/css">
<script src="<%=path%>/static/jslib/jquery-easyui-1.4.5/jquery.easyui.min.js" type="text/javascript"  charset="utf-8"></script>
<script src="<%=path%>/static/jslib/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/static/jslib/extension/extEasyUI.js" type="text/javascript" charset="utf-8"></script><%-- 引入easyui扩展 --%>
<link href="<%=path%>/static/style/extIcon.css" rel="stylesheet" type="text/css"><%-- 引入扩展图标 --%>
<link href="<%=path%>/static/style/extCss.css" rel="stylesheet" type="text/css"><%-- 引入自定义样式 --%>
<script src="<%=path%>/static/jslib/extension/extJavascript.js" type="text/javascript" charset="utf-8"></script><%-- 引入javascript扩展 --%>
<script type="text/javascript">
    $(function () {
        $("img").lazyload({
            placeholder: "<%=path%>/style/images/lazyload.gif",
            effect: "fadeIn"//加载图片使用的效果(淡入)
        });
    });
</script>