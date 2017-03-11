package com.nju.pams.backend.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nju.pams.cacheable.service.UserService;
import com.nju.pams.model.User;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/backend")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/showUser")  
    public String toIndex(HttpServletRequest request, Model model){  
		final JSONObject result = new JSONObject();
        int userId = Integer.parseInt(request.getParameter("id"));  
        User user = userService.getUserById(userId);  
        model.addAttribute("user", user);  
        result.put("user", user.toString());
        return "showUser";
        //return result.toString();  
	}

}
