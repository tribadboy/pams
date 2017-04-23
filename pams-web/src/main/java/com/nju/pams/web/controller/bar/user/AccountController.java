package com.nju.pams.web.controller.bar.user;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nju.pams.biz.service.PamsLoginInfoService;
import com.nju.pams.biz.service.PamsUserService;
import com.nju.pams.model.PamsLoginInfo;
import com.nju.pams.model.PamsUser;
import com.nju.pams.model.PamsUserPhoto;
import com.nju.pams.model.constant.DataFileConstant;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.util.FileUtil;
import com.nju.pams.util.ResultUtil;
import com.nju.pams.util.constant.ResultEnum;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_USER_ACCOUNT)
public class AccountController {  
	
    private static final Logger logger = Logger.getLogger(AccountController.class);
    
    @Autowired
    PamsUserService pamsUserService;
    
    @Autowired
    PamsLoginInfoService pamsLoginInfoService;
    
    /**
     * 返回用户信息页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "person-data")
    public String getPersonDataPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	PamsUser user = pamsUserService.getPamsUserByUsername(username);
    	model.addAttribute("user", user);
    	model.addAttribute("registerTime", pamsLoginInfoService.getPamsRegisterTimeByUserId(userId));
    	String jsonStr = getLoginInfo(userId);
    	JSONObject jsonResult = JSONObject.fromObject(jsonStr);
    	PamsUserPhoto userPhoto = pamsUserService.getPamsUserPhotoByUserId(userId);
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
    
    //返回修改信息页面
    @RequestMapping(value = "change-info")
    public String getChangeInfoPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	PamsUser user = pamsUserService.getPamsUserByUsername(username);
    	PamsUserPhoto userPhoto = pamsUserService.getPamsUserPhotoByUserId(userId);
    	if(null == userPhoto) {
    		model.addAttribute("photoName", "default.jpg");
    	} else {
    		model.addAttribute("photoName", userPhoto.getPhotoName());
    	}
    	model.addAttribute("user", user);
        return "authc/user-bar/change-info";
    }
    
    /**
   	 * 修改个人信息
   	 * @param model
   	 * @param username
   	 * @param password
   	 * @param phone
   	 * @param email
   	 * @return
   	 */
   	@RequestMapping(value = "update", method = RequestMethod.POST)
   	public String updateUserInfo(Model model, HttpServletRequest request,
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
   	        return "authc/user-bar/change-info";
		} 
   		
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
   		//更信息用户信息
    	PamsUser pamsUser = pamsUserService.getPamsUserByUsername(username);
   		pamsUser.setPassword(password);
   		pamsUser.setPhone(phone);
   		pamsUser.setMail(email);
   		pamsUserService.updatePamsUser(pamsUser);
   		
   		PamsUser newUser = pamsUserService.getPamsUserByUsername(username);
   		PamsUserPhoto userPhoto = pamsUserService.getPamsUserPhotoByUserId(userId);
   		if(null == userPhoto) {
    		model.addAttribute("photoName", "default.jpg");
    	} else {
    		model.addAttribute("photoName", userPhoto.getPhotoName());
    	}
    	model.addAttribute("user", newUser);
        model.addAttribute("message", "修改信息成功");
        return "authc/user-bar/change-info";
   	}
   	
	/**
   	 * 用户图片上传
   	 * @param request
   	 * @return
   	 * @throws IOException
   	 */
   	@RequestMapping(value = "userPhotoUpload", method = RequestMethod.POST)
    @ResponseBody
    public synchronized String imgUpload(HttpServletRequest request){
   		final JSONObject result = new JSONObject();
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		ResultUtil.addResult(result, ResultEnum.SessionClose);
        	return result.toString();
    	}
    	
   		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
   		MultipartFile file = multipartRequest.getFile("file"); 	
   		if(!file.isEmpty()) {
   			//检查新上传的文件名长度
            String newName = userId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            if(newName.length() > PamsUserPhoto.MAX_LENGTH) {
            	ResultUtil.addResult(result, ResultEnum.PictureNameTooLong);
            	return result.toString();
            }
            //检查新上传的文件是否是图片格式的文件
            if(!newName.endsWith("png") && !newName.endsWith("jpg") && !newName.endsWith("jpeg")) {
            	ResultUtil.addResult(result, ResultEnum.PictureTypeError);
            	return result.toString();
            }
                  
            String realPath = DataFileConstant.USER_PHOTO;
            //放入新的用户图片
            try {
            	//由于照片名前面添加了时间戳和用户id，故照片名几乎不可能重复
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, newName));
			} catch (IOException e) {			
				e.printStackTrace();
				ResultUtil.addResult(result, ResultEnum.UploadPictureError);
	        	return result.toString();
			}
            
            //更新数据库中存储的信息
            PamsUserPhoto userPhoto = pamsUserService.getPamsUserPhotoByUserId(userId);
            if(null == userPhoto) {
            	pamsUserService.insertPamsUserPhoto(new PamsUserPhoto(userId, newName));
            } else {
            	String oldName = userPhoto.getPhotoName();
            	userPhoto.setPhotoName(newName);
            	pamsUserService.updatePamsUserPhoto(userPhoto);
            	//删除旧的用户照片
            	FileUtil.deleteFile(realPath + "/" + oldName);
            }            
            ResultUtil.addSuccess(result);
    		return result.toString();
        } else {
        	ResultUtil.addResult(result, ResultEnum.UploadPictureError);
        	return result.toString();
        } 		
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