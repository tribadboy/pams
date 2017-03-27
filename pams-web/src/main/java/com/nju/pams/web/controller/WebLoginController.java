package com.nju.pams.web.controller;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.nju.pams.model.PamsUser;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.util.NullUtil;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = PathConstant.WEB_ANON)
public class WebLoginController {
	
    private static final Logger logger = Logger.getLogger(WebLoginController.class);
    
    //登录页面
    //url: http://localhost:8080/pams-web/web/anon/login.html
    @RequestMapping(value = "login.html")
    public String login(){
        logger.info("访问login.html,跳转到anon/login.jsp");
        return "/anon/login";
    }
    
    //浏览器重定向到登录页面
    //url: http://localhost:8080/pams-web/web/anon/logout.html
    @RequestMapping(value = "/logout.html")
    public String doLogout(HttpServletRequest request, Model model) {
        logger.info("======用户"+request.getSession().getAttribute("pamsUser")+"退出了系统");
        SecurityUtils.getSubject().logout();
        //使用重定向重新请求，而非直接到jsp
        return "redirect:" + PathConstant.WEB_ANON + "login.html";
    }
    
    
    //用户在login.jsp填写信息后登录，处理登录的业务逻辑
    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public String doLogin(PamsUser user, HttpServletRequest request, Model model) {
    	
        String message;		//message记录登录的错误信息，同步返回给用户，显示到login.jsp上
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        token.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            if (subject.isAuthenticated()) {
                request.getSession().setAttribute("username",user.getUsername());
                logger.info("doLogin:权限认证成功，在session中记录username：" + request.getSession().getAttribute("username"));
                
                SavedRequest savedRequest = WebUtils.getSavedRequest(request);
                model.addAttribute("username",user.getUsername());
                if (savedRequest == null || savedRequest.getRequestUrl() == null) {
                	//重定向到登录后的home的请求上，再转到对应的jsp页面
                    return "redirect:" + PathConstant.WEB_AUTHC + "home";
                } else {
                    //服务器请求转发到保存的url
                	String lastUrl = savedRequest.getRequestUrl()
                			.concat(NullUtil.notNullProcess(savedRequest.getQueryString()))
                			.replaceFirst(NullUtil.notNullProcess(request.getContextPath()), "");
                	logger.info("登录后跳转到原请求页面：" + lastUrl);
                    return "redirect:" + lastUrl;
                }
            } else {
            	//subject 认证失败,返回到登录界面并显示
            	return "/anon/login";
            }
        } catch (IncorrectCredentialsException e) {
            message = "登录密码错误";
        } catch (ExcessiveAttemptsException e) {
            message = "登录失败次数过多";
        } catch (LockedAccountException e) {
            message = "帐号已被锁定";
        } catch (DisabledAccountException e) {
            message = "帐号已被禁用";
        } catch (ExpiredCredentialsException e) {
            message = "帐号已过期";
        } catch (UnknownAccountException e) {
            message = "帐号不存在";
        } catch (UnauthorizedException e) {
            message = "您没有得到相应的授权！";
        }
        model.addAttribute("message", message);
        logger.info("登录异常：" + message);
        return "/anon/login";
    }
}