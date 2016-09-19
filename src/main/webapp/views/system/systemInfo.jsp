<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">
        $(function () {
            $('#systemInfo_tab td').css({"text-align": "center", "vertical-align": "middle"});
        });
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false" style="text-align:center;font-size:14px;color:#444;margin: 0;padding: 0;">
<div data-options="region:'center',fit:true,border:false">
    <table id="systemInfo_tab" style="width: 100%;margin: 0;padding: 0;">
        <tr>
            <td>
                <div style="margin:40px;">
                    <img src="<%=request.getContextPath()%>/static/style/images/logo.png" style="width: 180px;height: 60px;">&nbsp;&nbsp;&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/static/style/images/vsLogo.png" style="width: 160px;height: 60px;">
                </div>
            </td>
        </tr>
        <tr>
            <td>版本号：1.2.30_d</td>
        </tr>
        <tr>
            <td>ValueStudio出品</td>
        </tr>
        <tr>
            <td><a href="http://www.lvtulife.com" target="_blank">http://www.lvtulife.com</a></td>
        </tr>
    </table>
</div>
</body>
</html>