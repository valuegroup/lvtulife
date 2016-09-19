<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%-- 引入plupload --%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/jslib/plupload-2.1.8/js/plupload.full.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/jslib/plupload-2.1.8/js/i18n/zh_CN.js"></script>
<script type="text/javascript">
    var uploader;//上传对象
    $(function () {
        uploader = new plupload.Uploader({//上传插件定义
            browse_button: 'pickfiles',//选择文件的按钮
            //browse_button : 'browse',
            container: 'container',//文件上传容器
            runtimes: 'html5,flash',//设置运行环境，会按设置的顺序，可以选择的值有html5,gears,flash,silverlight,browserplus,html4
            flash_swf_url: '<%=request.getContextPath()%>/jslib/plupload-2.1.8/js/Moxie.swf',// Flash环境路径设置
            silverlight_xap_url : '<%=request.getContextPath()%>/jslib/plupload-2.1.8/js/Moxie.xap',
            url: '<%=request.getContextPath()%>/system/sysUser/upload',//上传文件路径
            max_file_size: '5mb',//100b, 10kb, 10mb, 1gb
            file_post_name: 'fileData',
            chunk_size: '10mb',//分块大小，小于这个大小的不分块
            unique_names: true,//生成唯一文件名
            // 指定要浏览的文件类型
            filters: [{
                title: '图片文件',
                extensions: 'jpg,jpeg,gif,png'
            }]
        });


        uploader.bind('Init', function (up, params) {//初始化时
            //$('#filelist').html("<div>当前运行环境: " + params.runtime + "</div>");
            $('#filelist').html("");
        });
        uploader.bind('BeforeUpload', function (uploader, file) {//上传之前
            if (uploader.files.length > 1) {
                parent.$.messager.alert('提示', '只允许选择一张照片！', 'error');
                uploader.stop();
                return;
            }
            $('.ext-icon-cross').hide();
        });
        uploader.bind('FilesAdded', function (up, files) {//选择文件后
            $.each(files, function (i, file) {
                $('#filelist').append('<div id="' + file.id + '">' + file.name + '(' + plupload.formatSize(file.size) + ')<strong></strong>' + '<span onclick="uploader.removeFile(uploader.getFile($(this).parent().attr(\'id\')));$(this).parent().remove();" style="cursor:pointer;" class="ext-icon-cross" title="删除">&nbsp;&nbsp;&nbsp;&nbsp;</span></div>');
            });
            up.refresh();
        });
        uploader.bind('UploadProgress', function (up, file) {//上传进度改变
            var msg;
            if (file.percent == 100) {
                msg = '99';//因为某些大文件上传到服务器需要合并的过程，所以强制客户看到99%，等后台合并完成...
            } else {
                msg = file.percent;
            }
            $('#' + file.id + '>strong').html(msg + '%');

            parent.sysExt.progressBar({//显示文件上传滚动条
                title: '文件上传中...',
                value: msg
            });
        });
        uploader.bind('Error', function (up, err) {//出现错误
            $('#filelist').append("<div>错误代码: " + err.code + ", 描述信息: " + err.message + (err.file ? ", 文件名称: " + err.file.name : "") + "</div>");
            up.refresh();
        });
        uploader.bind('FileUploaded', function (up, file, info) {//上传完毕
            var response = $.parseJSON(info.response);
            if (response.success) {
                $('#' + file.id + '>strong').html("100%");
                //console.info(response.fileUrl);
                //console.info(file.name);
                //$('#f1').append('<input type="hidden" name="fileUrl" value="'+response.fileUrl+'"/>');
                //$('#f1').append('<input type="hidden" name="fileName" value="'+file.name+'"/><br/>');
                $(':input[name="photo"]').val(response.fileUrl);
            }
        });
        uploader.init();
    });
</script>