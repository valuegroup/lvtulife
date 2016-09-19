package com.lvtulife.system.controller;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.controller.StandardController;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Controller
@RequestMapping(value = SysConstants.URL_HEAD_SYSTEM + "/page")
public class SysPageController extends StandardController {
    private static final Logger logger = LoggerFactory.getLogger(SysPageController.class);

    private static final String WELCOME = "common/welcome";
    private static final String VERSION = "common/version";
    private static final String NORTH = "layout/north";
    private static final String MAIN = "layout/main";
    private static final String SOUTH = "layout/south";
    private static final String HOME = "common/home";
    private static final String INFO = "system/systemInfo";
    private static final String ICONS = "common/icons";
    private static final String NO_SECURITY = "error/noSecurity";
    /**
     * 验证码服务
     */
    @Resource(name = "imageCaptchaService")
    private ImageCaptchaService imageCaptchaService;


    /**
     * 欢迎页面
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return WELCOME;
    }


    /**
     * 系统更新日志
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public String version(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return VERSION;
    }


    /**
     * 管理界面
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return MAIN;
    }

    /**
     * 北部面板
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/north", method = RequestMethod.GET)
    public String north(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return NORTH;
    }

    /**
     * 南部面板
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/south", method = RequestMethod.GET)
    public String south(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return SOUTH;
    }

    /**
     * 首页
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return HOME;
    }


    /**
     * 系统信息
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return INFO;
    }

    /**
     * 浏览小图标
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/style/icons", method = RequestMethod.GET)
    public String icons(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return ICONS;
    }

    /**
     * 没有操作权限
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/error/noSecurity")
    public String noSecurity(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return NO_SECURITY;
    }


    /**
     * 生成验证码图片
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void getCode(HttpServletRequest request, HttpServletResponse response) {

        try {
            ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
            String captchaId = request.getSession().getId();

            //可使用SpringContextUtil.getBean的方式获取对象
            //ImageCaptchaService imageCaptchaService = (ImageCaptchaService) SpringContextUtil.getBean("imageCaptchaService");
            BufferedImage challenge = (BufferedImage) imageCaptchaService.getChallengeForID(captchaId, request.getLocale());

            ImageIO.write(challenge, "jpeg", jpegOutputStream);
            byte[] captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            ServletOutputStream responseOutputStream = response.getOutputStream();
            responseOutputStream.write(captchaChallengeAsJpeg);
            responseOutputStream.flush();
            responseOutputStream.close();

        } catch (Exception e) {
            logger.error("generate captcha image error: {}", e.getMessage());
        }

    }

}
