/*通用获取下拉框选项集合*/
var comboUrl = baseUrl + '/getCombobox';

var unselectAllAndReload = function(grid,isTree){
	if(isTree){
		grid.treegrid('unselectAll');
		grid.treegrid('reload');
	}else{
		grid.datagrid('unselectAll');
		grid.datagrid('reload');
	}
}

/*启用或禁用工具条上的按钮,false 启用| true 禁用 */
var disableToolbarButton = function(isDisable){
	var pd = parent.sysExt.currentDialog;
	var toolbarBtnIds = [];
	if(typeof(pd)!='undefined') {
		toolbarBtnIds = pd.dialog.defaults.toolbarBtnIds;
	}
	for(var i=0;i<toolbarBtnIds.length;i++){
		parent.$('#'+toolbarBtnIds[i]).linkbutton(isDisable?'disable':'enable');
	}
}

var messagerAlert = function(result){
	if(result.success){
		$.messager.alert('提示', '代码:'+result.code+'</br>内容:'+result.info+'</br>详细:'+result.detail, 'info');
	}else{
		$.messager.alert('提示', '代码:'+result.code+'</br>内容:'+result.info+'</br>详细:'+result.detail, 'error');
	}
}