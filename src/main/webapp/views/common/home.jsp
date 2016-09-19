<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>旅途生活-做你想做的梦</title>
    <jsp:include page="/views/include/inc.jsp"/>
    <jsp:include page="/views/include/unslider.jsp"/>
    <script type="text/javascript">
        $(function () {
            if (window.chrome) {
                $('.banner li').css('background-size', '100% 100%').css('height', '406px');
            }
            $('.banner').unslider({
                arrows: true,
                fluid: true, //显示点导航
                keys: true, //支持键盘导航
                dots: true
            });
        });
    </script>
    <script>
        var _hmt = _hmt || [];
        (function () {
            var hm = document.createElement("script");
            hm.src = "//hm.baidu.com/hm.js?1a82e95f78b3b25f639124f869438cd3";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/jslib/unslider/site/style.css" type="text/css"/>
    <style type="text/css">
        .lvtulife-header-link {
            display: inline;
            float: right;
            overflow: hidden;
            border: 0px red solid;
            margin-right: 400px;
        }

        .lvtulife-header-logo {
            display: inline;
            float: left;
            overflow: hidden;
            border: 0px red solid;
            margin-left: 320px;
            margin-top: 18px;
        }

        .lvtulife-header-link li {
            display: inline;
            float: left;
        }

        .lvtulife-header-link a {
            display: block;
            padding: 31px 22px 30px 22px;
        }

        .lvtulife-header-log {
            display: inline;
            float: right;
            padding: 32px 12.5px 0 0;
            text-align: right;
            font-size: 12px;
        }

        .lvtulife-header {
            height: 85px;
            width: 100%;
            font-size: 14px;
            font-family: "微软雅黑", "Microsoft Yahei", Arial, Helvetica, sans-serif, "宋体";
            position: relative;
        }

        .imgA {
            background-image: url('<%=request.getContextPath()%>/static/jslib/unslider/site/img/A.jpg');
        }

        .imgB {
            background-image: url('<%=request.getContextPath()%>/static/jslib/unslider/site/img/B.jpg');
        }

        .imgC {
            background-image: url('<%=request.getContextPath()%>/static/jslib/unslider/site/img/C.jpg');
        }

        .imgD {
            background-image: url('<%=request.getContextPath()%>/static/jslib/unslider/site/img/D.jpg');
        }

        .imgE {
            background-image: url('<%=request.getContextPath()%>/static/jslib/unslider/site/img/E.jpg');
        }

    </style>
</head>
<body>
<div class="lvtulife-header">
    <ul class="lvtulife-header-logo">
        <li><img src="<%=request.getContextPath()%>/static/style/images/logo.png" alt="Unslider logo" width="197" height="53"/></li>
    </ul>
    <ul class="lvtulife-header-link">
        <li><a>大道自然</a></li>
        <li><a>美好生活</a></li>
        <li><a>学而有用</a></li>
        <li><a>无穷视野</a></li>
    </ul>
</div>
<div class="banner">
    <ul>

        <li class="imgA">
            <div class="inner">
                <h1>记录过去，规划未来</h1>

                <p>过去的和未来的同样重要</p>
            </div>
        </li>
        <li class="imgD">
            <div class="inner">
                <h1>掌控时间，精彩生活</h1>

                <p>干你想干的事,做你想做的梦</p>
            </div>
        </li>
        <li class="imgB">
            <div class="inner">
                <h1>张弛有度，轻重缓急</h1>

                <p>生命有限，分清轻重缓急乃第一要务</p>
            </div>
        </li>
        <li class="imgC">
            <div class="inner">
                <h1>习惯十倍于自然</h1>

                <p>人喜欢习惯，因为造它的就是自己</p>
            </div>
        </li>
        <li class="imgE">
            <div class="inner">
                <h1>大道自然</h1>

                <p>皆由心生，皆由心灭</p>
            </div>
        </li>
    </ul>
</div>
<div class="features" style="background:#f7f7f6;">
    <ul class="wrap">
        <li class="browser"><b>随时随地伴您左右</b>

            <p>PC,网页,移动终端<br>实时为您跨设备跨平台同步</p></li>
        <li class="keyboard"><b>智能提醒</b>

            <p>每天完成多一点的梦想</p></li>
        <li class="height"><b>强大的性能</b>

            <p>享受无网络使用和零同步延迟</p></li>
        <li class="responsive"><b>安全恒久</b>

            <p>珍贵内容永久珍藏<br>资料加密绝不外泄</p></li>
    </ul>
    <div style="width:100%;text-align:center;position:fixed;_position:absolute;bottom:10px;_bottom:10px;_margin-top:expression(this.style.pixelHeight+document.documentElement.scrollTop)">
        <a href="http://www.miitbeian.gov.cn/" style="text-decoration: none;color: #999">Copyright 2014-2019 lvtulife.com All Rights Reserved 备案号：粤ICP备14085955号</a>
    </div>
</div>
</body>
</html>