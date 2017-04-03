package com.nju.pams.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nju.pams.biz.service.PamsUserService;
import com.nju.pams.model.PamsUser;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.util.ResultUtil;
import com.nju.pams.util.constant.ResultEnum;

import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_USER)
public class WebUserController {  
	
	@Autowired
	PamsUserService pamsUserService;
    
    private static final Logger logger = Logger.getLogger(WebUserController.class);
    
    /**
     * 检查用户名是否已被注册
     * @param username
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "checkUsername", method = RequestMethod.POST)
	public String checkUsername(@RequestParam("username") final String username) {
		final JSONObject result = new JSONObject();
		
		logger.info("收到用户注册输入的用户名：" + username);
		if(null == username) {
			ResultUtil.addResult(result, ResultEnum.NullParameter);
			return result.toString();
		} else if(pamsUserService.getPamsUserByUsername(username) != null) {
			ResultUtil.addResult(result, ResultEnum.DuplicateUsername);
			return result.toString();
		}
		ResultUtil.addSuccess(result);
		return result.toString();
	}
    
    /**
     * 返回注册页面jsp
     * @return
     */
    @RequestMapping(value = "registerPage")
    public String registerPage(){
        return "anon/register";
    }
    
    /**
     * 接收注册信息，返回注册结果
     * @param model
     * @param request
     * @param username
     * @param password
     * @param phone
     * @param mail
     * @return
     */
   	@RequestMapping(value = "register", method = RequestMethod.POST)
   	public String registerUsername(Model model, HttpServletRequest request, 
   			@RequestParam("username") final String username,
   			@RequestParam("password") final String password,
   			@RequestParam("phone") final String phone,
   			@RequestParam("email") final String email
   			) {
   		logger.info("注册用户");
   		String message;		//注册失败时，存储失败信息，返回给页面
   		if(null == username || null == password || null == phone || null == email) {
   			message = ResultEnum.NullParameter.getMsg();
   			model.addAttribute("message", message);
   	        logger.info("注册异常：" + message);
   	        return "anon/register";
		} else if(pamsUserService.getPamsUserByUsername(username) != null) {
			message = ResultEnum.DuplicateUsername.getMsg();
   			model.addAttribute("message", message);
   	        logger.info("注册异常：" + message);
   	        return "anon/register";
		}
   		//创建用户并插入数据库
   		PamsUser newUser = new PamsUser(username, password, 0, phone, email);
   		pamsUserService.insertPamsUser(newUser);
   		
   		//将用户信息通过shiro认证
        UsernamePasswordToken token = new UsernamePasswordToken(newUser.getUsername(), newUser.getPassword());
        token.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        request.getSession().setAttribute("username",username);
        logger.info("注册用户成功，在session中记录username：" + request.getSession().getAttribute("username"));
        model.addAttribute("username",username);
        
        return "redirect:" + PathConstant.WEB_AUTHC + "home";
   	}
   	
   	
   	@RequestMapping(value = "update", method = RequestMethod.POST)
   	public String updateUserInfo(Model model,
   			@RequestParam("username") final String username,
   			@RequestParam("password") final String password,
   			@RequestParam("phone") final String phone,
   			@RequestParam("email") final String email
   			) {
   		logger.info("修改用户信息");
   		String message;		//修改失败时，存储失败信息，返回给页面
   		if(null == username || null == password || null == phone || null == email) {
   			message = ResultEnum.NullParameter.getMsg();
   			model.addAttribute("message", message);
   	        logger.info("用户信息修改异常：" + message);
   	        return "authc/userInfo";
		} 
   		PamsUser pamsUser = pamsUserService.getPamsUserByUsername(username);
   		if(null == pamsUser) {
   			message = ResultEnum.UsernameNotExist.getMsg();
   			model.addAttribute("message", message);
   	        logger.info("用户信息修改异常：" + message);
   	        return "anon/login";
   		}
   		//更信息用户信息
   		pamsUser.setPassword(password);
   		pamsUser.setPhone(phone);
   		pamsUser.setMail(email);
   		pamsUserService.updatePamsUser(pamsUser);
        
        return "redirect:" + PathConstant.WEB_AUTHC + "home";
   	}
}  