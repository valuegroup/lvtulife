<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>更新日志</title>
    <jsp:include page="/views/include/incBootstrap.jsp"/>
    <%
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        String contextPath = request.getContextPath();
        String version = "50";
    %>
    <script type="text/javascript">
        var sysExt = sysExt || {};
        sysExt.contextPath = '<%=contextPath%>';
        sysExt.basePath = '<%=basePath%>';
        sysExt.version = '<%=version%>';
    </script>
    <link rel="stylesheet" href="<%=contextPath%>/static/style/images/version/version.css">
    <script>
        $(function () {

            var nextDataNumber = 3;
            var ajaxLoading = false;
            var docNode = $(document);

            var ulNode = $('ul.timeline');

            function initLiNodes() {
                var liNodes = ulNode.find('li'), count = liNodes.length, i, liNode, leftCount = nextDataNumber * 20;
                for (i = 0; i < count; i++) {
                    liNode = $(liNodes.get(i));
                    if (i % 2 !== 0) {
                        liNode.addClass('alt');
                    } else {
                        liNode.removeClass('alt');
                    }
                    liNode.find('.number').text(leftCount + count - i);
                }
            }


            $('#fetchNextData').click(function () {
                var $this = $(this);
                $this.addClass('disabled').text('正在加载后二十条数据...');
                ajaxLoading = true;

                $.get(sysExt.contextPath + '/static/shareFiles/version/version_data_' + nextDataNumber + '.txt', function (data) {
                    ajaxLoading = false;
                    ulNode.append(data);
                    $this.removeClass('disabled').text('后二十条数据');
                    nextDataNumber--;

                    if (nextDataNumber === 0) {
                        $this.hide();
                    }

                    initLiNodes();
                });

            });

            initLiNodes();

            docNode.scroll(function () {

                if (docNode.height() - $(window).height() - docNode.scrollTop() < 10) {
                    if (!ajaxLoading) {
                        $('#fetchNextData').click();
                    }
                }

            });

        });
    </script>
</head>
<body data-spy="scroll" data-target=".bs-docs-sidebar">
<div class="jumbotron subhead">
    <div class="container">
        <h1>
            更新记录</h1>

        <p class="lead">
            见证旅途生活的进步历程。</p>
    </div>
</div>
<div class="container">
    <ul class="timeline">

        <li>
            <div class="time">20160609</div>
            <div class="version">v1.4.5</div>
            <div class="number"></div>
            <div class="content">
<pre>
系统日志>BaseService不建议加入切面日志，如需要加入切面日志建议在业务层加入
系统日志>SysLogAspect类中调用的持久层方法请勿加入切面日志，否则会出现死循环
请求地址>优化现有请求地址格式“/模块/不带前缀的实体名称/方法名称”
</pre>
            </div>
        </li>

        <li>
            <div class="time">20160603</div>
            <div class="version">v1.4.4</div>
            <div class="number"></div>
            <div class="content">
<pre>
切面日志>基础配置，分别在spring.xml 和 servlet.xml 中 添加 "aop:aspectj-autoproxy" 和 "aop:aspectj-autoproxy proxy-target-class=true"，需独立开启
切面日志>记录系统异常日志时无法写入数据库的问题，原因是事物回滚了，解决方法将tx:annotation-driven order设置成2;SysLogAspect order设置成1优先于事物处理
前端通用>在列表页面中，字段类型为选项时，字体颜色随机，以便更加直观
系统日志>增加切面日志查询模块
系统日志>BaseService不建议加入切面日志，如需要加入切面日志建议在业务层加入
系统日志>SysLogAspect类中调用的持久层方法请勿加入切面日志，否则会出现死循环
</pre>
            </div>
        </li>

        <li>
            <div class="time">20160602</div>
            <div class="version">v1.4.3</div>
            <div class="number"></div>
            <div class="content">
<pre>
切面日志>记录系统操作日志，功能使用AOP开发，通过@LogController 和 @LogService 分别记录 Controller 层和 service 层的操作日志
</pre>
            </div>
        </li>

        <li>
            <div class="time">20160601</div>
            <div class="version">v1.4.2</div>
            <div class="number"></div>
            <div class="content">
<pre>
系统框架>集成Mybatis框架，增加 HeaderOnlyOAuth2ExceptionRenderer 用于更改错误返回信息，将原有的XML转换成JSON
通用功能>完成GTD业务中公共常量的编写（17个）
</pre>
            </div>
        </li>

        <li>
            <div class="time">20160531</div>
            <div class="version">v1.4.1</div>
            <div class="number"></div>
            <div class="content">
<pre>
功能开发>代码一键构造功能增加Mybatis代码逻辑
</pre>
            </div>
        </li>

        <li>
            <div class="time">20160530</div>
            <div class="version">v1.4.0</div>
            <div class="number"></div>
            <div class="content">
