<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.lvtulife.system.component.vo.SessionInfo" %>
<%@ page import="com.lvtulife.base.component.constants.SysConstants" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%
    String contextPath = request.getContextPath();
    SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");

    String mainNorthUrl = contextPath + SysConstants.URL_HEAD_SYSTEM + "/page/north";
    String mainSouthUrl = contextPath + SysConstants.URL_HEAD_SYSTEM + "/page/south";
    String mainWelcomeUrl = contextPath + SysConstants.URL_HEAD_SYSTEM + "/page/welcome";
    String mainLoginUrl = contextPath + SysConstants.URL_HEAD_SYSTEM + "/user/login";
    String getMenusUrl = contextPath + SysConstants.URL_HEAD_SYSTEM + "/resource/getMenus";
    String updateCurrentPwdUrl = contextPath + SysConstants.URL_HEAD_SYSTEM + "/user/updateCurrentPwd";
%>
<!DOCTYPE html>
<html>
<head>
    <title>旅途生活</title>
    <jsp:include page="/views/include/inc.jsp"/>
    <style>
        .tt-inner {
            display: inline-block;
            line-height: 12px;
            padding-top: 5px;
        }

        .tt-inner img {
            border: 0;
        }
    </style>
    <script type="text/javascript">
        var menuTreeArr = []; //系统资源树集合
        var mainTabs;

        var reloadNorthFun = function () {
            <%--
            用于重新加载north面板,如用户当前用户信息更新后
            可用  $('#mainLayout').layout('panel','north').panel({href:'/security/north.jsp'});
            可用  $('#mainLayout').layout('panel','north').panel('refresh','/security/north.jsp');
            使用方法：一二级页面，parent.reloadNorthFun();
            参考案例：用户信息编辑
            --%>
            $('#mainLayout').layout('panel', 'north').panel('refresh');
        };

        var reloadMenuTreeFun = function () {
            <%--
            用于更新资源列表后刷新资源树
            使用方法：一级子页面，parent.reloadMenuTreeFun();
                    二级子页面，在一级子页面将‘parent.reloadMenuTreeFun();’作为变量传至二级子页面，
                            二级子页面以方法的形式使用该变量如：reloadMenuTreeFun();
            参考案例：资源列表页面和资源列表编辑页面
            --%>
            for (var i = 0; i < menuTreeArr.length; i++) {
                menuTreeArr[i].tree('reload');
            }
        }
        function mainGetTab(value, type) {
            /* 获得指定的选项卡面板
             参数为空:返回当前选中的选项卡
             参数不为空:
             value='parent',type为空:返回当前选项卡的父选项卡
             type=类型,value=该类型值
             获得指定类型(index 索引、title 标题、tabId 选项卡id、tabPid 父选项卡id)的选项卡面板

             示例:
             parent.mainTabCloseFun('角色列表','title');
             parent.mainTabCloseFun(1,'index');
             parent.mainTabCloseFun('t2','tabId');
             parent.mainTabCloseFun('s13','tabPid');
             parent.mainTabCloseFun('parent');
             parent.mainTabCloseFun();
             */

            var tab;
            if (!sysExt.isNull(type) && !sysExt.isNull(value)) {
                if (type == 'index' || type == 'title') {
                    tab = mainTabs.tabs('getTab', value);
                } else if (type == 'tabId' || type == 'tabPid') {
                    var tabs = mainTabs.tabs('tabs');
                    for (var i = 0; i < tabs.length; i++) {
                        var tempId;
                        if (type == 'tabPid') {
                            tempId = tabs[i].panel('options').attributes.tabPid
                        } else {
                            tempId = tabs[i].panel('options').attributes.tabId
                        }
                        if (value == tempId) {
                            tab = tabs[i];
                        }
                    }
                } else {
                    tab = mainTabs.tabs('getSelected');//other
                }
            } else {
                tab = mainTabs.tabs('getSelected');
                if (value == 'parent') {
                    var tabs = mainTabs.tabs('tabs');
                    var tabPid = tab.panel('options').attributes.tabPid;
                    for (var i = 0; i < tabs.length; i++) {
                        if (tabPid == tabs[i].panel('options').attributes.tabId) {
                            tab = tabs[i];
                        }
                    }
                }
            }
            return tab;
        }

        function mainTabCloseAllFun() {
            //关闭所有选项卡  使用方法：一二级页面，parent.mainTabCloseAllFun();
            for (var i = 1; i > 0; i--) {
                var tab = mainTabs.tabs('getTab', i);
                if (tab != null) {
                    mainTabs.tabs('close', i);
                    i = 2;
                }
            }
        }

        function mainTabCloseFun(value, type) {
            //关闭指定选项卡  使用方法：一二级页面，parent.mainTabCloseFun();
            var tab = mainGetTab(value, type);
            var index = mainTabs.tabs('getTabIndex', tab);
            if (tab.panel('options').closable) {
                mainTabs.tabs('close', index);
            } else {
                mainShowSlide('[' + tab.panel('options').title + ']不可以被关闭！');
                mainTabRefreshFun();
            }
        }

        function mainTabCloseAndRefreshFun(value, type) {
            mainTabRefreshFun('parent');//刷新父选项卡
            mainTabCloseFun(value, type);//关闭当前选项卡
        }

        function mainTabRefreshFun(value, type) {
            //刷新指定选项卡  使用方法：一二级页面，parent.mainTabRefreshFun();
            var tab = mainGetTab(value, type);
            var panel = tab.panel('panel');
            var frame = panel.find('iframe');
            try {
                /*$('#mainTabs').tabs('update',{
                 tab:tab,
                 options : {
                 content :sysExt.formatString('<iframe src="{0}" allowTransparency="true" style="border:0;width:100%;height:99%;" frameBorder="0"></iframe>', frame[0].src),
                 }
                 });*/
                if (frame.length > 0) {
                    for (var i = 0; i < frame.length; i++) {
                        frame[i].contentWindow.document.write('');
                        frame[i].contentWindow.close();
                        frame[i].src = frame[i].src;
                    }
                    if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
                        try {
                            CollectGarbage();
                        } catch (e) {
                        }
                    }
                }

            } catch (e) {
            }
        }

        function mainTabNewFun(node, isMenu) {
            var tabs = $('#mainTabs');
            var tab = mainGetTab();
            var options = tab.panel('options').attributes;
            var url = node.url;
            var tabId = node.id;
            var tabPid = node.pid;
            if (typeof(isMenu) == 'undefined') {
                tabId = options.tabId + '_' + node.operation;
                tabPid = options.tabId;
            }

            var opts = {
                title: node.text,
                closable: true,//用于判断是否能关闭该选项卡
                iconCls: node.iconCls,
                content: sysExt.formatString('<iframe src="{0}" allowTransparency="true" style="border:0;width:100%;height:99%;" frameBorder="0"></iframe>', url),
                border: false,
                fit: true,
                attributes: {
                    'tabId': tabId,
                    'tabPid': tabPid
                }
            };
            if (tabs.tabs('exists', opts.title)) {
                tabs.tabs('select', opts.title);
                mainTabRefreshFun(); //打开后刷新
            } else {
                tabs.tabs('add', opts);
            }
        }

        function mainDialogCloseFun() {
            //关闭当前弹出的Dialog窗口  使用方法：一二级页面，parent.mainDialogCloseFun();
            if (typeof(sysExt.currentDialog) != 'undefined')
                sysExt.currentDialog.dialog('destroy');
        }

        function mainShowSlide(info) {
            //通用友好提示
            if (typeof(info) == 'undefined') {
                info = '请选择一条记录!';
            }
            $.messager.show({
                title: '提示',
                msg: info + '<br><font color=gray>窗口3秒后自动关闭.</font>',
                timeout: 3000,
                showType: 'slide',
                style: {
                    right: '',
                    top: document.body.scrollTop + document.documentElement.scrollTop,
                    bottom: ''
                }
            });
        }

        var loginFun = function () {
            if ($('#loginDialog form').form('validate')) {
                $('#loginBtn').linkbutton('disable');
                $.post('<%=mainLoginUrl%>', $('#loginDialog form').serialize(), function (result) {
                    if (result.success) {
                        $('#loginDialog').dialog('close');
                    } else {
                        $.messager.alert('提示', result.msg, 'error', function () {
                            $('#loginDialog form :input:eq(1)').focus();
                        });
                    }
                    $('#loginBtn').linkbutton('enable');
                }, 'json');
            }
        };

        function createMenuFun(menuId, menus) {
            <%--
            用于创建选项卡中的资源树，并将创建的资源树对象存在全局数组中，便于reload树等操作
            使用方法：menuId:资源树的ID;resTypeId:资源类型ID
            --%>
            var tempMenu = $('#' + menuId).tree({
                data: menus,
                parentField: 'pid',
                onSelect: function (node) {
                    $(this).tree('expand', node.target); //点击标题自动展开节点
                },
                onClick: function (node) {
                    if (!sysExt.isNull(node.attributes.url)) {
                        var url = sysExt.contextPath + node.attributes.url;
                        if (!sysExt.startWith(node.attributes.url, '/')) {
                            url = node.attributes.url;
                        }
                        if (node.attributes.target && node.attributes.target.length > 0) {
                            window.open(url, node.attributes.target);
                        } else {
                            node.url = url;
                            mainTabNewFun(node, true);
                        }
                    }
                }
            });
            menuTreeArr.push(tempMenu);//将创建的资源树对象存在全局数组中，便于reload树等操作
        }

        function createResTypeTabs(menuId, resTypeId, tabName, tabIcon, tabTitle) {
            <%--
            用于创建资源类型选项卡和各选项卡中的资源树
            使用方法：menuId:资源树的ID;resTypeId:资源类型ID;tabName:资源树上方选项卡标题;
                    tabIcon:资源树上方选项卡logo,同选项卡tabTitle一致;
                    tabTitle:下方选项卡标题,可用图标表示,也可以用标题表示,建议使用logo.为空值时使用logo,同tabIcon一致
            --%>
            if (sysExt.isNull(tabTitle)) {
                tabTitle = sysExt.formatString("<span class='tt-inner'><img class='iconImg {0}'/></span>", tabIcon);
            }
            var panelOptions = 'border:false,fit:true';//sysExt.formatString("title:'{0}',iconCls:'{1}',border:false,fit:true",tabName,tabIcon);
            var opts = {
                title: tabTitle,
                content: sysExt.formatString('<div class="easyui-panel" data-options="{0}" style="padding-top:10px;"><ul id="{1}"></ul></div>', panelOptions, menuId),
                border: false,
                fit: true
            };

            if ($('#main_resource_type_tabs').tabs('exists', opts.title)) {
                $('#main_resource_type_tabs').tabs('select', opts.title);
            } else {
                $('#main_resource_type_tabs').tabs('add', opts);
            }
        }

        function setResTypeTabs() {
            $.post('<%=getMenusUrl%>', function (result) {
                if (result.success) {
                    for (var i = 0; i < result.data.length; i++) {

                        var mtype = result.data[i].mtype;
                        var menus = result.data[i].menus;

                        createResTypeTabs(mtype.typeMark, mtype.id, mtype.description, mtype.iconCls, mtype.panelTitle);//创建选项卡和各选项卡中的资源树
                        createMenuFun(mtype.typeMark, menus);//创建选项卡中的菜单，采用全局数组方式存储资源树
                    }
                    <%--
                    createResTypeTabs('mainMenu',0,"系统基础","ext-icon-cog","");
                    createResTypeTabs('wmsMenu',1,"仓储管理","ext-icon-house","");
                    createResTypeTabs('emailMenu',2,"邮件营销","ext-icon-email","");
                    createResTypeTabs('planMenu',3,"目标执行","ext-icon-date_edit","");
                    --%>
                    $('.iconImg').attr('src', sysExt.pixel_0);//给一个0像素的图片占位，避免在图片周围出现边框
                    $('#main_resource_type_tabs').tabs('select', 0);//设置默认选中
                    //$('#main_resource_type_tabs').tabs('disableTab', 3);//设置不启用disableTab\启用enableTab
                } else {
                    $.messager.alert('提示', '代码:' + result.code + '</br>内容:' + result.info + '</br>详细:' + result.detail, 'error');
                }
            }, 'json');
        }

        $(function () {
            setResTypeTabs();//创建选项卡和各选项卡中的资源树

            $('#loginDialog').show().dialog({
                modal: true,
                closable: false,
                iconCls: 'ext-icon-lock_open',
                buttons: [{
                    id: 'loginBtn',
                    text: '登录',
                    handler: function () {
                        loginFun();
                    }
                }],
                onOpen: function () {
                    $('#loginDialog form :input[name="data.passWord"]').val('');
                    $('form :input').keyup(function (event) {
                        if (event.keyCode == 13) {
                            loginFun();
                        }
                    });
                }
            }).dialog('close');

            $('#passwordDialog').show().dialog({
                modal: true,
                closable: true,
                iconCls: 'ext-icon-lock_edit',
                buttons: [{
                    text: '修改',
                    handler: function () {
                        if ($('#passwordDialog form').form('validate')) {
                            $.post('<%=updateCurrentPwdUrl%>', {
                                'data.passWord': $('#pwd').val()
                            }, function (result) {
                                if (result.success) {
                                    $.messager.alert('提示', '密码修改成功！', 'info');
                                    $('#passwordDialog').dialog('close');
                                }
                            }, 'json');
                        }
                    }
                }],
                onOpen: function () {
                    $('#passwordDialog form :input').val('');
                }
            }).dialog('close');

            $('#mainLayout').layout('panel', 'center').panel({
                onResize: function (width, height) {
                    sysExt.setIframeHeight('centerIframe', $('#mainLayout').layout('panel', 'center').panel('options').height - 5);
                }
            });

            <%--
            $('#main_resource_type_tabs').tabs({
                onSelect: function(title,index){
                    var title = "系统基础";
                    $('#main_title_tabs_panel').panel({title:title,iconCls:'ext-icon-shape_align_left'});
                }
            });
            --%>

            mainTabs = $('#mainTabs').tabs({
                fit: true,
                border: false,
                margin: 0,
                tools: [
                    <%--{iconCls : 'ext-icon-arrow_up',handler : function() { mainTabs.tabs({ tabPosition : 'top' });}
                        }, {iconCls : 'ext-icon-arrow_left',handler : function() {mainTabs.tabs({ tabPosition : 'left' });}
                        }, {iconCls : 'ext-icon-arrow_down',handler : function() {mainTabs.tabs({ tabPosition : 'bottom' });}
                        }, {iconCls : 'ext-icon-arrow_right',handler : function() {mainTabs.tabs({ tabPosition : 'right' });}},
                    --%>
                    {
                        //text : '刷新',
                        plain: true,
                        iconCls: 'ext-icon-arrow_refresh_small',
                        handler: function () {
                            mainTabRefreshFun();
                        }
                    }, {
                        //text : '关闭所有',
                        plain: true,
                        iconCls: 'ext-icon-cancel',
                        handler: function () {
                            mainTabCloseAllFun();
                        }
                    }, {
                        //text : '关闭',
                        plain: true,
                        iconCls: 'ext-icon-cross',
                        handler: function () {
                            mainTabCloseFun();
                        }
                    }]
            });

        });

    </script>

