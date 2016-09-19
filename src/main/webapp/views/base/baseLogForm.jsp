<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String tableName = "系统日志";%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">

        var baseUrl = sysExt.apiHeadBase + '/log';
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
        <tr><th>日志类型</th><td><input name="logType" class="easyui-combobox" data-options="data:sysExt.getCombos('logType'),valueField:'value',textField:'label',required:false,editable:false"/></td></tr>
        <tr><th>方法类型</th><td><input name="methodType" class="easyui-combobox" data-options="data:sysExt.getCombos('method'),valueField:'value',textField:'label',required:false,editable:false"/></td></tr>
        <tr><th>请求方法</th><td><input name="method" class="easyui-textbox" data-options="required:false,validType:'length[0,100]'"/></td></tr>
        <tr><th>方法参数</th><td><input name="params" class="easyui-textbox" data-options="required:false,multiline:true,validType:'length[0,255]'" style="height:100px"/></td></tr>
        <tr><th>方法描述</th><td><input name="description" class="easyui-textbox" data-options="required:false,multiline:true,validType:'length[0,255]'" style="height:100px"/></td></tr>
        <tr><th>异常代码</th><td><input name="exCode" class="easyui-textbox" data-options="required:false,multiline:true,validType:'length[0,255]'" style="height:100px"/></td></tr>
        <tr><th>异常信息</th><td><input name="exDetail" class="easyui-textbox" data-options="required:false,multiline:true,validType:'length[0,255]'" style="height:100px"/></td></tr>
        <tr><th>请求地址</th><td><input name="requestIp" class="easyui-textbox" data-options="required:false,validType:'length[0,50]'"/></td></tr>
        <tr><th>操作时间</th><td><input name="createdDt" class="Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td></tr>
    </table>
</form>
</body>
</html>
