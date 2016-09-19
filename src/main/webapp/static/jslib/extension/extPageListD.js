/*通用获取下拉框选项集合*/
var comboUrl = baseUrl + '/getCombobox';

/*校验是否单选*/
var checkOnlyFun = function(){
	var selectSize = 0;
	if(isTreeGrid){
		selectSize = grid.treegrid('getSelections').length;
	}else{
		selectSize = grid.datagrid('getSelections').length;
	}
	if(selectSize>1){
		parent.mainShowSlide('请去除多选模式并选择一条记录进行操作!');
		return false;
	}
	return true;
}

/*通用获得当前选中节点*/
var getNode = function(){
	if(isTreeGrid){
		return grid.treegrid('getSelected');
	}else{
		return grid.datagrid('getSelected');
	}
}

/*通用添加方法*/
var addFun = function() {
	var dialog = parent.sysExt.modalDialog({
		title : '添加'+pageTableName+'信息',
		url: baseUrl + '/form',
		toolbar : [
        {
        	id : 'addFunSaveButton',
			text : '保存关闭',
			iconCls:'ext-icon-disk',
			handler : function() {
				//dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$, parent.reloadMenuTreeFun, false);
				dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$, false);
			}
		},{
			id : 'addFunMultSaveButton',
			text : '批量添加',
			iconCls:'ext-icon-disk_multiple',
			handler : function() {
				//dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$, parent.reloadMenuTreeFun, true);
				dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$, true);
			}
		},{
			id : 'addFunCloseButton',
			text : '关闭窗口',
			iconCls:'ext-icon-cross',
			handler : function() {
				destroyDialog(dialog,true);
			}
		} ]
	});
};

/*通用查看方法*/
var showFun = function(index,node) {
	if(typeof(node)=='undefined'){
		node = getNode();
	}
	if(checkOnlyFun()){
		if (node) {
			var dialog = parent.sysExt.modalDialog({
				title : '查看'+pageTableName+'信息',
				url: baseUrl + '/form?id=' + node.id,
				toolbar : [ {
					text : '关闭窗口',
					iconCls:'ext-icon-cross',
					handler : function() {
						destroyDialog(dialog,false);
					}
				}]
			});
		}else{
			parent.mainShowSlide();
		}
	}
};

/*通用编辑方法*/
var editFun = function() {
	if(checkOnlyFun()){
		var node = getNode();
		if (node) {
			var dialog = parent.sysExt.modalDialog({
				id : 'editFunDialog',
				title : '编辑'+pageTableName+'信息',
				url: baseUrl + '/form?id=' + node.id,
				toolbar : [ {
					id : 'editFunSaveButton',
					text : '编辑保存',
					iconCls:'ext-icon-disk',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
					}
				},{
					id : 'editFunCloseButton',
					text : '关闭窗口',
					iconCls:'ext-icon-cross',
					handler : function() {
						destroyDialog(dialog,true);
					}
				}]
			});
		}else{
			parent.mainShowSlide();
		}
	}
};

/*关闭dialog,ifConfirm=true 带询问*/
var destroyDialog = function(dialog,ifConfirm){
	if(ifConfirm){
		parent.$.messager.confirm('询问','您确定要关闭窗口？<br>PS:该操作不会对内容进行保存.',function(r){
			if(r){
				dialog.dialog('destroy');
			}
		});
	}else{
		dialog.dialog('destroy');
	}
}

/*通用删除方法*/
var removeFun = function() {
	if($('#singleSelBtn').linkbutton('options').selected){
		//批量删除写法
		var ids = [];
		var rows;
		if(isTreeGrid){
			rows = grid.treegrid('getSelections');
		}else{
			rows = grid.datagrid('getSelections');
		}

		for(var i=0; i<rows.length; i++){
            var row = rows[i];
            ids.push(row.id);
        }
        if(ids.length>0){
        	parent.$.messager.confirm('询问','您确定要删除这['+rows.length+']条记录？',function(r){
        		if (r) {
					$.post(baseUrl + '/deletes', {
        				ids : ids.join(',')
    				}, function(result) {
    					messagerAlert(result);
    					unselectAllAndReload();
        			}, 'json');
        		}
    		});
        }
	}else{
		//单个删除写法
		var node = getNode();
		if (node) {
			parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
				if (r) {
					$.post(baseUrl + '/delete', {
						id : node.id 
					}, function(result) {
						messagerAlert(result);
						unselectAllAndReload();
					}, 'json');
				}
			});
		}else{
			parent.mainShowSlide();
		}	
	}
};

