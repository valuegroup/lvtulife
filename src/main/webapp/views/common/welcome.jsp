<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>更新日志</title>
    <%--<jsp:include page="/views/include/inc.jsp"/>--%>

    <style type="text/css">

        #logs ul {
            border-top: 1px dotted #036;
            width: 90%;
            padding-bottom: 15px;
            padding-top: 10px;
            margin-left: 20px;
        }

        #logs ol {
            border-top: 1px dotted #036;
            width: 90%;
            padding-bottom: 15px;
            padding-top: 10px;
            margin-left: 20px;
        }

        #logs label {
            margin-left: 20px;
        }

        .finish {
            float: left;
            width: 600px;
            margin-right: 60px;
        }

        .unfinish {
            float: left;
            width: 600px;
        }
    </style>

</head>
<body>
<div id="logs" class="finish">

    <label style="color:blue">当前开发版本：V1.4</label>
    <ul>
    </ul>

    <label>待完成内容</label>
    <ul>
        <li>系统框架>Redis集成和优化</li>
        <li>系统框架>Solr集成和优化</li>
        <li>系统框架>加入Swagger UI(是一款RESTFUL接口的文档在线自动生成+功能测试的软件)</li>
        <li>系统框架>ActiveMQ消息队列</li>
        <li>系统框架>ApacheCXF的集成和优化</li>
        <li>前端框架>引入新的前端框架AngularJS</li>
        <li>系统前端>集成bootstrap前端开发框架</li>
        <li>系统前端>集成ECharts前端统计图表框架</li>
        <li>通用功能>添加公有的拖拽排序功能(能适应不同实体)</li>
        <li>数据结构>初始化数据库数据，剔除开发中的垃圾数据</li>
        <li>数据结构>对各个表的重要字段建立索引,优化查询速度</li>
        <li>部署测试>数据压力测试</li>
    </ul>
</div>
<div id="logs2" class="unfinish">

</div>
</body>
</html>