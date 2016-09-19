// 获取新增或更新的URL
var getUrl = function(){
	var url;
	if ($(':input[name="id"]').val().length > 0) {
		url = baseUrl + '/update';
	} else {
		url = baseUrl + '/save';
	}
	return url;
}

// 初始化表单数据
var pageFormDataLoad = function(){
	if ($(':input[name="id"]').val().length > 0) {
		// 编辑页面
		$.messager.progress({
			text : '基础数据加载中....'
		});
		$.post(baseUrl + '/getById', {
			id : $(':input[name="id"]').val()
		}, function(result) {
			if(result.success){
				if (result.data != undefined) {
					$('form').form('load', result.data);
					pageFormDataLoadCustom(result.data);
				}
			}else{
				messagerAlert(result);
			}
			$.messager.progress('close');
		}, 'json');
	}else{
		// 添加页面	隐藏主键所在行，需要再id所在tr加上id="primaryKeyTr"
		$('#primaryKeyTr').hide();
	}
}

$(function(){
	pageFormDataLoad();
});