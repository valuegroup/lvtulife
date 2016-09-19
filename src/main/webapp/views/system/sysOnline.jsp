<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String tableName = "用户登录监控";%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">
        var grid;
        var isTreeGrid = false;
        var baseUrl = sysExt.apiHeadSystem + '/online';
        var pageTableName = '<%=tableName%>';

        $(function () {
            grid = $('#grid').datagrid({
                title: '',
                url: baseUrl + '/grid',
                striped: true,
                rownumbers: true,
                pagination: true,
                singleSelect: true,
                idField: 'id',
                sortName: 'createdDt',
                sortOrder: 'desc',
                pageSize: 100,
                pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500, 1000],
                frozenColumns: [[{
                    width: '100',
                    title: '登录名',
                    field: 'loginName',
                    sortable: true
                }, {
                    width: '300',
                    title: 'IP地址',
                    field: 'ip',
                    sortable: true
                }]],
                columns: [[{
                    width: '150',
                    title: '创建时间',
                    field: 'createdDt',
                    sortable: true
                }, {
                    width: '100',
                    title: '类别',
                    field: 'olType',
                    sortable: true,
                    align: 'center',
                    formatter: function (value, row, index) {
                        return sysExt.getComboValue('olType', value);
                    }
                }]],
                toolbar: '#toolbar',
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
                <li><input id="query_1" name="QUERY_t#loginName_S_LK" data-options="prompt:'登录名'" style="width: 80px;height:24px;" class="easyui-textbox"/></li>
                <li><input id="query_2" name="QUERY_t#ip_S_LK" data-options="prompt:'IP地址'" style="width: 80px;height:24px;" class="easyui-textbox"/></li>
                <li><select id="query_3" name="QUERY_t#olType_BT_EQ" class="easyui-combobox easyui-textbox" data-options="editable:false,data:sysExt.getCombos('olType'),valueField:'value',textField:'label',panelHeight:'auto',prompt:'监控类型'" style="width: 80px;height:24px;"></select></li>
                <li><input name="QUERY_t#createdDt_D_GE" title="创建时间[开始]" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" data-options="position:'left'" class="Wdate easyui-tooltip sysSearchDateBox"/></li>
                <li><input name="QUERY_t#createdDt_D_LE" title="创建时间[结束]" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" data-options="position:'left'" class="Wdate easyui-tooltip sysSearchDateBox"/></li>
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