package com.nju.pams.web.controller;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nju.pams.cacheable.service.UserService;
import com.nju.pams.model.User;
import com.nju.pams.model.cacheable.UserCache;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/test")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired  
    private CacheManager cacheManager; 
	
	@ResponseBody
	@RequestMapping("/showUser")  
    public String toIndex(HttpServletRequest request, @RequestParam("userId") final Integer userId){  
		final JSONObject result = new JSONObject();

        User user = userService.getUserById(userId);  
        result.put("user", user);
        return result.toString();  
	}
	
	@ResponseBody
	@RequestMapping("/showUserCache")  
    public String toIndex2(HttpServletRequest request, @RequestParam("userId") final Integer userId){  
		final JSONObject result = new JSONObject();

        UserCache userCache = userService.getUserCacheById(userId);  
        result.put("user", userCache);
        return result.toString();
	}
	
	@ResponseBody
	@RequestMapping("/deleteUserCache")  
    public String toIndex3(HttpServletRequest request, @RequestParam("userId") final Integer userId){  

        userService.deleteUserCacheById(userId);  
        return "deleteUser";
	}
	
	@ResponseBody
	@RequestMapping("/addUserCache")  
    public String toIndex4(HttpServletRequest request){  
		UserCache userCache = new UserCache();
		userCache.setId(1);
		userCache.setAge(10);
		userCache.setUsername("aaa");
		userCache.setPassword("sdfsdfsa");
        userService.addUserCache(userCache);  
        return "addUser";
	}
	
	@ResponseBody
	@RequestMapping("/updateUserCache")  
    public String toIndex5(HttpServletRequest request, @RequestParam("userId") final Integer userId,
    		@RequestParam("name") final String name){  
		
		UserCache userCache = new UserCache();
		userCache.setId(userId);
		userCache.setAge(10);
		userCache.setUsername(name);
		userCache.setPassword("sdfsdfsa");
        userService.updateUserCache(userCache);  
        return "updateUser";
	}
	
	@ResponseBody
	@RequestMapping("/deleteAllUserCache")  
    public String toIndex6(HttpServletRequest request){  
        Collection<String> names = cacheManager.getCacheNames();
        if(null != names && 0 != names.size()) {
        	for(String name : names) {
        		cacheManager.getCache(name).clear();
        	}
        }
        return "deleteAllUserCache";
	}


}
