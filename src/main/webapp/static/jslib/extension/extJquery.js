var sysExt = sysExt || {};
sysExt.data = sysExt.data || {};// 用于存放临时的数据或者对象
var combo_arrays = undefined;// 通用下拉框数组
/**
 * 屏蔽右键
 * 
 * @author 孙宇
 * 
 * @requires jQuery
 */
$(document).bind('contextmenu', function() {
	// return false;
});

/**
 * 禁止复制
 * 
 * @author 孙宇
 * 
 * @requires jQuery
 */
$(document).bind('selectstart', function() {
	// return false;
});

/**
 * @author 孙宇
 * 
 * 增加命名空间功能
 * 
 * 使用方法：sysExt.ns('jQuery.bbb.ccc','jQuery.eee.fff');
 */
sysExt.ns = function() {
	var o = {}, d;
	for (var i = 0; i < arguments.length; i++) {
		d = arguments[i].split(".");
		o = window[d[0]] = window[d[0]] || {};
		for (var k = 0; k < d.slice(1).length; k++) {
			o = o[d[k + 1]] = o[d[k + 1]] || {};
		}
	}
	return o;
};

/**
 * 将form表单元素的值序列化成对象
 * 
 * @example sysExt.serializeObject($('#formId'))
 * 
 * @author 孙宇
 * 
 * @requires jQuery
 * 
 * @returns object
 */
sysExt.serializeObject = function(form) {
	var o = {};
	var sdateType = '';
	$.each(form.serializeArray(), function(index) {
		if (this['value'] != undefined && this['value'].length > 0) {// 如果表单项的值非空，才进行序列化操作
			if (o[this['name']]) {
				o[this['name']] = o[this['name']] + "," + this['value'];
			} else {
				//特殊情况 用于多时间过滤查询时处理,在auto dev代码自动生成时启用此策略
				if(this['name']=='SDATETYPE'){
					sdateType = this['value'];
				}else if(this['name']=='GE'){
					o[sdateType+'GE'] = this['value'];
				}else if(this['name']=='LE'){
					o[sdateType+'LE'] = this['value'];
				}else{
					//正常情况
					o[this['name']] = this['value'];
				}
			}
		}
	});
	return o;
};

sysExt.serializeStr = function(form){
	var str = "";
	var last = false;
	$.each(form.serializeArray(), function(index) {
		if (this['value'] != undefined && this['value'].length > 0) {// 如果表单项的值非空，才进行序列化操作
			if(!last){
				str+='?';
			}else{
				str+='&';
			}
			
			str+=this['name']+ "=" + this['value'];
			last = true;
		}
	});
	return str;
}

/**
 * 增加formatString功能
 * 
 * @author 孙宇
 * 
 * @example sysExt.formatString('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 * 
 * @returns 格式化后的字符串
 */
