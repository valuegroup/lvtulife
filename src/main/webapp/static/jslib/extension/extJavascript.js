var sysExt = sysExt || {};

/**
 * 去字符串空格
 *
 * @author 孙宇
 */
sysExt.trim = function (str) {
    return str.replace(/(^\s*)|(\s*$)/g, '');
};
sysExt.ltrim = function (str) {
    return str.replace(/(^\s*)/g, '');
};
sysExt.rtrim = function (str) {
    return str.replace(/(\s*$)/g, '');
};

/**
 * 判断开始字符是否是XX
 *
 * @author 孙宇
 */
sysExt.startWith = function (source, str) {
    var reg = new RegExp("^" + str);
    return reg.test(source);
};
/**
 * 判断结束字符是否是XX
 *
 * @author 孙宇
 */
sysExt.endWith = function (source, str) {
    var reg = new RegExp(str + "$");
    return reg.test(source);
};

/**
 * iframe自适应高度
 *
 * @author 孙宇
 *
 * @param iframe
 */
sysExt.autoIframeHeight = function (iframe) {
    iframe.style.height = iframe.contentWindow.document.body.scrollHeight + "px";
};

/**
 * 设置iframe高度
 *
 * @author 孙宇
 *
 * @param iframe
 */
sysExt.setIframeHeight = function (iframe, height) {
    iframe.height = height;
};


/**
 * 打印对象中的各个属性和属性值 简单版
 * @param myObject
 */
sysExt.dumpObjEasy = function (myObject) {
    var s = "";
    for (var property in myObject) {
        s = s + "\n " + property + ": " + myObject[property];
    }
    return s;
}


var MAX_DUMP_DEPTH = 10;
/**
 * 打印对象中的各个属性和属性值
 * @param obj 需要打印的对象
 * @param name    名称
 * @param indent    分隔
 * @param depth 循环深度
 * @returns
 */
sysExt.dumpObj = function (obj, name, indent, depth) {
    if (depth > MAX_DUMP_DEPTH) {
        return indent + name + ": <Maximum Depth Reached>\n";
    }
    if (typeof obj == "object") {
        var child = null;
        var output = indent + name + "\n";
        indent += "\t";
        for (var item in obj) {
            try {
                child = obj[item];
            } catch (e) {
                child = "<Unable to Evaluate>";
            }
            if (typeof child == "object") {
                output += dumpObj(child, item, indent, depth + 1);
            } else {
                output += indent + item + ": " + child + "\n";
            }
        }
        return output;
    } else {
        return obj;
    }
}

/**
 * 数组去重
 * @param arr
 * @returns {Array}
 */
sysExt.arrayUnique = function (arr) {
    var res = [], hash = {};
    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
        if (!hash[elem]) {
            res.push(elem);
            hash[elem] = true;
        }
    }
    return res;
}

sysExt.nullToStr = function (data) {
    return (typeof(data) == "undefined" || data == "" || data == undefined || data == null) ? "暂无" : data;
}

sysExt.isNull = function (data) {
    return (typeof(data) == "undefined" || data == "" || data == undefined || data == null);
}
