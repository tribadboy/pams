package com.nju.pams.background.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nju.pams.model.constant.PathConstant;

@Controller
@RequestMapping(value = PathConstant.BACKGROUND_AUTHC)
public class WebAuthcController {
	
	// 登录成功的页面
    @RequestMapping(value = "home")
    public String adminHomePage(){
        return "authc/home";
    }

    // 只有角色为admin的才能访问
    @RequiresRoles("vip")
    @RequestMapping(value = "role")
    public String adminWithRole(){
        return "authc/role";
    }

    // 有user:view和user:create权限才能访问
    @RequiresPermissions(value={"vip:create","normal:query"}, logical= Logical.OR)
    @RequestMapping(value = "auth")
    public String adminWithAuth(){
        return "authc/auth";
    }
}