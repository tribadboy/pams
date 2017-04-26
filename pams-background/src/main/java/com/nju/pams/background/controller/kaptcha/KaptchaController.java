package com.nju.pams.background.controller.kaptcha;

import java.awt.image.BufferedImage;  

import javax.imageio.ImageIO;  
import javax.servlet.ServletOutputStream;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;  
  
import com.google.code.kaptcha.Constants;  
import com.google.code.kaptcha.Producer;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.util.ResultUtil;
import com.nju.pams.util.constant.ResultEnum;

import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.BACKGROUND_CODE)
public class KaptchaController {  
	
	@Autowired  
    private Producer captchaProducer = null;  
    
    private static final Logger logger = Logger.getLogger(KaptchaController.class);

    /**
     * 生成kaptcha验证码图片
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("kaptcha-img")  
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{  
    	//生成验证码，并存入session中
        String code = captchaProducer.createText(); 
    	HttpSession session = request.getSession();  
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, code);  
        logger.info("generate verification code: " + code);
        //设置响应消息类型
        response.setDateHeader("Expires", 0);  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/jpeg");  
        //根据验证码生成图片
        BufferedImage bi = captchaProducer.createImage(code);  
        ServletOutputStream out = response.getOutputStream();  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
        return null;  
    }  
    
    /**
     * 检查验证码是否正确
     * @param kaptcha
     * @param request
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "verification", method = RequestMethod.POST)
	public String checkVerificationCode(@RequestParam("kaptcha") final String kaptcha,
			HttpServletRequest request) {
		final JSONObject result = new JSONObject();
		
		logger.info("收到用户输入的验证码：" + kaptcha);
		String code = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if(null == code || null == kaptcha || !code.equalsIgnoreCase(kaptcha)) {
			ResultUtil.addResult(result, ResultEnum.ErrorVerificationCode);
			return result.toString();
		}
		ResultUtil.addSuccess(result);
		return result.toString();
	}
}  