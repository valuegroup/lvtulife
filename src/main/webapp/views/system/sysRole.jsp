<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String tableName = "角色";%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">
        var grid;
        var isTreeGrid = false;
        var baseUrl = sysExt.apiHeadSystem + '/role';
        var pageTableName = '<%=tableName%>';

        var grantFun = function (id) {
            var node = grid.datagrid('getSelected');
            if (node) {
                var dialog = parent.sysExt.modalDialog({
                    title: '角色授权',
                    url: baseUrl + '/roleGrant?id=' + node.id,
                    toolbar: [
                        {
                            id: 'btn1', text: '授权', iconCls: 'ext-icon-shield',
                            handler: function () {
                                dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                            }
                        },
                        {
                            id: 'btn2', text: '展开', iconCls: 'ext-icon-resultset_next',
                            handler: function () {
                                dialog.find('iframe').get(0).contentWindow.expandAll(dialog, grid, parent.$);
                            }
                        },
                        {
                            id: 'btn3', text: '折叠', iconCls: 'ext-icon-resultset_previous',
                            handler: function () {
                                dialog.find('iframe').get(0).contentWindow.collapseAll(dialog, grid, parent.$);
                            }
                        },
                        {
                            id: 'btn4', text: '全选', iconCls: 'ext-icon-tick',
                            handler: function () {
                                dialog.find('iframe').get(0).contentWindow.checkAll(dialog, grid, parent.$);
                            }
                        },
                        {
                            id: 'btn5', text: '清空', iconCls: 'ext-icon-cross',
                            handler: function () {
                                dialog.find('iframe').get(0).contentWindow.uncheckAll(dialog, grid, parent.$);
                            }
                        },
                        {
                            id: 'btn6', text: '当前全选', iconCls: 'ext-icon-text_indent',
                            handler: function () {
                                dialog.find('iframe').get(0).contentWindow.selectCheckAll(dialog, grid, parent.$);
                            }
                        },
                        {
                            id: 'btn7', text: '当前清空', iconCls: 'ext-icon-text_indent_remove',
                            handler: function () {
                                dialog.find('iframe').get(0).contentWindow.selectuncheckAll(dialog, grid, parent.$);
                            }
                        }
                    ]
                    /*,buttons: [{
                     text: '授权',
                     handler: function () {
                     dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                     }
                     }]*/
                });
            }
        };

        $(function () {
            grid = $('#grid').datagrid({
                title: '',
                url: baseUrl + '/gridAll',
                striped: true,
                rownumbers: true,
                singleSelect: true,
                idField: 'id',
                sortName: 'sort',
                sortOrder: 'asc',
                frozenColumns: [[{width: '100', title: '角色名称', field: 'roleName', sortable: true}]],
                columns: [[
                    {width: '100', title: '角色名称EN', field: 'roleNameEn'},
                    {width: '300', title: '资源描述', field: 'bewrite'},
                    {
                        width: '60', title: '资源状态', field: 'rstatus', align: 'center', formatter: function (value, row, index) {
                        return sysExt.getComboValue('rstatus', value);
                    }
                    },
                    {
                        width: '60', title: '数据来源', field: 'source', align: 'center', formatter: function (value, row, index) {
                        return sysExt.getComboValue('dataSource', value);
                    }
                    },
                    {width: '150', title: '创建时间', field: 'createdDt', sortable: true},
                    {width: '150', title: '修改时间', field: 'updatedDt', sortable: true},
                    {width: '60', title: '排序', field: 'sort', hidden: true, sortable: true}]], <%--, {
				title : '操作',
				field : 'action',
				width : '100',
				formatter : function(value, row) {
					var str = '';
					var temp = "&nbsp;";
					<%if (securityUtil.havePermission("L")) {%>
						str += temp+sysExt.formatString('<img class="iconImg ext-icon-note" title="查看" onclick="showFun(\'{0}\');"/>', row.rid);
					<%}%>
					<%if (securityUtil.havePermission("U")) {%>
						str += temp+sysExt.formatString('<img class="iconImg ext-icon-note_edit" title="编辑" onclick="editFun(\'{0}\');"/>', row.rid);
					<%}%>
					
					<%if (securityUtil.havePermission("D")) {%>
						str += temp+sysExt.formatString('<img class="iconImg ext-icon-note_delete" title="删除" onclick="removeFun(\'{0}\');"/>', row.rid);
					<%}%>
					<%if (securityUtil.havePermission("R")) {%>
						str += temp+sysExt.formatString('<img class="iconImg ext-icon-shield" title="设置资源权限" onclick="grantFun(\'{0}\');"/>', row.rid);
					<%}%>
					return str;
				}
			}] ], --%>
                toolbar: '#toolbar',
                onBeforeLoad: function (param) {
                    beforeLoadFun(param);
                },
                onLoadSuccess: function (data) {
                    loadSuccessFun(data);
                },
                onDblClickRow: showFun
            });

            $('#grantBtn').text('授权').linkbutton({iconCls: 'ext-icon-shield', plain: true, onClick: grantFun}).attr('href', 'javascript:void(0);');
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
                <li><a id="moreBtn"></a></li>
                <li><a id="grantBtn"></a></li>
                <div id="mmore">
                    <div id="showBtn"></div>
                    <div class="menu-sep"></div>
                    <div id="delBtn"></div>
                </div>
                <li><a id="singleSelBtn"></a></li>

                <li><input id="query_1" name="QUERY_t#roleName_S_LK" data-options="prompt:'资源名称'" style="width: 80px;height:24px;" class="easyui-textbox"/></li>
                <li><select id="query_2" name="QUERY_t#rstatus_BT_EQ" class="easyui-combobox easyui-textbox" data-options="editable:false,data:sysExt.getCombos('rstatus'),valueField:'value',textField:'label',panelHeight:'auto',prompt:'角色状态'" style="width: 80px;height:24px;"></select></li>
                <li><a id="searchBtn"></a></li>
                <li><a id="resetSearchBtn"></a></li>
                <%--<li><imput id="searchBox" class="easyui-searchbox" style="width: 150px" data-options="searcher:function(value,name){grid.datagrid('load',{'QUERY_t#roleName_S_LK':value});},prompt:'搜索角色名称'"/></li>
                <li><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchBox').searchbox('setValue','');grid.datagrid('load',{});">清空查询</a></li>--%>
            </ul>
        </form>
    </div>
</div>
<div data-options="region:'center',fit:true,border:false">
    <table id="grid" data-options="fit:true,border:false"></table>
</div>
</body>
</html>