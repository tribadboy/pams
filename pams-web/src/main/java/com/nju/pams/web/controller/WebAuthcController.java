package com.nju.pams.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nju.pams.model.constant.PathConstant;

@Controller
@RequestMapping(value = PathConstant.WEB_AUTHC)
public class WebAuthcController {
	
	// 登录成功的页面
    @RequestMapping(value = "home")
    public String adminHomePage(){
        return "authc/home";
    }

}