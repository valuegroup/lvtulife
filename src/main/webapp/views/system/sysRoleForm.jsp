<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String tableName = "角色";%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">

        var baseUrl = sysExt.apiHeadSystem + '/role';
        var pageTableName = '<%=tableName%>';

        var pageFormDataLoadCustom = function (data) {
        }

        var submitForm = function ($dialog, $grid, $pjq, ifRepeat) {
            if ($('form').form('validate')) {
                disableToolbarButton(true);//禁用
                $.post(getUrl(), sysExt.serializeObject($('form')), function (result) {
                    if (result.success) {
                        unselectAllAndReload($grid, false);
                        if (ifRepeat) {
                            $('form').form('reset');
                        } else {
                            $dialog.dialog('destroy');
                        }
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
        <tr>
            <th>角色名称</th>
            <td><input name="roleName" class="easyui-textbox" data-options="required:true"/></td>
        </tr>
        <tr>
            <th>角色状态</th>
            <td><input name="rstatus" class="easyui-combobox" data-options="data:sysExt.getCombos('rstatus'),valueField:'value',textField:'label',required:true,editable:false"/></td>
        </tr>
        <tr>
            <th>角色顺序</th>
            <td><input name="sort" class="easyui-numberspinner" value="100"/></td>
        </tr>
        <tr>
            <th>角色描述</th>
            <td>
                <input id="bewrite" name="bewrite" class="easyui-textbox" data-options="multiline:true" style="height:100px"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>