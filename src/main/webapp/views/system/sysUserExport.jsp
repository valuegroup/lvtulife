<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="/views/include/inc.jsp"/>
    <script type="text/javascript">
        var submitForm = function ($dialog, $grid, $pjq, $searchForm) {
            $searchForm.form('submit', {
                url: sysExt.apiHeadSystem + '/user/exportFile',
                onSubmit: function (param) {
                    return true;
                }
            });
        };
    </script>
</head>
<body>
<fieldset>
    <legend>数据导出</legend>
    <table class="table" style="width: 100%;">
        </tr>
        <td colspan="2" align="left"><span style="color:blue;">提示：导出后文件格式为Excel。</span></td>
        </tr>
    </table>
</fieldset>
</body>
</html>