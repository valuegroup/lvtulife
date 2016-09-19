<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String tableName = "资源信息";%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">

        var baseUrl = sysExt.apiHeadSystem + '/resource';
        var pageTableName = '<%=tableName%>';

        var submitForm = function ($dialog, $grid, $pjq, ifRepeat) {
            if ($('form').form('validate')) {
                disableToolbarButton(true);//禁用
                $.post(getUrl(), sysExt.serializeObject($('form')), function (result) {
                    if (result.success) {
                        unselectAllAndReload($grid, true);
                        if (ifRepeat) {
                            clearIcon();
                            $('form').form('reset');
                        } else {
                            $dialog.dialog('destroy');
                        }
                    } else {
                        messagerAlert(result);
                    }
                    disableToolbarButton(false);//启用
                }, 'json');
            }
        };

        var showIcons = function () {
            var dialog = parent.sysExt.modalDialog({
                title: '浏览小图标',
                url: sysExt.apiHeadSystem + '/page/style/icons',
                buttons: [{
                    text: '确定',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.selectIcon(dialog, $('#iconCls'));
                    }
                }]
            });
        };

        var getSort = function () {
            var t = $('#pid').combotree('tree');
            var node = t.tree('getSelected');
            if (node) {
                //依据当前父节点，从数据库中获取最大的排序字段
                $.post(baseUrl + '/getResourceMaxSeq', {
                    'pid': node.id
                }, function (result) {
                    if (result.success) {
                        $('#sysresourcce_sort').numberspinner('setValue', result.data);
                    } else {
                        messagerAlert(result);
                    }
                }, 'json');
            } else {
                // 根节点
                $('#sysresourcce_sort').numberspinner('setValue', 1);
            }
        };

        function formatItem(row) {
            var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +
                    '<span style="color:#888">' + row.desc + '</span>';
            return s;
        }

        var pageFormDataLoadCustom = function (data) {
            $('#iconCls').attr('class', data.iconCls);//设置背景图标
        }

        function clearIcon() {
            $('#iconCls').val('');
            $('#iconCls').attr('class', '');
        }

        $(function () {

            $('#target').combobox({
                url: sysExt.contextPath + '/static/json/system/combobox_target.json',
                method: 'get',
                valueField: 'id',
                textField: 'text',
                panelHeight: 180,
                editable: false,
                required: false,//必填
                multiple: false,//多选
                formatter: function (row) {
                    var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +
                            '<span style="color:#888">' + row.desc + '</span>';
                    return s;
                }
            });

            //资源类型下拉框
            $('#typeId').combobox({
                data: sysExt.getCombos('typeName'),
                valueField: 'value',
                textField: 'label',
                required: true,
                editable: false,
                panelHeight: 180,
                onSelect: function (res) {
                    //清空上级资源下拉框选项，依据当前所选资源类型重新加载上级资源下拉选项
                    var url = baseUrl + '/getMenuForType' + '?id=' + res.value;
                    $('#pid').combotree('clear');
                    $.post(url, function (data) {
                        $('#pid').combotree('loadData', data);
                    }, 'json');
                    //$('#pid').combotree('clear').combotree('reload', url);  //使用.combotree('addClearBtn', 'icon-clear')方法时请勿使用reload方法
                }
            });

            //上级资源下拉框
            $('#pid').combotree({
                url: baseUrl + '/getMenuForType',
                editable: false,
                idField: 'id',
                textField: 'text',
                panelHeight: 180,
                parentField: 'pid'/**,
                 onSelect: function(res){
				//依据当前父节点，从数据库中获取最大的排序字段
				$.post(baseUrl + '/system/sysResource/doNotNeedSecurity_getResourceSeqForResPid', {
					'other' : res.id
				}, function(result) {
	            	$('#sysresourcce_sort').numberspinner('setValue', result.obj);
				}, 'json');
	        }**/
            });
        });
    </script>
    <jsp:include page="/views/include/incPageFormD.jsp"/>
</head>
<body>
<form method="post" class="formForm">
    <table class="formTable" style="width: 100%;">
        <tr>
            <th><span class="tableTitle"><%=tableName%></span></th>
            <td>
                <div class="lineStyle"></div>
            </td>
        </tr>
        <tr id="primaryKeyTr">
            <th>主键</th>
            <td>
                <a class="easyui-tooltip" title="自动生成,无需编辑" data-options="position:'bottom'">
                    <input name="id" value="${id}" class="easyui-textbox" data-options="readonly:true"/>
                </a>
            </td>
        </tr>
        <tr>
            <th>资源名称</th>
            <td><input id="resName" name="resName" class="easyui-textbox" data-options="required:true,addClearBtn:true"/></td>
        </tr>
        <tr>
            <th>请求地址</th>
            <td>/<input id="url" name="urlHead" class="easyui-textbox" style="width: 80px;"/>
                /<input id="urlModule" name="urlModule" class="easyui-textbox" style="width: 80px;"/>
                /<input id="urlMethod" name="urlMethod" class="easyui-textbox" style="width: 80px;"/>
            </td>
        </tr>
        <tr>
            <th>资源属性</th>
            <td><input name="resAttr" class="easyui-combobox"
                       data-options="data:sysExt.getCombos('resAttr'),valueField:'value',textField:'label',required:true,editable:false"
                       style="width: 80px;"/>
                <input id="target" name="target" style="width: 200px;"/>
            </td>
        </tr>
        <tr>
            <th>含基础权限点</th>
            <td>
                <input name="isAuthPoint" class="easyui-combobox"
                       data-options="data:sysExt.getCombos('yesOrNo'),valueField:'value',textField:'label',required:true,editable:false"/>
            </td>
        </tr>
        <tr>
            <th>资源关联</th>
            <td>
                <select id="typeId" name="typeId" class="easyui-textbox" style="width: 80px;"></select>
                <select id="pid" name="pid" style="width: 200px;"></select>
                <!-- &nbsp;<img class="iconImg icon-clear imgMiddle" onclick="$('#pid').combotree('clear');" title="清空" /> -->
            </td>
        </tr>
        <tr>
            <th>资源图标</th>
            <td>
                <input id="iconCls" name="iconCls" readonly="readonly" style="padding-left: 18px; width: 164px;"/>&nbsp;<img class="iconImg icon-search imgMiddle" onclick="showIcons();" title="浏览图标"/>&nbsp;<img class="iconImg icon-clear imgMiddle" onclick="clearIcon()" title="清空"/>
            </td>
        </tr>
        <tr>
            <th>顺序</th>
            <td>
                <input id="sysresourcce_sort" name="sort" class="easyui-numberspinner"
                       data-options="min:0,max:100000,buttonText:'获取顺序',onClickButton:getSort"/>
            </td>
        </tr>
        <tr>
            <th>资源描述</th>
            <td><input id="bewrite" name="bewrite" class="easyui-textbox" data-options="multiline:true"
                       style="height:100px"/></td>
        </tr>
    </table>
</form>
</body>
</html>