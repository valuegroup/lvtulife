<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String tableName = "数据字典";%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">
        var baseUrl = sysExt.apiHeadBase + '/dictionary';
        var pageTableName = '<%=tableName%>';

        var submitForm = function ($dialog, $grid, $pjq, ifRepeat) {
            if ($('form').form('validate')) {
                disableToolbarButton(true);//禁用
                $.post(getUrl(), sysExt.serializeObject($('form')), function (result) {
                    if (result.success) {
                        unselectAllAndReload($grid, false);
                        if (ifRepeat) {
                            clearIcon();
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
            <th>中文名</th>
            <td><input name="tnameCn" class="easyui-validatebox" data-options="required:true"/>
            </td>
        </tr>
        <tr>
            <th>英文名</th>
            <td><input name="tnameEn" class="easyui-validatebox" data-options="required:true"/>
            </td>
        </tr>
        <tr>
            <th>中文内容</th>
            <td><input name="tvalueCn" class="easyui-validatebox" data-options="required:true"/>
            </td>
        </tr>
        <tr>
            <th>英文内容</th>
            <td><input name="tvalueEn" class="easyui-validatebox" data-options="required:true"/>
            </td>
        </tr>
        <tr>
            <th>类型</th>
            <td><input name="dtype" class="easyui-combobox"
                       data-options="data:getCombos('type'),valueField:'value',textField:'label',panelHeight:'auto',required:true,editable:false"/>
            </td>
        </tr>
        <tr>
            </td>
            <th>排序</th>
            <td><input name="sort" id="sort" class="easyui-numberspinner" data-options="min:0,max:100,editable:true"
                       value='1'/>
            </td>
        </tr>
        <tr>
            <th>备注</th>
            <td><input name="remark"/>
            </td>
        </tr>
        <tr>
            <th>创建时间</th>
            <td><input name="createdDt" class="Wdate" disabled
                       onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
            </td>
        </tr>
        <tr>
            <th>最后修改时间</th>
            <td><input name="updatedDt" class="Wdate" disabled
                       onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