<pre>
v1.4.X 版本开始↑
版本说明：
本版本主要开发GTD和会员中心业务，同时在开发过程中完善底层框架和加入通用的组件或功能
</pre>
            </div>
        </li>

        <li>
            <div class="time">20160530</div>
            <div class="version">v1.3.5</div>
            <div class="number"></div>
            <div class="content">
<pre>
v1.3.X 版本结束↓
版本说明：
本版本主要对底层的框架进行了改动，重新优化了系统结构
集成Spring Security 实现安全控制
集成Spring Oauth2 可提供开放平台
集成Mybatis 持久层框架，结合Hibernate一同使用
优化 src 和 webapp 文件结构
重构 代码一键生成功能
修复版本升级后遗留的老问题
</pre>
            </div>
        </li>

        <li>
            <div class="time">20160530</div>
            <div class="version">v1.3.4</div>
            <div class="number"></div>
            <div class="content">
<pre>
系统框架>集成Mybatis，BaseMapper 继承 BaseService
开发规范>新增修改删除使用Hibernate,复杂查询使用Mybatis
系统框架>集成Mybatis，基础的用法示例
系统框架>集成Mybatis，事物测试，可否使用Hibernate的事物来管理Mybatis
</pre>
            </div>
        </li>

        <li>
            <div class="time">20160519</div>
            <div class="version">v1.3.3</div>
            <div class="number"></div>
            <div class="content">
<pre>
系统框架>集成Mybatis,将dao、entity、mapper统一放置在mybatis包下，以便初始化时自动扫描文件
通用功能>新增代码自动生成功能,可生成控制层，业务层，持久层，标准的列表页和表单页，同时会在资源表中增加相应资源
开发说明>base、system 模块下持久层框架暂时使用Hibernate,其他模块
</pre>
            </div>
        </li>

        <li>
            <div class="time">20160510</div>
            <div class="version">v1.3.2</div>
            <div class="number"></div>
            <div class="content">
<pre>
通用功能>优化404\500等页面的展现结果，方便开发调试
页面跳转>修复指向根节点（/index.jsp）报400错误,原因是路径写错误了
</pre>
            </div>
        </li>

        <li>
            <div class="time">20160503</div>
            <div class="version">v1.3.1</div>
            <div class="number"></div>
            <div class="content">
<pre>
项目结构>优化webapp层结构，区分static(静态文件，如css,img,js等)和views(视图等，如html,jsp等)
</pre>
            </div>
        </li>

        <li>
            <div class="time">20160428</div>
            <div class="version">v1.2.30</div>
            <div class="number"></div>
            <div class="content">
<pre>
系统底层>修改web.xml中DispatcherServlet的拦截标识，将url-pattern中"*.do"改成"/"
[待完成]系统框架>集成spring security；开启csrf
</pre>
            </div>
        </li>

        <li>
            <div class="time">20160427</div>
            <div class="version">v1.2.29</div>
            <div class="number"></div>
            <div class="content">
<pre>
框架集成>集成Oauth2,解决Oauth2授权请求成功后 Json Bean 返回值和示例项目中的返回值不一致的问题,昨天临时将fastjson 改成 jackson，暂时可以解决问题，现在更换成另外一种解决方式，在servlet.xml中message-converters添加自定义的oAuth2AccessTokenHttpMessageConverter
退出系统>修复退出系统时弹出异常的问题，原是post请求，这种方式不合适，改成了【location.replace(sysExt.contextPath + '/logout');】
权限控制>修复访问系统重要链接时，部分内容可以访问，解决方案【security:intercept-url pattern="/**" access="isAuthenticated()"】
</pre>
            </div>
        </li>

        <li>
            <div class="time">20160425</div>
            <div class="version">v1.2.28</div>
            <div class="number"></div>
            <div class="content">
<pre>
框架集成>集成Oauth2,解决无法正常访问oauth/authorize等oauth2等相关的请求，解决方法：修改 web.xml 中 DispatcherServlet 的 url-pattern，改“*.do”为 “/”
框架集成>集成Oauth2,解决authorization_code授权码模式，在登录后无法跳转到登录前的页面.解决方法：设置customUsernamePasswordFilter 的 authenticationSuccessHandler 的 alwaysUseDefaultTargetUrl 属性 = false
框架集成>集成Oauth2,解决Oauth2授权请求成功后 Json Bean 返回值和示例项目中的返回值不一致的问题,暂时将fastjson 改成 jackson

