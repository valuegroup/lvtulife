<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="/views/include/inc.jsp"/>

    <script>
        var baseUrl = sysExt.apiHeadSystem + '/oneKeyBulid';
        var submitForm = function ($dialog, $grid, $pjq) {
            disableToolbarButton(true);//禁用
            $.post(baseUrl + '/bulidCode', {instructs: getBulidCheck()}, function (result) {
                messagerAlert(result);
                if (result.success) {
                    unselectAllAndReload($grid, false);
                    $dialog.dialog('destroy');
                }
                disableToolbarButton(false);//启用
            }, 'json');
        }

        var unselectAllAndReload = function (grid) {
            grid.datagrid('unselectAll');
            grid.datagrid('reload');
        }

        var getBulidCheck = function () {
            //   SysInfo|system|0.1.1.0.1.1.0.1.0,SysUser|system|0.1.1.0.1.1.0.0.0
            var size = $('#iptSize').val();
            var instructsArr = [];

            for (var i = 0; i < size; i++) {
                var privilege = [];
                var obj = document.getElementsByName('ck' + i);

                for (var j = 0; j < obj.length; j++) {
                    var checkVal = obj[j].checked ? 1 : 0;
                    privilege.push(checkVal);
                }
                var instructs = $('#spName' + i).text() + "|" + $('#spModular' + i).text() + "|" + privilege.join('.');

                instructsArr.push(instructs);
            }
            return instructsArr.join(',');
        }

        var messagerAlert = function (result) {
            if (result.success) {
                $.messager.alert('提示', '代码:' + result.code + '</br>内容:' + result.info, 'info');
            } else {
                $.messager.alert('提示', '代码:' + result.code + '</br>内容:' + result.info + '</br>详细:' + result.detail, 'error');
            }
        }

        /*启用或禁用工具条上的按钮,false 启用| true 禁用 */
        var disableToolbarButton = function (isDisable) {
            var pd = parent.sysExt.currentDialog;
            var toolbarBtnIds = [];
            if (typeof(pd) != 'undefined') {
                toolbarBtnIds = pd.dialog.defaults.toolbarBtnIds;
            }
            for (var i = 0; i < toolbarBtnIds.length; i++) {
                parent.$('#' + toolbarBtnIds[i]).linkbutton(isDisable ? 'disable' : 'enable');
            }
        }

    </script>
</head>
<input id="iptSize" type="hidden" value="${datas.size()}"/>
<table class="table2" style="font-size: 12px;margin: 10px;text-align: center">
    <tr>
        <th>Entity</th>
        <th>Module</th>
        <th>Ctro</th>
        <th>Api</th>
        <th>SerI</th>
        <th>SerImpl</th>
        <th>DaoI</th>
        <th>DaoImpl</th>
        <th>PageList</th>
        <th>PageForm</th>
        <th>Mapper</th>
        <th>Map.xml</th>
        <th>Res</th>
    </tr>

    <c:forEach var="entity" items="${datas}" varStatus="status">
        <tr>
            <td><span id="spName${status.index}">${entity.entityName}</span></td>
            <td><span id="spModular${status.index}">${entity.modular}</span></td>
            <td><input name="ck${status.index}" type="checkbox" value="" checked="true" <c:if test="${entity.hasController}">disabled="true" </c:if>/></td>
            <td><input name="ck${status.index}" type="checkbox" value="" checked="true" <c:if test="${entity.hasApiController}">disabled="true" </c:if>/></td>
            <td><input name="ck${status.index}" type="checkbox" value="" checked="true" <c:if test="${entity.hasService}">disabled="true" </c:if>/></td>
            <td><input name="ck${status.index}" type="checkbox" value="" checked="true" <c:if test="${entity.hasServiceImpl}">disabled="true" </c:if>/></td>
            <td><input name="ck${status.index}" type="checkbox" value="" checked="true" <c:if test="${entity.hasDao}">disabled="true" </c:if>/></td>
            <td><input name="ck${status.index}" type="checkbox" value="" checked="true" <c:if test="${entity.hasDaoImpl}">disabled="true" </c:if>/></td>
            <td><input name="ck${status.index}" type="checkbox" value="" checked="true" <c:if test="${entity.hasPageList}">disabled="true" </c:if>/></td>
            <td><input name="ck${status.index}" type="checkbox" value="" checked="true" <c:if test="${entity.hasPageForm}">disabled="true" </c:if>/></td>
            <td><input name="ck${status.index}" type="checkbox" value="" checked="true" <c:if test="${entity.hasMapper}">disabled="true" </c:if>/></td>
            <td><input name="ck${status.index}" type="checkbox" value="" checked="true" <c:if test="${entity.hasMapXML}">disabled="true" </c:if>/></td>
            <td><input name="ck${status.index}" type="checkbox" value="" checked="true" <c:if test="${entity.hasMenuResource}">disabled="true" </c:if>/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
