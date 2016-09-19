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
        var baseUrl = sysExt.apiHeadSystem + '/resourceType';
        var pageTableName = '<%=tableName%>';

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
                frozenColumns: [[{width: '100', title: '资源类型名称', field: 'typeName',}]],
                columns: [[{width: '100', title: '类型标识', field: 'typeMark',},
                    {width: '100', title: '描述', field: 'bewrite'},
                    {width: '150', title: '图标', field: 'iconCls'},
                    {width: '200', title: '面板标题', field: 'panelTitle'},
                    {width: '60', title: '排序', field: 'sort', align: 'center', sortable: true}]],
                toolbar: '#toolbar',
                onBeforeLoad: function (param) {beforeLoadFun(param);},
                onLoadSuccess: function (data) {loadSuccessFun(data);},
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
                <li><a id="moreBtn"></a></li>
                <div id="mmore">
                    <div id="showBtn"></div>
                    <div class="menu-sep"></div>
                    <div id="delBtn"></div>
                </div>
                <li><a id="singleSelBtn"></a></li>
                <li><input id="query_1" name="QUERY_t#typeName_S_LK" data-options="prompt:'类型名称'" style="width: 80px;height:24px;" class="easyui-textbox"/></li>
                <%--<li><input id="searchBox" class="easyui-searchbox" style="width: 150px" data-options="searcher:function(value,name){grid.datagrid('load',{'QUERY_t#typeName_S_LK':value});},prompt:'搜索资源类型名称'"/></li>
                <li><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchBox').searchbox('setValue','');grid.datagrid('load',{});">清空查询</a></li>--%>
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