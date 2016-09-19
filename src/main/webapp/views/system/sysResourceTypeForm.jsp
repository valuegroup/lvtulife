<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String tableName = "资源类型";%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">
        var baseUrl = sysExt.apiHeadSystem + '/resourceType';
        var pageTableName = '<%=tableName%>';

        var submitForm = function ($dialog, $grid, $pjq, ifRepeat) {
            if ($('form').form('validate')) {
                disableToolbarButton(true);//禁用
                $.post(getUrl(), sysExt.serializeObject($('form')), function (result) {
                    if (result.success) {
                        unselectAllAndReload($grid,false);
                        if(ifRepeat){
                            clearIcon();
                            $('form').form('reset');
                        }else{
                            $dialog.dialog('destroy');
                        }
                    } else {
                        messagerAlert(result);
                    }
                    disableToolbarButton(false);//启用
                }, 'json');
            }
        };

        var clearIcon = function(){
            $('#iconCls').val('');$('#iconCls').attr('class','');
        }

        var showIcons = function () {
            var dialog = parent.sysExt.modalDialog({
                title: '浏览小图标',
                url: sysExt.apiHeadSystem + '/page/style/icons',
                buttons: [{
                    text: '确定',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.selectIcon(dialog, $('#iconCls'));
                    }
                }]
            });
        };
        function getSort() {
            parent.$.messager.progress({
                text: '数据加载中....'
            });
            //依据当前父节点，从数据库中获取最大的排序字段
            $.get(baseUrl + '/getResTypeSeq', {}, function (result) {
                $('#sysrestype_sort').numberspinner('setValue', result.data);
                parent.$.messager.progress('close');
            }, 'json');
        }

        var pageFormDataLoadCustom = function(result){
            $('#iconCls').attr('class', result.iconCls);//设置背景图标
        }

    </script>
    <jsp:include page="/views/include/incPageFormD.jsp"/>
</head>
<body>
<form method="post" class="formForm">
    <table class="formTable" style="width: 100%;">
        <tr>
            <th><span class="tableTitle"><%=tableName%></span></th>
            <td><div class="lineStyle"></div></td>
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
            <th>类型名称</th>
            <td><input name="typeName" class="easyui-textbox" data-options="required:true"/></td>
        </tr>
        <tr>
            <th>英文标识</th>
            <td><input name="typeMark" class="easyui-textbox" data-options="required:true"/></td>
        </tr>
        <tr>
            <th>面板图标</th>
            <td><input id="iconCls" name="iconCls" readonly="readonly" style="padding-left: 18px; width: 134px;"/>&nbsp;<img class="iconImg ext-icon-zoom imgMiddle" onclick="showIcons();" title="浏览图标"/>&nbsp;<img class="iconImg icon-clear imgMiddle" onclick="clearIcon()" title="清空" /></td>
        </tr>
        <tr>
            <th>面板描述</th>
            <td><input name="bewrite" class="easyui-textbox" data-options="required:true"/></td>
        </tr>
        <tr>
            <th>模块标题</th>
            <td><input name="panelTitle" class="easyui-textbox"/></td>
        </tr>
        <tr>
            <th>顺序</th>
            <td>
                <input id="sysrestype_sort" name="sort" class="easyui-numberspinner"
                       data-options="min:0,max:100000" style="width: 100px;"/>&nbsp;<img
                    class="iconImg ext-icon-zoom imgMiddle" onclick="getSort();" title="获取顺序"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>