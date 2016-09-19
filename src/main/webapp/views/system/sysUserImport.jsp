<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String contextPath = request.getContextPath();
%>
<% String tableName = "用户信息导入";%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <%-- 引入jquery插件ajaxform--%>
    <script src="<%=contextPath %>/static/jslib/jquery.form.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">
        var baseUrl = sysExt.apiHeadSystem + '/user';
        var pageTableName = '<%=tableName%>';

        var submitForm = function ($dialog, $grid, $pjq) {
            disableToolbarButton(true);//禁用
            $('#form').ajaxSubmit({
                type:"post",
                dataType:'json',
                url: baseUrl + '/importFile',
                beforeSend: function() {
                    //表单提交前做表单验证
                    var filePath =$('#impfile').filebox('getValue');
                    var suffix = filePath.substring(filePath.lastIndexOf('.'),filePath.length);
                    if(suffix!='.xls'){
                        $.messager.alert('提示', '文件格式不正确，请选择正确的Excel文件(后缀名.xls)！', 'error');
                        return false;
                    }
                    parent.$.messager.progress({
                        text: '数据处理中....'
                    });
                },
                timeout:0,
                success: function(result) {
                    //提交成功后调用
                    parent.$.messager.progress('close');
                    if(result.success){
                        unselectAllAndReload($grid, false);
                        $dialog.dialog('destroy');
                    }else{
                        messagerAlert(result);
                    }
                    disableToolbarButton(false);
                }
            });
            return false; // 阻止表单自动提交事件
        };
    </script>
    <jsp:include page="/views/include/incPageCustomD.jsp"/>
</head>
<body>
<form id="form" method="post" enctype="multipart/form-data" class="formForm">
    <table class="formTable" style="width: 100%;">
        <tr>
            <th><span class="tableTitle"><%=tableName%></span></th>
            <td>
                <div class="lineStyle"></div>
            </td>
        </tr>
        <tr>
            <th>模版下载:</th>
            <td><a href="<%=contextPath %>/static/shareFiles/template/usersImportTemplate.xls">EXCEL模版文件.xls</a></td>
        </tr>
        <tr>
            <th>导入文件:</th>
            <td>
                <input class="easyui-filebox" id="impfile" name="impfile" data-options="prompt:'请选择一个Excel文件',buttonText:'选择',required:true">
            </td>
        </tr>
        <tr>
            <th>提醒:</th>
            <td style="color:blue;">请先下载模版文件，按格式填写好后保存，再将文件导入</td>
        </tr>
    </table>
</form>
</body>
</html>