</head>
<body id="mainLayout" class="easyui-layout"><!-- 70px -->
<div data-options="region:'north',href:'<%=mainNorthUrl%>',border : false,split:true"
     style="height: 70px; overflow: hidden;" <%--class="logo"--%>></div>
<div data-options="region:'west',href:'',split:true" style="width: 220px;">
    <%--<div id="main_title_tabs_panel" class="easyui-panel" data-options="title:'系统基础',border:false,fit:true">--%>
    <!-- 选项卡方式展现 -->
    <div id="main_resource_type_tabs" class="easyui-tabs" data-options="fit:true,border:false,tabPosition:'bottom'">
        <%--
        <div title="<span class='tt-inner'><img class='iconImg ext-icon-cog'/></span>">
            <div class="easyui-panel" data-options="title:'系统基础',iconCls:'ext-icon-cog',border:false,fit:true">
                <ul id="mainMenu"></ul>
            </div>
        </div>
        <div title="<span class='tt-inner'><img src='<%=contextPath%>/style/images/ext_icons/house.png'/></span>">
            <div class="easyui-panel" data-options="title:'仓储管理',iconCls:'ext-icon-house',border:false,fit:true">
                <ul id="wmsMenu"></ul>
            </div>
        </div>
        <div title="<span class='tt-inner'><img src='<%=contextPath%>/style/images/ext_icons/email/email.png'/></span>">
            <div class="easyui-panel" data-options="title:'邮件营销',iconCls:'ext-icon-email',border:false,fit:true">
                <ul id="emailMenu"></ul>
            </div>
        </div>
        <div title="<span class='tt-inner'><img src='<%=contextPath%>/style/images/ext_icons/Date/date_edit.png'/></span>">
            <div class="easyui-panel" data-options="title:'目标执行',iconCls:'ext-icon-date_edit',border:false,fit:true">
                <ul id="planMenu"></ul>
            </div>
        </div>
        --%>
    </div>
    <%--</div>--%>
    <%--
        <!--手风琴方式展现-->
        <div id="main_title_tabs_panel" class="easyui-panel" data-options="title:'功能导航',border:false,fit:true">
        <div id="main_acc" class="easyui-accordion" data-options="fit:true,border:false" style="width:195px;height:200px;">
            <div title="基础" data-options="iconCls:'icon-save',selected:true" style="overflow:auto;">
                <ul id="mainMenu"></ul>
            </div>
            <div title="仓储" data-options="iconCls:'ext-icon-reload'" style="padding:10px;"></div>
            <div title="邮件" data-options="iconCls:'ext-icon-reload'" style="padding:10px;"></div>
            <div title="目标" data-options="iconCls:'ext-icon-reload'" style="padding:10px;"></div>
        </div>
        </div>
    --%>
