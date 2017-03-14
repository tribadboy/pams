package com.nju.pams.web.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/shiro")
public class ShiroAdminController {
    // 登录成功的页面
    @RequestMapping(value = "/admin/shiroHome")
    public String adminHomePage(){
        return "admin/shiroHome";
    }

    // 只有角色为admin的才能访问
    @RequiresRoles("admin")
    @RequestMapping(value = "/admin/shiroWithRole")
    public String adminWithRole(){
        return "admin/shiroWithRole";
    }

    // 有user:view和user:create权限才能访问
    @RequiresPermissions(value={"user:view","user:create"}, logical= Logical.OR)
    @RequestMapping(value = "/admin/shiroWithAuth")
    public String adminWithAuth(){
        return "admin/shiroWithAuth";
    }
}