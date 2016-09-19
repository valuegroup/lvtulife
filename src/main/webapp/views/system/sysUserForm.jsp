<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String tableName = "用户";%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">
        var baseUrl = sysExt.apiHeadSystem + '/user';
        var pageTableName = '<%=tableName%>';

        /*var submitNow = function ($dialog, $grid, $pjq, ifRepeat) {
         $.post(getUrl(), sysExt.serializeObject($('form')), function (result) {
         parent.sysExt.progressBar('close');//关闭上传进度条
         if (result.success) {
         unselectAllAndReload($grid, false);
         if (result.data) {
         parent.reloadNorthFun();//重新加载north面板,用户当前用户信息更新后
         }
         $dialog.dialog('destroy');
         } else {
         messagerAlert(result);
         }
         }, 'json');
         };*/

        /*var submitForm = function ($dialog, $grid, $pjq, ifRepeat) {
         if ($('form').form('validate')) {
         if (uploader.files.length > 0) {
         uploader.start();
         uploader.bind('StateChanged', function (uploader) {// 在所有的文件上传完毕时，提交表单
         if (uploader.files.length === (uploader.total.uploaded + uploader.total.failed)) {
         submitNow($dialog, $grid, $pjq,ifRepeat);
         }
         });
         } else {
         submitNow($dialog, $grid, $pjq,ifRepeat);
         }
         }
         };*/

        var submitForm = function ($dialog, $grid, $pjq, ifRepeat) {
            if ($('form').form('validate')) {
                disableToolbarButton(true);//禁用
                $.post(getUrl(), sysExt.serializeObject($('form')), function (result) {
                    if (result.success) {
                        unselectAllAndReload($grid, false);
                        if (ifRepeat) {
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

        var pageFormDataLoadCustom = function (result) {
            $('#photo').attr('src', sysExt.contextPath + result.photo);
        }

    </script>
    <jsp:include page="/views/include/incPageFormD.jsp"/>
    <%--<jsp:include page="/views/include/incPlupload.jsp"/>--%>
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
            <th>登录名称</th>
            <td><input name="loginName" class="easyui-textbox" data-options="required:true"/></td>
        </tr>
        <tr>
            <th>姓名</th>
            <td><input name="userName"/></td>
        </tr>
        <tr>
            <th>性别</th>
            <td><input name="sex" class="easyui-combobox" data-options="data:sysExt.getCombos('sex'),valueField:'value',textField:'label',required:true,editable:false"/></td>
        </tr>
        <tr>
            <th>年龄</th>
            <td><input name="age" class="easyui-numberbox" data-options="min:1,max:120,value:1"/></td>
        </tr>
        <%--<tr>
            <th>照片上传</th>
            <td>
                <div id="container" style="width: 260px;">
                    <a id="pickfiles" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom'">选择文件</a>

                    <div id="filelist">您的浏览器没有安装Flash插件，或不支持HTML5！</div>
                </div>
            </td>
        </tr>--%>
        <%--<tr>
            <th></th>
            <td><input name="photo" readonly="readonly" style="display: none;"/> <img id="photo" src="" style="width: 200px; height: 200px;"></td>
        </tr>--%>
    </table>
</form>
</body>
</html>