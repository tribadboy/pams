package com.nju.pams.background.controller.bar.user;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nju.pams.biz.service.PamsLoginInfoService;
import com.nju.pams.biz.service.PamsUserService;
import com.nju.pams.model.PamsLoginInfo;
import com.nju.pams.model.PamsUser;
import com.nju.pams.model.PamsUserPhoto;
import com.nju.pams.model.constant.DataFileConstant;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.util.ResultUtil;
import com.nju.pams.util.constant.ResultEnum;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_USER_DATA)
public class UserDataController {  
    
    private static final Logger logger = Logger.getLogger(UserDataController.class);
    
    @Autowired
    PamsUserService pamsUserService;
    
    @Autowired
    PamsLoginInfoService pamsLoginInfoService;
   
    
    //返回用户搜索页面
    @RequestMapping(value = "searchUser")
    public String getSearchUserPage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
        return "authc/user-bar/searchUser";
    }
    
 
    /**
     * 自动发送的数据格式：
     *  1. start: 开始记录的起始数，如第 20 条,从0开始
     *  2. limit : 单页多少条记录
     *  3. pageIndex : 第几页，同start参数重复，可以选择其中一个使用
     *
     * 返回的数据格式：
     *  {
     *     "rows" : [{},{}], //数据集合
     *     "results" : 100, //记录总数
     *     "hasError" : false, //是否存在错误
     *     "error" : "" // 仅在 hasError : true 时使用
     *   }
     * 
     */
	@ResponseBody
	@RequestMapping(value = "searchUserDataInfo")
	public String searchStockDataInfo( HttpServletRequest request,
			@RequestParam(value = "nameKey", required = false) String nameKey,
   			@RequestParam("start") final Integer start,
   			@RequestParam("limit") final Integer limit
			) {
		final JSONObject result = new JSONObject();
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        result.put("rows", "[]");
   	        result.put("results", 0);
   	        result.put("hasError", true);
   	        result.put("error", "会话已断开，请重新登录");
   	        return result.toString();
    	}
    	
    	List<PamsUser> allList;
    	if(StringUtils.isEmpty(nameKey)) {
    		allList = pamsUserService.getPamsUserList();
    	} else {
    		try {
    			nameKey = URLDecoder.decode(nameKey, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				logger.info("模糊查询的关键词解码异常");
	    		SecurityUtils.getSubject().logout();
	   	        result.put("rows", "[]");
	   	        result.put("results", 0);
	   	        result.put("hasError", true);
	   	        result.put("error", "模糊查询的关键词解码异常");
	   	        return result.toString();
			}
    		allList = pamsUserService.getPamsUsersByKey(nameKey);
    	}
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<PamsUser> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	for(PamsUser user : resultList) {
    		JSONObject json = new JSONObject();
    		json.put("username", user.getUsername());
    		json.put("phone", user.getPhone());
    		json.put("mail", user.getMail());
    		json.put("status", PamsUser.Status.getMsgFromIndex(user.getStatus()));
    		array.add(json);
    	}
    	result.put("rows", array);
	    result.put("results", total);
	    result.put("hasError", false);
    	
    	return result.toString();
	}	
	
	 /**
     * 返回用户信息页面
     * @return
     */
    @RequestMapping(value = "getUserInfo")
    public String getUserInfoPage(HttpServletRequest request, Model model,
    		@RequestParam("findUsername") final String findUsername){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	PamsUser user = pamsUserService.getPamsUserByUsername(findUsername);
    	model.addAttribute("user", user);
    	model.addAttribute("registerTime", pamsLoginInfoService.getPamsRegisterTimeByUserId(user.getUserId()));
    	String jsonStr = getLoginInfo(user.getUserId());
    	JSONObject jsonResult = JSONObject.fromObject(jsonStr);
    	PamsUserPhoto userPhoto = pamsUserService.getPamsUserPhotoByUserId(user.getUserId());
    	if(null == userPhoto) {
    		model.addAttribute("photoName", "default.jpg");
    	} else {
    		model.addAttribute("photoName", userPhoto.getPhotoName());
    	}
    	model.addAttribute("loginInfoList", jsonResult.get("data"));
    	
        return "authc/user-bar/person-data";
    }
    
    /**
     * 获取用户最近的登录信息
     * @param username
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "getLoginInfo")
	public String getLoginInfo(@RequestParam("userId") Integer userId) {
		final JSONObject result = new JSONObject();
		if(null == userId) {
			ResultUtil.addResult(result, ResultEnum.NullParameter);
			return result.toString();
		} 
		String username = pamsUserService.getPamsUserByUserId(userId).getUsername();
		List<PamsLoginInfo> resultList = pamsLoginInfoService.getPamsLoginInfoListByUserId(userId);
		JSONArray array = new JSONArray();
		for(PamsLoginInfo loginInfo : resultList) {
			JSONObject obj = new JSONObject();
			obj.put("username", username);
			obj.put("ip", loginInfo.getIp());
			obj.put("loginTime", loginInfo.getLoginTime());
			array.add(obj);
		}
		result.put("data", array);
		ResultUtil.addSuccess(result);
		return result.toString();
	}
    
    /**
     * 获取用户头像的请求
     * 由于无法直接访问本地资源，需要通过web请求转换
     * @param photoName
     * @param request
     * @param response
     */
    @RequestMapping("getUserPhoto")  
    public void getUserPhoto(@RequestParam("photoName") String photoName,HttpServletRequest request,
    		HttpServletResponse response){  
        response.setContentType("application/octet-stream;charset=UTF-8");  
        try {  
           FileInputStream inputStream = new FileInputStream(DataFileConstant.USER_PHOTO + "/" + photoName);
           byte[]data = new byte[inputStream.available()];  
           inputStream.read(data);           
           inputStream.close(); 
           
           OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());  
           outputStream.write(data);  
           outputStream.flush();  
           outputStream.close(); 
       } catch (Exception e) {  
           e.printStackTrace();  
       }
    }  
	
}  