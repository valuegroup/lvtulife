<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String tableName = "角色授予";%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">
        var baseUrl = sysExt.apiHeadSystem + '/user';
        var pageTableName = '<%=tableName%>';

        var submitForm = function ($dialog, $grid, $pjq) {
            disableToolbarButton(true);//禁用
            var nodes = $('#tree').tree('getChecked', ['checked', 'indeterminate']);
            var ids = [];
            for (var i = 0; i < nodes.length; i++) {
                ids.push(nodes[i].id);
            }

            if (ids.length == 0) {
                ids.push(0);
            }

            $.post(baseUrl + '/grantUserRole', {
                id: $(':input[name="id"]').val(),
                rids: ids.join(',')
            }, function (result) {
                if (result.success) {
                    $dialog.dialog('destroy');
                } else {
                    messagerAlert(result);
                }
                disableToolbarButton(false);
            }, 'json');
        };

        $(function () {
            parent.$.messager.progress({
                text: '数据加载中....'
            });
            $('#tree').tree({
                url: sysExt.apiHeadSystem + '/role/getRolesTree',
                checkbox: true,
                onLoadSuccess: function (node, data) {
                    $.post(sysExt.contextPath + '/system/role/getUserRoles', {
                        id: $(':input[name="id"]').val()
                    }, function (result) {
                        if (result) {
                            for (var i = 0; i < result.length; i++) {
                                var node = $('#tree').tree('find', result[i].id);
                                if (node) {
                                    var isLeaf = $('#tree').tree('isLeaf', node.target);
                                    if (isLeaf) {
                                        $('#tree').tree('check', node.target);
                                    }
                                }
                            }
                        }
                        parent.$.messager.progress('close');
                    }, 'json');
                }
            });
        });
    </script>
    <jsp:include page="/views/include/incPageCustomD.jsp"/>
</head>
<body>
<input name="id" value="${id}" readonly="readonly" type="hidden"/>
<fieldset>
    <legend>所属角色</legend>
    <ul id="tree"></ul>
</fieldset>
</body>
</html>