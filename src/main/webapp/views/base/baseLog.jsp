<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String tableName = "系统日志";%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">
        var grid;
        var isTreeGrid = false;
        var baseUrl = sysExt.apiHeadBase + '/log';
        var pageTableName = '<%=tableName%>';

        var tempFun = function (id) {
            var node = grid.datagrid('getSelected');
            if (node) {
                var dialog = parent.sysExt.modalDialog({
                    title: '示例',
                    url: baseUrl + '/temp?id=' + node.id,
                    toolbar: [
                        {
                            id: 'btn1', text: '示例', iconCls: 'ext-icon-shield',
                            handler: function () {
                                dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                            }
                        }
                    ]
                });
            }
        };

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
                pageSize: 20,
                pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
                frozenColumns : [[]],
                columns : [ [ 
                    {width : '100',title : '主键',field : 'id',align : 'center',hidden : true,sortable : true}
                    ,{width : '100',title : '请求地址',field : 'requestIp',align : 'left',hidden : false,sortable : true}
                    ,{width : '140',title : '操作时间',field : 'createdDt',align : 'left',hidden : false,sortable : true}
                    ,{width : '50',title : '日志类型',field : 'logType',align : 'center',hidden : false,sortable : true,formatter:function(value,row,index){return sysExt.getComboValue('logType',value);}}
                    ,{width : '50',title : '方法类型',field : 'methodType',align : 'center',hidden : false,sortable : true,formatter:function(value,row,index){return sysExt.getComboValue('method',value);}}
                    ,{width : '200',title : '方法描述',field : 'description',align : 'left',hidden : false,sortable : true}
                    ,{width : '100',title : '请求方法',field : 'method',align : 'left',hidden : false,sortable : true}
                    ,{width : '500',title : '操作类',field : 'clazz',align : 'left',hidden : false,sortable : true}
                    ,{width : '500',title : '方法参数',field : 'params',align : 'left',hidden : false,sortable : true}
                    ,{width : '150',title : '异常代码',field : 'exCode',align : 'left',hidden : false,sortable : true}
                    ,{width : '150',title : '异常信息',field : 'exDetail',align : 'left',hidden : false,sortable : true}
                    ] ],
                toolbar: '#toolbar',
                onBeforeLoad: function (param) {
                    beforeLoadFun(param);
                },
                onLoadSuccess: function (data) {
                    loadSuccessFun(data);
                },
                onDblClickRow: showFun
            });

            $('#tempBtn').text('示例').linkbutton({iconCls: 'ext-icon-shield', plain: true, onClick: tempFun}).attr('href', 'javascript:void(0);');
        });
    </script>
    <jsp:include page="/views/include/incPageListD.jsp"/>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div id="toolbar" style="display: none;">
    <div class="easyui-panel" style="width:100%;" data-options="border:false">
        <form id="searchForm">
            <ul class="sysToolBarUl">
                <li><a id="moreBtn"></a></li>
                <div id="mmore">
                    <div id="showBtn"></div>
                    <div class="menu-sep"></div>
                </div>
                <li><a id="singleSelBtn"></a></li>
				<li><input name="QUERY_t#logType_BT_EQ" style="width: 80px;height:24px;" class="easyui-combobox easyui-textbox" data-options="data:sysExt.getCombos('logType'),valueField:'value',textField:'label',panelHeight:'auto',editable:false,prompt:'日志类型'"/></li>
				<li><input name="QUERY_t#methodType_BT_EQ" style="width: 80px;height:24px;" class="easyui-combobox easyui-textbox" data-options="data:sysExt.getCombos('method'),valueField:'value',textField:'label',panelHeight:'auto',editable:false,prompt:'方法类型'"/></li>
				<li><input name="QUERY_t#method_S_LK" style="width: 80px;height:24px;" class="easyui-textbox" data-options="prompt:'请求方法'" /></li>
				<li><input name="QUERY_t#params_S_LK" style="width: 80px;height:24px;" class="easyui-textbox" data-options="prompt:'方法参数'" /></li>
				<li><input name="QUERY_t#exCode_S_LK" style="width: 80px;height:24px;" class="easyui-textbox" data-options="prompt:'异常代码'" /></li>
				<li><input name="QUERY_t#exDetail_S_LK" style="width: 80px;height:24px;" class="easyui-textbox" data-options="prompt:'异常信息'" /></li>
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
