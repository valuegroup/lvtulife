<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String tableName = "角色授权";%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">

        var baseUrl = sysExt.apiHeadSystem + '/role';
        var pageTableName = '<%=tableName%>';

        var submitForm = function ($dialog, $grid, $pjq) {
            disableToolbarButton(true);//禁用

            var nodes = $('#tree').tree('getChecked', ['checked', 'indeterminate']);
            var ids = [];
            var typeids = [];
            for (var i = 0; i < nodes.length; i++) {
                var node = nodes[i];
                ids.push(node.id);
                if (typeof(node.attributes.typeId) != "undefined" && node.attributes.typeId != "" && node.attributes.typeId != "null")
                    typeids.push(node.attributes.typeId);
            }

            $.post(baseUrl + '/grantRoleRes', {
                rid: $(':input[name="rid"]').val(),
                resIds: ids.join(','),
                typeIds: sysExt.arrayUnique(typeids).join(',')
            }, function (result) {
                if (result.success) {
                    $dialog.dialog('destroy');
                } else {
                    messagerAlert(result);
                }
                disableToolbarButton(false);//启用
            }, 'json');
        };

        $(function () {
            parent.$.messager.progress({
                text: '数据加载中....'
            });

            //取出资源树
            $('#tree').tree({
                url: sysExt.apiHeadSystem + '/resource/getResourcesTree',
                //idField : 'resId',//这样设置无法使用find,因此使用默认id,在实体类中添加一个虚拟的字段id
                parentField: 'pid',
                checkbox: true,
                cascadeCheck: false,
                onCheck: function (node, checked) {
                    //若子节点选中了,父节点必须选中
                    var nodeParent = $('#tree').tree('getParent', node.target);
                    if (nodeParent) {
                        if (checked) {
                            //当前节点选中，其父节点一定要选中
                            $('#tree').tree('check', nodeParent.target);
                        } else {
                            //当前节点未选中，需判断当前节点的其他兄弟节点是否都未选中，若都未选中则父节点不选中
                            var nodeChildrens = $('#tree').tree('getChildren', nodeParent.target);
                            if (nodeChildrens) {
                                var isCheck = 'uncheck';
                                for (var i = 0; i < nodeChildrens.length; i++) {
                                    if (nodeChildrens[i].checked)
                                        isCheck = 'check';
                                }
                                $('#tree').tree(isCheck, nodeParent.target);
                            }
                        }
                    }
                },
                onLoadSuccess: function (node, data) {
                    //将当前角色和资源权限点关联
                    $.post(baseUrl + '/getRoleAuthPoints', {
                        rid: $(':input[name="rid"]').val()
                    }, function (result) {
                        if (result) {
                            for (var i = 0; i < result.length; i++) {
                                var noteId = result[i].id;
                                var node = $('#tree').tree('find', noteId);
                                if (node) {
                                    $('#tree').tree('check', node.target);
                                }
                            }
                        }
                        parent.$.messager.progress('close');
                    }, 'json');
                }
            });
        });


        function collapseAll() {
            var node = $('#tree').tree('getSelected');
            if (node) {
                $('#tree').tree('collapseAll', node.target);
            } else {
                $('#tree').tree('collapseAll');
            }
        }

        function expandAll() {
            var node = $('#tree').tree('getSelected');
            if (node) {
                $('#tree').tree('expandAll', node.target);
            } else {
                $('#tree').tree('expandAll');
            }
        }

        function checkAll() {
            var nodes = $('#tree').tree('getChecked', 'unchecked');
            for (var i = 0; i < nodes.length; i++) {
                $('#tree').tree('check', nodes[i].target);
            }
        }

        function uncheckAll() {
            var nodes = $('#tree').tree('getChecked', 'checked');
            for (var i = 0; i < nodes.length; i++) {
                $('#tree').tree('uncheck', nodes[i].target);
            }
        }

        function selectCheckAll() {
            var selNode = $('#tree').tree('getSelected');
            if (selNode) {
                $('#tree').tree('check', selNode.target);
                var nodes = $('#tree').tree('getChildren', selNode.target);
                for (var i = 0; i < nodes.length; i++) {
                    $('#tree').tree('check', nodes[i].target);
                }
            }
        }

        function selectuncheckAll() {
            var selNode = $('#tree').tree('getSelected');
            if (selNode) {
                $('#tree').tree('uncheck', selNode.target);
                var nodes = $('#tree').tree('getChildren', selNode.target);
                for (var i = 0; i < nodes.length; i++) {
                    $('#tree').tree('uncheck', nodes[i].target);
                }
            }
        }
    </script>
    <jsp:include page="/views/include/incPageCustomD.jsp"/>
</head>
<body>
<input name="rid" value="${id}" readonly="readonly" type="hidden"/>
<%--<div id="toolbar">
    <table>
        <tr>
            <td>
                <a onclick="expandAll();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-resultset_next'">展开</a>
                <a onclick="collapseAll();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-resultset_previous'">折叠</a>
            </td>
            <td>
                <div class="datagrid-btn-separator"></div>
            </td>
            <td>
                <a onclick="checkAll();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-tick'">全选</a>
                <a onclick="uncheckAll();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-cross'">清空</a>
            </td>
            <td>
                <div class="datagrid-btn-separator"></div>
            </td>
            <td>
                <a onclick="selectCheckAll();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-text_indent'">当前全选</a>
                <a onclick="selectuncheckAll();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-text_indent_remove'">当前清空</a>
            </td>
        </tr>
    </table>
</div>--%>
<ul id="tree" style="margin-top: 5px;margin-left: 10px;"></ul>
</body>
</html>