</div>
<div data-options="region:'center'" style="overflow: hidden;">
    <div id="mainTabs">
        <div title="首页" data-options="iconCls:'ext-icon-color_swatch',attributes:{'tabId':0,'tabPid':0}">
            <iframe src="<%=mainWelcomeUrl%>" allowTransparency="true" style="border: 0; width: 100%; height: 99%;" frameBorder="0"></iframe>
        </div>
    </div>
</div>
<div data-options="region:'south',href:'<%=mainSouthUrl%>',border:false"
     style="height: 30px; overflow: hidden;"></div>
<div id="loginDialog" title="解锁登录" style="display: none;">
    <form method="post" class="form" onsubmit="return false;">
        <table class="table">
            <tr>
                <th width="50">登录名</th>
                <%--<td><%=sessionInfo.getUser().getLoginName()%><input name="data.loginName" readonly="readonly" type="hidden" value="<%=sessionInfo.getUser().getLoginName()%>" /></td>--%>
                <td><sec:authentication property="name"/><input name="data.loginName" readonly="readonly" type="hidden" value="<sec:authentication property="name"/>"/></td>
            </tr>
            <tr>
                <th>密码</th>
                <td><input name="data.passWord" type="password" class="easyui-validatebox" data-options="required:true"/>
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="passwordDialog" title="修改密码" style="display: none;">
    <form method="post" class="form" onsubmit="return false;">
        <table class="table">
            <tr>
                <th>新密码</th>
                <td><input id="pwd" name="data.passWord" type="password" class="easyui-validatebox" data-options="required:true"/>
                </td>
            </tr>
            <tr>
                <th>重复密码</th>
                <td><input type="password" class="easyui-validatebox" data-options="required:true,validType:'eqPwd[\'#pwd\']'"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>