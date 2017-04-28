package com.nju.pams.background.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nju.pams.model.constant.PathConstant;

@Controller
@RequestMapping(value = PathConstant.WEB_ERROR)
public class WebErrorController {
	
	//权限不足访问的页面
    @RequestMapping(value = "unauthorized")
    public String getUnauthorizedPage(){
        return "error/unauthorized";
    }
    
	//报错访问的页面
    @RequestMapping(value = "error")
    public String getErrorPage(){
        return "error/error";
    }

}