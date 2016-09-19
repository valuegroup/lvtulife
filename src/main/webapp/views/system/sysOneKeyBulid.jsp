<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String tableName = "代码一键生成";%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">
        var grid;
        var isTreeGrid = false;
        var baseUrl = sysExt.apiHeadSystem + '/oneKeyBulid';
        var pageTableName = '<%=tableName%>';


        /*校验是否单选*/
        var checkOnlyFun = function () {
            var selectSize = 0;
            if (isTreeGrid) {
                selectSize = grid.treegrid('getSelections').length;
            } else {
                selectSize = grid.datagrid('getSelections').length;
            }
            if (selectSize > 1) {
                parent.mainShowSlide('请去除多选模式并选择一条记录进行操作!');
                return false;
            }
            return true;
        }

        /*通用获得当前选中节点*/
        var getNode = function () {
            if (isTreeGrid) {
                return grid.treegrid('getSelected');
            } else {
                return grid.datagrid('getSelected');
            }
        }



        var bulidFun = function () {
            var ids = [];
            var rows = grid.datagrid('getSelections');

            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                ids.push(row.entityName);
            }
            if (ids.length > 0) {
                var dialog = parent.sysExt.modalDialog({
                    id : 'editFunDialog',
                    title : '选择需要生成的信息',
                    url: baseUrl + '/bulidCheck?ids=' + ids.join(','),
                    width:880,
                    height:350,
                    toolbar : [ {
                        id : 'editFunSaveButton',
                        text : '一键生成',
                        iconCls:'ext-icon-page_white_code',
                        handler : function() {
                            dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                        }
                    }]
                });
            }
        };

        var messagerAlert = function (result) {
            if (result.success) {
                $.messager.alert('提示', '代码:' + result.code + '</br>内容:' + result.info, 'info');
            } else {
                $.messager.alert('提示', '代码:' + result.code + '</br>内容:' + result.info + '</br>详细:' + result.detail, 'error');
            }
        }

        var unselectAllAndReload = function () {
            if (isTreeGrid) {
                grid.treegrid('unselectAll');
                grid.treegrid('reload');
            } else {
                grid.datagrid('unselectAll');
                grid.datagrid('reload');
            }
        }

        var singleSelFun = function () {
            if (isTreeGrid) {
                grid.treegrid('clearSelections');
                grid.treegrid({singleSelect: !$('#singleSelBtn').linkbutton('options').selected});
            } else {
                grid.datagrid('clearSelections');
                grid.datagrid({singleSelect: !$('#singleSelBtn').linkbutton('options').selected});
            }
        };

        var beforeLoadFun = function (param) {
            parent.$.messager.progress({text: '数据加载中....'});
        }

        var loadSuccessFun = function (data) {
            $('.iconImg').attr('src', sysExt.pixel_0);
            parent.$.messager.progress('close');
        }

        var yesOrNoFun = function (value) {
            return value ? "存在" : "<font color='#a9a9a9'>不存在</font>";
        }

        /*通用基础按钮生成方法*/
        $(function () {

            grid = $('#grid').datagrid({
                title: '',
                url: baseUrl + '/gridAll',
                striped: true,
                rownumbers: true,
                singleSelect: false,//单选多选
                idField: 'entityName',
                sortName: 'entityName',
                sortOrder: 'asc',
                toolbar: '#toolbar',
                columns: [[
                    {width: '150', title: '实体类名称', field: 'entityName'},
                    {width: '250', title: '注释', field: 'tableComment'},
                    {width: '70',  title: '模块', field: 'modular'},
                    {width: '80',  title: '控制层', field: 'hasController', formatter: function (value, row, index) {return yesOrNoFun(value);}},
                    {width: '80',  title: '控制层接口', field: 'hasApiController', formatter: function (value, row, index) {return yesOrNoFun(value);}},
                    {width: '80',  title: '业务层接口', field: 'hasService', formatter: function (value, row, index) {return yesOrNoFun(value);}},
                    {width: '80',  title: '业务层实现', field: 'hasServiceImpl', formatter: function (value, row, index) {return yesOrNoFun(value);}},
                    {width: '80',  title: '持久层接口', field: 'hasDao', formatter: function (value, row, index) {return yesOrNoFun(value);}},
                    {width: '80',  title: '持久层实现', field: 'hasDaoImpl', formatter: function (value, row, index) {return yesOrNoFun(value);}},
                    {width: '80',  title: '列表页面', field: 'hasPageList', formatter: function (value, row, index) {return yesOrNoFun(value);}},
                    {width: '80',  title: '表单页面', field: 'hasPageForm',formatter: function (value, row, index) {return yesOrNoFun(value);}},
                    {width: '80',  title: 'Mapper', field: 'hasMapper',formatter: function (value, row, index) {return yesOrNoFun(value);}},
                    {width: '80',  title: 'MapXML', field: 'hasMapXML',formatter: function (value, row, index) {return yesOrNoFun(value);}},
                    {width: '80',  title: '菜单资源', field: 'hasMenuResource',formatter: function (value, row, index) {return yesOrNoFun(value);}}
                ]],
                onBeforeLoad: function (param) {
                    beforeLoadFun(param);
                },
                onLoadSuccess: function (data) {
                    loadSuccessFun(data);
                }
            });
        });
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div id="toolbar" style="display: none;">
    <div class="easyui-panel" style="width:100%;" data-options="border:false">
        <form id="searchForm">
            <ul class="sysToolBarUl">
                <li><a onclick="bulidFun();" data-options="iconCls:'ext-icon-page_white_code',plain:true" href="javascript:void(0);" class="easyui-linkbutton">构建选项</a></li>
            </ul>
        </form>
    </div>
</div>
<div data-options="region:'center',fit:true,border:false">
    <table id="grid" data-options="fit:true,border:false"></table>
</div>
</body>
</html>