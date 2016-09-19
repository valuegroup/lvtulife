<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String tableName = "业务初始参数表";%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">
        var grid;
        var isTreeGrid = false;
        var baseUrl = sysExt.apiHeadGtd + '/initParams';
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
                sortName: 'id',
                sortOrder: 'asc',
                pageSize: 20,
                pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
                frozenColumns : [[]],
                columns : [ [ 
                    {width : '250',title : '主键（用户ID）',field : 'id',align : 'center',hidden : true,sortable : true}
                    ,{width : '100',title : '初始年份',field : 'initYear',align : 'center',hidden : false,sortable : true}
                    ,{width : '100',title : '当前阶段',field : 'nowStage',align : 'left',hidden : false,sortable : true,formatter:function(value,row,index){return sysExt.getComboValue('yesOrNo',value);}}
                    ,{width : '100',title : '当前年度',field : 'nowYear',align : 'center',hidden : false,sortable : true}
                    ,{width : '100',title : '当前季度',field : 'nowQuarter',align : 'left',hidden : false,sortable : true}
                    ,{width : '100',title : '当前月度',field : 'nowMonth',align : 'left',hidden : false,sortable : true}
                    ,{width : '250',title : '当前月度第几周',field : 'nowWeekMonth',align : 'left',hidden : false,sortable : true}
                    ,{width : '250',title : '当前年度第几周',field : 'nowWeekYear',align : 'left',hidden : false,sortable : true}
                    ,{width : '100',title : '当前天数',field : 'nowDay',align : 'center',hidden : false,sortable : true}
                    ,{width : '250',title : '自定义阶段信息（json）',field : 'customStage',align : 'left',hidden : false,sortable : true,formatter:function(value,row,index){return sysExt.getComboValue('yesOrNo',value);}}
                    ,{width : '250',title : '自定义常用时段信息（json）',field : 'customTimeInterval',align : 'left',hidden : false,sortable : true,formatter:function(value,row,index){return sysExt.getComboValue('yesOrNo',value);}}
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
                <li><a id="addBtn"></a></li>
                <li><a id="editBtn"></a></li>
                <li><a id="moreBtn"></a></li>
                <li><a id="tempBtn"></a></li>
                <div id="mmore">
                    <div id="showBtn"></div>
                    <div class="menu-sep"></div>
                    <div id="delBtn"></div>
                </div>
                <li><a id="singleSelBtn"></a></li>
				<li><input name="QUERY_t#initYear_I_EQ" style="width: 80px;height:24px;" class="easyui-textbox" data-options="prompt:'初始年份'" /></li>
				<li><input name="QUERY_t#nowStage_BT_EQ" style="width: 80px;height:24px;" class="easyui-combobox easyui-textbox" data-options="data:sysExt.getCombos('yesOrNo'),valueField:'value',textField:'label',panelHeight:'auto',editable:false,prompt:'当前阶段'"/></li>
				<li><input name="QUERY_t#nowYear_I_EQ" style="width: 80px;height:24px;" class="easyui-textbox" data-options="prompt:'当前年度'" /></li>
				<li><input name="QUERY_t#nowQuarter_BT_EQ" style="width: 80px;height:24px;" class="easyui-textbox" data-options="prompt:'当前季度'" /></li>
				<li><input name="QUERY_t#nowMonth_BT_EQ" style="width: 80px;height:24px;" class="easyui-textbox" data-options="prompt:'当前月度'" /></li>
				<li><input name="QUERY_t#nowWeekMonth_BT_EQ" style="width: 77px;height:24px;" class="easyui-textbox" data-options="prompt:'当前月度第几周'" /></li>
				<li><input name="QUERY_t#nowWeekYear_BT_EQ" style="width: 77px;height:24px;" class="easyui-textbox" data-options="prompt:'当前年度第几周'" /></li>
				<li><input name="QUERY_t#nowDay_I_EQ" style="width: 80px;height:24px;" class="easyui-textbox" data-options="prompt:'当前天数'" /></li>
				<li><input name="QUERY_t#customStage_S_LK" style="width: 143px;height:24px;" class="easyui-combobox easyui-textbox" data-options="data:sysExt.getCombos('yesOrNo'),valueField:'value',textField:'label',panelHeight:'auto',editable:false,prompt:'自定义阶段信息（json）'"/></li>
				<li><input name="QUERY_t#customTimeInterval_S_LK" style="width: 165px;height:24px;" class="easyui-combobox easyui-textbox" data-options="data:sysExt.getCombos('yesOrNo'),valueField:'value',textField:'label',panelHeight:'auto',editable:false,prompt:'自定义常用时段信息（json）'"/></li>
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
