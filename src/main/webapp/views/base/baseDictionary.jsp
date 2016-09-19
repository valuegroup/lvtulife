<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String tableName = "数据字典";%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">
        var grid;
        var isTreeGrid = false;
        var baseUrl = sysExt.apiHeadBase + '/dictionary';
        var pageTableName = '<%=tableName%>';

        $(function () {

            grid = $('#grid').datagrid({
                title: '',
                url: baseUrl + '/grid',
                fit: true,
                border: false,
                striped: true,
                rownumbers: true,
                pagination: true,
                singleSelect: true,
                idField: 'id',
                multiSort: true,
                pageSize: 50,
                pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
                method: 'post',
                //frozenColumns : [ [  ] ],
                columns: [[{title: '中文名', field: 'tnameCn', sortable: true, width: '100'},
                    {title: '英文名', field: 'tnameEn', sortable: true, width: '100'},
                    {title: 'ID', field: 'id', hidden: true, width: '80'},
                    {
                        title: '类型',
                        field: 'dtype',
                        sortable: true,
                        align: 'center',
                        width: '80',
                        formatter: function (value, row, index) {
                            return getComboValue('dataSource', value);
                        }
                    },
                    {title: '中文内容', field: 'tvalueCn', sortable: true, width: '100'},
                    {title: '英文内容', field: 'tvalueEn', sortable: true, width: '100'},
                    {title: '备注', field: 'remark', sortable: true, width: '100'},
                    {title: '创建时间', field: 'createdDt', sortable: true, width: '140'},
                    {title: '最后修改时间', field: 'updatedDt', sortable: true, width: '140'},
                    {title: '排序', field: 'sort', align: 'center', sortable: true, width: '50'},
                    {
                        title: '数据状态',
                        field: 'del',
                        sortable: true,
                        align: 'center',
                        width: '80',
                        formatter: function (value, row, index) {
                            return getComboValue('status', value);
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
                <li><a id="addBtn"></a></li>
                <li><a id="editBtn"></a></li>
                <li><a id="moreBtn"></a></li>
                <div id="mmore">
                    <div id="showBtn"></div>
                    <div class="menu-sep"></div>
                    <div id="delBtn"></div>
                </div>
                <li><a id="singleSelBtn"></a></li>
                <li><input id="query_1" name="QUERY_t#tnameCn_S_LK" data-options="prompt:'类型名称CN'"
                           style="width: 80px;height:24px;" class="easyui-textbox"/></li>
                <li><input id="query_2" name="QUERY_t#tnameEn_S_LK" data-options="prompt:'类型名称EN'"
                           style="width: 80px;height:24px;" class="easyui-textbox"/></li>
                <li><input id="query_3" name="QUERY_t#tvalueCn_S_LK" data-options="prompt:'类型值CN'"
                           style="width: 80px;height:24px;" class="easyui-textbox"/></li>
                <li><input id="query_4" name="QUERY_t#tvalueEn_S_LK" data-options="prompt:'类型值EN'"
                           style="width: 80px;height:24px;" class="easyui-textbox"/></li>
                <li><select id="query_5" name="QUERY_t#dtype_BT_EQ" class="easyui-combobox easyui-textbox"
                            data-options="editable:false,data:sysExt.getCombos('dataSource'),valueField:'value',textField:'label',panelHeight:'auto',prompt:'类型'"
                            style="width: 80px;height:24px;"></select></li>
                <li><input name="QUERY_t#createdDt_D_GE"
                           onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" title="创建时间[开始]"
                           data-options="position:'left'" class="Wdate easyui-tooltip sysSearchDateBox"/></li>
                <li><input name="QUERY_t#createdDt_D_LE"
                           onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" title="创建时间[结束]"
                           data-options="position:'left'" class="Wdate easyui-tooltip sysSearchDateBox"/></li>
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