var messagerAlert = function(result){
	if(result.success){
		$.messager.alert('提示', '代码:'+result.code+'</br>内容:'+result.info, 'info');
	}else{
		$.messager.alert('提示', '代码:'+result.code+'</br>内容:'+result.info+'</br>详细:'+result.detail, 'error');
	}
}

var unselectAllAndReload = function(){
	if(isTreeGrid){
		grid.treegrid('unselectAll');
		grid.treegrid('reload');
	}else{
		grid.datagrid('unselectAll');
		grid.datagrid('reload');
	}
}

/*通用树节点展开方法  treegrid*/
var redoFun = function() {
	var node = grid.treegrid('getSelected');
	if (node) {
		grid.treegrid('expandAll', node.id);
	} else {
		grid.treegrid('expandAll');
	}
};

/*通用树节点折叠方法 treegrid*/
var undoFun = function() {
	var node = grid.treegrid('getSelected');
	if (node) {
		grid.treegrid('collapseAll', node.id);
	} else {
		grid.treegrid('collapseAll');
	}
};

/*通用过滤方法*/
var searchFun = function(){
	if(isTreeGrid){
		grid.treegrid('load',sysExt.serializeObject($('#searchForm')));
	}else{
		grid.datagrid('load',sysExt.serializeObject($('#searchForm')));
	}
};

/*通用重置过滤方法*/
var resetSearchFun = function(){
	$('#searchForm input').val('');
	$('#searchForm').form('clear');
	if(isTreeGrid){
		grid.treegrid('load',{});
	}else{
		grid.datagrid('load',{});
	}
};

var singleSelFun = function(){
	if(isTreeGrid){
		grid.treegrid('clearSelections');
		grid.treegrid({singleSelect:!$('#singleSelBtn').linkbutton('options').selected});
	}else{
		grid.datagrid('clearSelections');
		grid.datagrid({singleSelect:!$('#singleSelBtn').linkbutton('options').selected});
	}
};

var beforeLoadFun = function(param){parent.$.messager.progress({text : '数据加载中....'});}

var loadSuccessFun = function(data){$('.iconImg').attr('src', sysExt.pixel_0);parent.$.messager.progress('close');}

/*通用基础按钮生成方法*/
$(function(){
	$('#addBtn').text('添加').linkbutton({iconCls:'ext-icon-note_add',plain:true,onClick:addFun}).attr('href','javascript:void(0);');
	$('#editBtn').text('编辑').linkbutton({iconCls:'ext-icon-note_edit',plain:true,onClick:editFun}).attr('href','javascript:void(0);');
	$('#showBtn').text('查看').attr('data-options',"iconCls:'ext-icon-note'").attr('title',"可以直接双击该行记录,查看该记录明细").attr('href','javascript:void(0);').bind('click', showFun).tooltip({position:'right'});
	$('#delBtn').text('删除|批量').attr('data-options',"iconCls:'ext-icon-note_delete'").attr('title',"使用多选模式,可以批量删除").attr('href','javascript:void(0);').bind('click', removeFun).tooltip({position:'right'});
	$('#moreBtn').menubutton({iconCls:'ext-icon-text_list_bullets',plain:true,position:'right',menu:'#mmore'});
	$('#singleSelBtn').linkbutton({iconCls:'ext-icon-shape_ungroup',plain:true,toggle:true,onClick:singleSelFun}).attr('title',"多选模式:用于批量删除").tooltip().attr('href','javascript:void(0);');

	if(isTreeGrid){
		$('#redoBtn').linkbutton({iconCls:'ext-icon-resultset_next',plain:true,onClick:redoFun}).attr('title',"展开树节点").tooltip().attr('href','javascript:void(0);');
		$('#undoBtn').linkbutton({iconCls:'ext-icon-resultset_previous',plain:true,onClick:undoFun}).attr('title',"折叠树节点").tooltip().attr('href','javascript:void(0);');
	}
	
	$('#searchBtn').text('过滤').linkbutton({iconCls:'ext-icon-zoom',plain:true,onClick:searchFun}).attr('href','javascript:void(0);');
	$('#resetSearchBtn').text('重置过滤').linkbutton({iconCls:'ext-icon-zoom_out',plain:true,onClick:resetSearchFun}).attr('href','javascript:void(0);');
});