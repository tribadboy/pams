package com.nju.pams.web.controller;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nju.pams.cacheable.service.UserService;
import com.nju.pams.model.User;
import com.nju.pams.model.cacheable.UserCache;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/web")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired  
    private CacheManager cacheManager; 
	
	@RequestMapping("/showUser")  
    public String toIndex(HttpServletRequest request, Model model){  
		final JSONObject result = new JSONObject();
        int userId = Integer.parseInt(request.getParameter("id"));  
        User user = userService.getUserById(userId);  
        if(user == null) {
        	user = new User();user.setUsername("null");user.setPassword("null");
        }
        model.addAttribute("user", user);  
        result.put("user", user.toString());
        return "showUser";
        //return result.toString();  
	}
	
	
	@RequestMapping("/showUserCache")  
    public String toIndex2(HttpServletRequest request, Model model){  
		final JSONObject result = new JSONObject();
        int userId = Integer.parseInt(request.getParameter("id"));  
        UserCache userCache = userService.getUserCacheById(userId);  
        if(userCache == null) {
        	userCache = new UserCache(); userCache.setUsername("null"); userCache.setPassword("null");
        }
        model.addAttribute("user", userCache);  
        result.put("user", userCache.toString());
        return "showUser";
	}
	
	@RequestMapping("/deleteUserCache")  
    public String toIndex3(HttpServletRequest request, Model model){  
        int userId = Integer.parseInt(request.getParameter("id"));  
        userService.deleteUserCacheById(userId);  
        return "deleteUser";
	}
	
	@RequestMapping("/addUserCache")  
    public String toIndex4(HttpServletRequest request, Model model){  
		UserCache userCache = new UserCache();
		userCache.setId(1);
		userCache.setAge(10);
		userCache.setUsername("aaa");
		userCache.setPassword("sdfsdfsa");
        userService.addUserCache(userCache);  
        return "addUser";
	}
	
	@RequestMapping("/updateUserCache")  
    public String toIndex5(HttpServletRequest request, Model model){  
		int userId = Integer.parseInt(request.getParameter("id"));  
		String name = request.getParameter("name");
		UserCache userCache = new UserCache();
		userCache.setId(userId);
		userCache.setAge(10);
		userCache.setUsername(name);
		userCache.setPassword("sdfsdfsa");
        userService.updateUserCache(userCache);  
        return "updateUser";
	}
	
	@RequestMapping("/deleteAllUserCache")  
    public String toIndex6(HttpServletRequest request, Model model){  
        Collection<String> names = cacheManager.getCacheNames();
        if(null != names && 0 != names.size()) {
        	for(String name : names) {
        		System.out.println("redis cache \"" + name + "\"  clear ");
        		cacheManager.getCache(name).clear();
        	}
        }
        return "deleteAllUserCache";
	}


}
