<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String tableName = "资源类型";%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">
        var grid;
        var isTreeGrid = false;
        var baseUrl = sysExt.apiHeadSystem + '/user';
        var pageTableName = '<%=tableName%>';

        var grantRoleFun = function () {
            var node = grid.datagrid('getSelected');
            if (node && checkOnlyFun()) {
                var dialog = parent.sysExt.modalDialog({
                    title: '修改角色',
                    url: baseUrl + '/userGrant?id=' + node.id,
                    toolbar: [
                        {
                            id: 'btn1', text: '保存', iconCls: 'ext-icon-shield',
                            handler: function () {
                                dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                            }
                        }]
                    /*,buttons: [{
                     text: '修改',
                     handler: function () {
                     dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                     }
                     }]*/
                });
            }
        };
        var importDataFun = function () {
            var dialog = parent.sysExt.modalDialog({
                title: '批量导入用户信息',
                url: baseUrl + '/userImport',
                height: 450,
                toolbar: [{
                    id: 'btn1', text: '批量导入', iconCls: 'ext-icon-table_add',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                    }
                }]
            });
        };

        var setStatusFun = function (toDo) {
            if ($('#singleSelBtn').linkbutton('options').selected) {
                //批量删除写法
                var ids = [];
                var rows = grid.datagrid('getSelections');
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    ids.push(row.id);
                }
                if (ids.length > 0) {
                    parent.$.messager.confirm('询问', '您确定要操作这[' + rows.length + ']条记录？', function (r) {
                        if (r) {
                            $.post(baseUrl + '/changes', {
                                ids: ids.join(','),
                                todo: toDo
                            }, function (result) {
                                messagerAlert(result);
                                unselectAllAndReload();
                            }, 'json');
                        }
                    });
                }
            } else {
                //单个删除写法
                var node = getNode();
                if (node) {
                    parent.$.messager.confirm('询问', '您确定要操作此记录？', function (r) {
                        if (r) {
                            $.post(baseUrl + '/change', {
                                id: node.id,
                                todo: toDo
                            }, function (result) {
                                messagerAlert(result);
                                unselectAllAndReload();
                            }, 'json');
                        }
                    });
                } else {
                    parent.mainShowSlide();
                }
            }
        };

        var exportDataFun = function () {
            //简单方式
            $('#searchForm').form('submit', {
                url: baseUrl + '/exportFile'
            });

            parent.mainShowSlide('处理完毕,正在新建下载任务,请耐心等待！');
        };

        var photoformatter = function (value, row, index) {
            if (value) {
                return sysExt.formatString('<span title="{0}">{1}</span>', value, value);
            }
        }

        $(function () {
            grid = $('#grid').datagrid({
                title: '',
                url: baseUrl + '/grid',
                striped: true,
                rownumbers: true,
                pagination: true,
                singleSelect: true,//单选多选
                idField: 'id',
                sortName: 'id',
                sortOrder: 'asc',
                pageSize: 20,
                pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
                toolbar: '#toolbar',
                frozenColumns: [[{width: '15%', title: '登录名', field: 'loginName', sortable: true},
                    {width: '15%', title: '姓名', field: 'userName', sortable: true}]],
                columns: [[{width: '15%', title: '创建时间', field: 'createdDt', sortable: true},
                    {width: '15%', title: '修改时间', field: 'updatedDt', sortable: true},
                    {
                        width: '5%', title: '性别', field: 'sex', sortable: true, align: 'center', formatter: function (value, row, index) {
                        return sysExt.getComboValue('sex', value);
                    }
                    },
                    {
                        width: '5%', title: '管理员', field: 'isSuper', sortable: true, align: 'center', formatter: function (value, row, index) {
                        return sysExt.getComboValue('yesOrNo', value);
                    }
                    },
                    {width: '10%', title: '年龄', field: 'age', hidden: true},
                    {width: '30%', title: '照片', field: 'photo', formatter: photoformatter}]],
                onBeforeLoad: function (param) {
                    beforeLoadFun(param);
                },
                onLoadSuccess: function (data) {
                    loadSuccessFun(data);
                },
                onDblClickRow: showFun
            });
        });
    </script>
    <jsp:include page="/views/include/incPageListD.jsp"/>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div id="toolbar" style="display: none;">
    <div class="easyui-panel" style="width:100%;" data-options="border:false">
        <form id="searchForm">
            <ul class="sysToolBarUl">
                <li><a id="addBtn"></a></li>
                <li><a id="editBtn"></a></li>
                <li><a onclick="grantRoleFun();" data-options="iconCls:'ext-icon-user',plain:true" href="javascript:void(0);" class="easyui-linkbutton">角色授予</a></li>
                <li><a id="moreBtn"></a></li>
                <div id="mmore">
                    <div id="showBtn"></div>
                    <div class="menu-sep"></div>
                    <div id="delBtn"></div>
                    <div data-options="iconCls:'ext-icon-table_add'" onclick="importDataFun();">导入</div>
                    <div data-options="iconCls:'ext-icon-table_go'" onclick="exportDataFun();">导出</div>
                    <div data-options="iconCls:'ext-icon-tux'" onclick="setStatusFun(1);">设置超级管理员</div>
                    <div data-options="iconCls:'ext-icon-stop'" onclick="setStatusFun(2);">用户状态（禁用/启动）</div>
                </div>
                <li><a id="singleSelBtn"></a></li>
                <li><input id="query_1" name="QUERY_t#loginName_S_LK" data-options="prompt:'登录名'" style="width: 80px;height:24px;" class="easyui-textbox"/></li>
                <li><input id="query_2" name="QUERY_t#userName_S_LK" data-options="prompt:'姓名'" style="width: 80px;height:24px;" class="easyui-textbox"/></li>
                <li><select id="query_3" name="QUERY_t#sex_BT_EQ" class="easyui-combobox easyui-textbox" data-options="editable:false,data:sysExt.getCombos('sex'),valueField:'value',textField:'label',panelHeight:'auto',prompt:'性别'" style="width: 80px;height:24px;"></select></li>
                <li><select id="query_6" name="QUERY_t#isSuper_BT_EQ" class="easyui-combobox easyui-textbox" data-options="editable:false,data:sysExt.getCombos('yesOrNo'),valueField:'value',textField:'label',panelHeight:'auto',prompt:'管理员'" style="width: 80px;height:24px;"></select></li>
                <li><input id="query_4" name="QUERY_t#createdDt_D_GE" title="创建时间[开始]" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" data-options="position:'left'" class="Wdate easyui-tooltip sysSearchDateBox"/></li>
                <li><input id="query_5" name="QUERY_t#createdDt_D_LE" title="创建时间[结束]" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" data-options="position:'left'" class="Wdate easyui-tooltip sysSearchDateBox"/></li>
                <li><a id="searchBtn"></a></li>
                <li><a id="resetSearchBtn"></a></li>
            </ul>
        </form>
    </div>
</div>
<div data-options="region:'center',fit:true,border:false">
    <table id="grid" data-options="fit:true,border:false"></table>
</div>
</body>
</html>