sysExt.formatString = function(str) {
	for (var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};

/**
 * 接收一个以逗号分割的字符串，返回List，list里每一项都是一个字符串
 * 
 * @author 孙宇
 * 
 * @returns list
 */
sysExt.stringToList = function(value) {
	if (value != undefined && value != '') {
		var values = [];
		var t = value.split(',');
		for (var i = 0; i < t.length; i++) {
			values.push('' + t[i]);/* 避免他将ID当成数字 */
		}
		return values;
	} else {
		return [];
	}
};

/**
 * JSON对象转换成String
 * 
 * @param o
 * @returns
 */
sysExt.jsonToString = function(o) {
	var r = [];
	if (typeof o == "string")
		return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
	if (typeof o == "object") {
		if (!o.sort) {
			for ( var i in o)
				r.push(i + ":" + sysExt.jsonToString(o[i]));
			if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {
				r.push("toString:" + o.toString.toString());
			}
			r = "{" + r.join() + "}";
		} else {
			for (var i = 0; i < o.length; i++)
				r.push(sysExt.jsonToString(o[i]));
			r = "[" + r.join() + "]";
		}
		return r;
	}
	return o.toString();
};

/**
 * Create a cookie with the given key and value and other optional parameters.
 * 
 * @example sysExt.cookie('the_cookie', 'the_value');
 * @desc Set the value of a cookie.
 * @example sysExt.cookie('the_cookie', 'the_value', { expires: 7, path: '/', domain: 'jquery.com', secure: true });
 * @desc Create a cookie with all available options.
 * @example sysExt.cookie('the_cookie', 'the_value');
 * @desc Create a session cookie.
 * @example sysExt.cookie('the_cookie', null);
 * @desc Delete a cookie by passing null as value. Keep in mind that you have to use the same path and domain used when the cookie was set.
 * 
 * @param String
 *            key The key of the cookie.
 * @param String
 *            value The value of the cookie.
 * @param Object
 *            options An object literal containing key/value pairs to provide optional cookie attributes.
 * @option Number|Date expires Either an integer specifying the expiration date from now on in days or a Date object. If a negative value is specified (e.g. a date in the past), the cookie will be deleted. If set to null or omitted, the cookie will be a session cookie and will not be retained when the the browser exits.
 * @option String path The value of the path atribute of the cookie (default: path of page that created the cookie).
 * @option String domain The value of the domain attribute of the cookie (default: domain of page that created the cookie).
 * @option Boolean secure If true, the secure attribute of the cookie will be set and the cookie transmission will require a secure protocol (like HTTPS).
 * @type undefined
 * 
 * @name sysExt.cookie
 * @cat Plugins/Cookie
 * @author Klaus Hartl/klaus.hartl@stilbuero.de
 * 
 * Get the value of a cookie with the given key.
 * 
 * @example sysExt.cookie('the_cookie');
 * @desc Get the value of a cookie.
 * 
 * @param String
 *            key The key of the cookie.
 * @return The value of the cookie.
 * @type String
 * 
 * @name sysExt.cookie
 * @cat Plugins/Cookie
 * @author Klaus Hartl/klaus.hartl@stilbuero.de
 */
sysExt.cookie = function(key, value, options) {
	if (arguments.length > 1 && (value === null || typeof value !== "object")) {
		options = $.extend({}, options);
		if (value === null) {
			options.expires = -1;
		}
		if (typeof options.expires === 'number') {
			var days = options.expires, t = options.expires = new Date();
			t.setDate(t.getDate() + days);
		}
		return (document.cookie = [ encodeURIComponent(key), '=', options.raw ? String(value) : encodeURIComponent(String(value)), options.expires ? '; expires=' + options.expires.toUTCString() : '', options.path ? '; path=' + options.path : '', options.domain ? '; domain=' + options.domain : '', options.secure ? '; secure' : '' ].join(''));
	}
	options = value || {};
	var result, decode = options.raw ? function(s) {
		return s;
	} : decodeURIComponent;
	return (result = new RegExp('(?:^|; )' + encodeURIComponent(key) + '=([^;]*)').exec(document.cookie)) ? decode(result[1]) : null;
};

sysExt.getRandomColor = function(){
	return '#'+('00000'+(Math.random()*0x1000000<<0).toString(16)).slice(-6);
}

/*通用的下拉框取值方法,用于列表展现*/
sysExt.getComboValue = function(arrayId,value){
	try{
		if(typeof(combo_arrays)=='undefined'){
			$.ajax({type: "GET",dataType: "json",async: false, url: comboUrl,success: function(result){combo_arrays = result;}});
		}else{
			for(var i = 0;i<combo_arrays[arrayId].length;i++) {
				// 设置前判断，避免每次颜色不一样
				if(combo_arrays[arrayId][i].colorLabel == ''){
					// 选项随机颜色
					var colorLabel = '<font style="color:'+sysExt.getRandomColor()+';font-weight:bold;">'+combo_arrays[arrayId][i].label+'</font>';
					combo_arrays[arrayId][i].colorLabel = colorLabel;
				}

			}
		}
		for(var i = 0;i<combo_arrays[arrayId].length;i++) {
			if(value == combo_arrays[arrayId][i].value){
				/*if(arrayId=='yesOrNo' && value == 0){//否
					return '<font style="color:red;">'+combo_arrays[arrayId][i].label+'</font>';
				}else{*/
					return combo_arrays[arrayId][i].colorLabel;
				/*}*/
			}
		}
	}catch(e){
		console.info('error:extJquery.js>getComboValue');
		return value;
	}
}


/*通用的下拉框取值方法,用于查询等模块的下拉框初始化*/
sysExt.getCombos = function(arrayId){
	try {
		if(typeof(arrayId)=='undefined' || arrayId=='')
			return [{'label':'参数不存在，数据未加载','value':'-9999'}];
		if(typeof(comboUrl)=='undefined' || comboUrl=='')
			return [{'label':'链接不存在，数据未加载','value':'-8888'}];
		if(typeof(combo_arrays)=='undefined'){
			$.ajax({type: "GET",dataType: "json",async: false, url: comboUrl,success: function(result){combo_arrays = result;}});
		}
		return combo_arrays[arrayId];
	} catch (e) {
		console.info('error:extJquery.js>getCombos');
		return [{'label': '数据加载异常', 'value': '-7777'}];
	}
}

/**
 * 改变jQuery的AJAX默认属性和方法
 * 
 * @author 孙宇
 * 
 * @requires jQuery
 * 
 */
$.ajaxSetup({
	type : 'POST',
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		try {
			parent.$.messager.progress('close');
			parent.$.messager.alert('错误', XMLHttpRequest.responseText);
		} catch (e) {
			alert(XMLHttpRequest.responseText);
		}
	}
});


/**
 * 解决class="iconImg"的img标记，没有src的时候，会出现边框问题
 * 
 * @author 孙宇
 * 
 * @requires jQuery
 */
$(function() {
	$('.iconImg').attr('src', sysExt.pixel_0);
});