框架集成>集成Oauth2,调试authorization_code授权码模式(即先登录获取code,再获取token) [最常用]
框架集成>集成Oauth2,调试password密码模式(将用户名,密码传过去,直接获取token) [适用于移动设备]
框架集成>集成Oauth2,调试client_credentials客户端模式(无用户,用户向客户端注册,然后客户端以自己的名义向'服务端'获取资源)
框架集成>集成Oauth2,调试implicit简化模式(在redirect_uri 的Hash传递token; Auth客户端运行在浏览器中,如JS,Flash)
框架集成>集成Oauth2,调试refresh_token刷新access_token
</pre>
            </div>
        </li>

        <li>
            <div class="time">20160421</div>
            <div class="version">v1.2.27</div>
            <div class="number"></div>
            <div class="content">
<pre>
底层优化>优化SysConstants.java类中部分常量命名方式
框架集成>集成Oauth2,了解OAuth的机制原理，下载网上的示例分析学习
</pre>
            </div>
        </li>

        <li>
            <div class="time">20160420</div>
            <div class="version">v1.2.26</div>
            <div class="number"></div>
            <div class="content">
<pre>
底层优化>优化RequestUrlBean中获取所有@RequestMapping()的请求地址的方法。最开始在Controller中使用注入RequestMappingHandlerMapping的方式，发现在集成Oauth2会出现异常；接下来使用类去继承RequestMappingHandlerMapping，发现会加载两次，最后采用【SpringContextUtil.getApplicationContext().getBean(RequestMappingHandlerMapping.class);】的方式解决问题
</pre>
            </div>
        </li>

        <li>
            <div class="time">20160419</div>
            <div class="version">v1.2.25</div>
            <div class="number"></div>
            <div class="content">
<pre>
系统框架>集成spring security oauth2；在配置文件中配置oauth2
系统框架>集成spring security oauth2；在数据库中添加oauth2相关的数据表
升级JDK>将JDK升级到1.8，可以适应时间的新特性
Idea配置>调整至Project language level 8 - Lambadas
运行环境>将idea运行调试配置中的JRE改成1.8，否则会出现【Unsupported major.minor version 52.0】的异常
</pre>
            </div>
        </li>

        <li>
            <div class="time">20160418</div>
            <div class="version">v1.2.24</div>
            <div class="number"></div>
            <div class="content">
<pre>
功能模块>所有功能调试测试通过
权限数据>所有权限数据优化完毕
程序启动>优化StartListener中对SecurityMetadataSourceManagerImpl.addDefaultResourceMap()的调用方式
系统框架>集成spring security；权限加载方法优化完毕
程序启动>启动速度优化，SecurityMetadataSourceManagerImpl循环调用了SysUserRoleServiceImpl的一个处理字符串的方法，速度极慢，后将此方法改到StringUtil.splitJointUrl(...)方法中
系统框架>集成spring security；使用MD5加密，并添加动态加密盐
系统框架>集成spring security；优化logout体验
</pre>
            </div>
        </li>
        <li>
            <div class="time">20160417</div>
            <div class="version">v1.2.23</div>
            <div class="number"></div>
            <div class="content">
<pre>
资源管理>优化角色资源基础数据，剔除controller中未引用的方法
</pre>
            </div>
        </li>
        <li>
            <div class="time">20160416</div>
            <div class="version">v1.2.22</div>
            <div class="number"></div>
            <div class="content">
<pre>
通用功能>优化删除方法和批量删除方法
用户管理>优化删除功能，检查限制条件，管理员用户删除需要先解除管理员状态
角色管理>优化删除功能，检查限制条件，内置角色不允许删除
角色管理>优化修改功能，内置角色不允许修改
资源管理>调整表结构，移除表中url,path字段，新增url_head,url_module,url_method,is_auth_point字段
角色授权>优化资源树内容，增加系统内置权限点功能
</pre>
            </div>
        </li>

        <li>
            <div class="time">20160415</div>
            <div class="version">v1.2.21</div>
            <div class="number"></div>
            <div class="content">
<pre>
前端页面>优化前端页面代码，将所有URL调用都配置成动态的
个人信息>修复页面无法打开，数据无法加载的情况
系统信息>修复页面无法打开，数据无法加载的情况
更新日志>新增 version.jsp 用于展示更新日志信息，界面使用垂直时间轴，可动态刷新数据
更新日志>将原 welcome.jsp 页面中的更新日志以文本的方式存放在shareFiles/version/version_data_x.txt中，每20条记录为一个文件
前端插件>引入前端开发框架 bootstrap-3.3.5
前端插件>更新jquery-1.12.3.min.js 和 jquery-2.2.3.min.js
</pre>
            </div>
        </li>
    </ul>
    <div style="margin-left:180px;">
        <button id="fetchNextData" class="btn btn-large btn-info" style="width:100%;">查看更多(20)</button>
    </div>
    <br/>
    <br/>
</div>
</body>
</html>