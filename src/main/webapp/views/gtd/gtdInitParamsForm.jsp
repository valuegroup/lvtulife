<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String tableName = "业务初始参数表";%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">

        var baseUrl = sysExt.apiHeadGtd + '/initParams';
        var pageTableName = '<%=tableName%>';

        var pageFormDataLoadCustom = function (data) {
        }

        var submitForm = function ($dialog, $grid, $pjq) {
            if ($('form').form('validate')) {
                disableToolbarButton(true);//禁用
                $.post(getUrl(), sysExt.serializeObject($('form')), function (result) {
                    if (result.success) {
                        unselectAllAndReload($grid, false);
                        $dialog.dialog('destroy');
                    } else {
                        messagerAlert(result);
                    }
                    disableToolbarButton(false);//启用
                }, 'json');
            }
        };

    </script>
    <jsp:include page="/views/include/incPageFormD.jsp"/>
</head>
<body>
<form method="post" class="formForm">
    <table class="formTable" style="width: 100%;">
        <tr>
            <th><span class="tableTitle"><%=tableName%></span></th>
            <td>
                <div class="lineStyle"></div>
            </td>
        </tr>
        <tr id="primaryKeyTr">
            <th>主键</th>
            <td>
                <a class="easyui-tooltip" title="自动生成,无需编辑" data-options="position:'bottom'">
                    <input name="id" value="${id}" class="easyui-textbox" data-options="readonly:true"/>
                </a>
            </td>
        </tr>
        <tr><th>初始年份</th><td><input name="initYear" class="easyui-numberbox" data-options="required:true,min:0,max:999999999"/></td></tr>
        <tr><th>当前阶段</th><td><input name="nowStage" class="easyui-combobox" data-options="data:sysExt.getCombos('yesOrNo'),valueField:'value',textField:'label',required:true,editable:false"/></td></tr>
        <tr><th>当前年度</th><td><input name="nowYear" class="easyui-numberbox" data-options="required:true,min:0,max:999999999"/></td></tr>
        <tr><th>当前季度</th><td><input name="nowQuarter" class="easyui-numberbox" data-options="required:true,min:0,max:999999999"/></td></tr>
        <tr><th>当前月度</th><td><input name="nowMonth" class="easyui-numberbox" data-options="required:true,min:0,max:999999999"/></td></tr>
        <tr><th>当前月度第几周</th><td><input name="nowWeekMonth" class="easyui-numberbox" data-options="required:true,min:0,max:999999999"/></td></tr>
        <tr><th>当前年度第几周</th><td><input name="nowWeekYear" class="easyui-numberbox" data-options="required:true,min:0,max:999999999"/></td></tr>
        <tr><th>当前天数</th><td><input name="nowDay" class="easyui-numberbox" data-options="required:true,min:0,max:999999999"/></td></tr>
        <tr><th>自定义阶段信息（json）</th><td><input name="customStage" class="easyui-textbox" data-options="required:false,multiline:true,validType:'length[0,255]'" style="height:100px"/></td></tr>
        <tr><th>自定义常用时段信息（json）</th><td><input name="customTimeInterval" class="easyui-textbox" data-options="required:false,multiline:true,validType:'length[0,255]'" style="height:100px"/></td></tr>
    </table>
</form>
</body>
</html>
