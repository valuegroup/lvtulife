<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String tableName = "资源";%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">
        var grid;
        var isTreeGrid = true;
        var baseUrl = sysExt.apiHeadSystem + '/resource';
        var pageTableName = '<%=tableName%>';

        $(function () {
            grid = $('#grid').treegrid({
                title: '',
                url: baseUrl + '/treeGrid',
                idField: 'id',
                treeField: 'resName',
                parentField: 'pid',
                fit: true,
                border: false,
                striped: true,
                rownumbers: true,
                pagination: false,
                sortName: 'sort',
                sortOrder: 'asc',
                frozenColumns: [[{width: '200', title: '资源名称', field: 'resName'}]],
                columns: [[
                    {width: '60', title: '主键', field: 'id'},
                    /*{width: '200', title: '图标名称', field: 'iconCls'},*/
                    {
                        width: '300', title: '请求地址', field: 'urlMethod', formatter: function (value, row) {

                        if (sysExt.isNull(value)) {
                            // 三者都为空，判断最后一个为空即可
                            return "";
                        } else if (sysExt.isNull(row.urlHead) && sysExt.isNull(row.urlModule)) {
                            // 来自其他包的如 /druid  /monitoring
                            return sysExt.formatString('<span title="{0}">/{1}</span>', value, value);
                        } else {
                            // 拼接成完整的URL 如/system/sysUser/list
                            return sysExt.formatString('<span title="{0}">/{1}/{2}/{3}</span>', value, row.urlHead, row.urlModule, value);
                        }
                    }
                    },
                    {
                        width: '60',
                        title: '资源类型',
                        field: 'typeId',
                        align: 'center',
                        formatter: function (value, row, index) {
                            return sysExt.getComboValue('typeName', value);
                        }
                    },
                    {
                        width: '60',
                        title: '资源属性',
                        field: 'resAttr',
                        align: 'center',
                        formatter: function (value, row, index) {
                            return sysExt.getComboValue('resAttr', value);
                        }
                    },
                    {width: '150', title: '创建时间', field: 'createdDt', hidden: true},
                    {width: '150', title: '修改时间', field: 'updatedDt', hidden: true},
                    {width: '80', title: '排序', field: 'sort', hidden: true},
                    {
                        width: '60',
                        title: '级别',
                        field: 'resLevel',
                        align: 'center',
                        formatter: function (value, row, index) {
                            return value + 1 + '级菜单'
                        }
                    },
                    {
                        width: '60',
                        title: '子节点',
                        field: 'resLeaf',
                        align: 'center',
                        formatter: function (value, row, index) {
                            return sysExt.getComboValue('yesOrNo', value);
                        }
                    },
                    {
                        width: '60',
                        title: '含基础权限',
                        field: 'isAuthPoint',
                        align: 'center',
                        formatter: function (value, row, index) {
                            return sysExt.getComboValue('yesOrNo', value);
                        }
                    },
                    {width: '80', title: '目标', field: 'target'},
                    {
                        width: '200', title: '资源描述', field: 'bewrite', formatter: function (value, row) {
                        if (value) {
                            return sysExt.formatString('<span title="{0}">{1}</span>', value, value);
                        }
                    }
                    },
                    <%--, {
                    title : '操作',
                    field : 'action',
                    width : '100',
                    formatter : function(value, row) {
                        var str = '';
                        var temp = "&nbsp;";
                            str += temp+sysExt.formatString('<img class="iconImg ext-icon-note" title="查看" onclick="showFun(\'{0}\');"/>', row.id);
                            str += temp+sysExt.formatString('<img class="iconImg ext-icon-note_edit" title="编辑" onclick="editFun(\'{0}\');"/>', row.id);
                            str += temp+sysExt.formatString('<img class="iconImg ext-icon-note_delete" title="删除" onclick="removeFun(\'{0}\');"/>', row.id);
                        return str;
                    }
                }--%>]],
                toolbar: '#toolbar',
                onBeforeLoad: function (param) {
                    beforeLoadFun(param);
                },
                onLoadSuccess: function (data) {
                    loadSuccessFun(data);
                },
                onDblClickRow: showFun,
                pageSize: 50,
                pageList: [10, 20, 30, 40, 50, 100, 200]
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
                <li><a id="redoBtn"></a></li>
                <li><a id="undoBtn"></a></li>
                <li><a id="reloadBtn"></a></li>
                <li><input id="query_1" name="QUERY_t#resName_S_LK" data-options="prompt:'资源名称'"
                           style="width: 80px;height:24px;" class="easyui-textbox"/></li>
                <li><select id="query_2" name="QUERY_t#typeId_I_EQ" class="easyui-combobox easyui-textbox"
                            data-options="editable:false,data:sysExt.getCombos('typeName'),valueField:'value',textField:'label',panelHeight:'auto',prompt:'资源类型'"
                            style="width: 80px;height:24px;"></select></li>
                <li><select id="query_3" name="QUERY_t#resAttr_BT_EQ" class="easyui-combobox easyui-textbox"
                            data-options="editable:false,data:sysExt.getCombos('resAttr'),valueField:'value',textField:'label',panelHeight:'auto',prompt:'资源属性'"
                            style="width: 80px;height:24px;"></select></li>
                <li><select id="query_4" name="QUERY_t#isAuthPoint_BT_EQ" class="easyui-combobox easyui-textbox"
                            data-options="editable:false,data:sysExt.getCombos('yesOrNo'),valueField:'value',textField:'label',panelHeight:'auto',prompt:'含基础权限点'"
                            style="width: 120px;height:24px;"></select></li>
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