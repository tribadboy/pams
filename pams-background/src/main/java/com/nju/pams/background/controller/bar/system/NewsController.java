package com.nju.pams.background.controller.bar.system;


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

import com.nju.pams.biz.service.PamsFinancialNewsService;
import com.nju.pams.model.PamsUserPhoto;
import com.nju.pams.model.constant.DataFileConstant;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.model.system.FinancialNews;
import com.nju.pams.util.DateUtil;
import com.nju.pams.util.FileUtil;
import com.nju.pams.util.ResultUtil;
import com.nju.pams.util.constant.ResultEnum;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_SYSTEM_NEWS)
public class NewsController {  
	
    private static final Logger logger = Logger.getLogger(NewsController.class);
    
    @Autowired
	PamsFinancialNewsService pamsFinancialNewsService;
    
    //返回创建新闻页面
    @RequestMapping(value = "makeNews")
    public String getMakeNewsPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
        return "authc/system-bar/makeNews";
    }
    
    //返回新闻查询页面
    @RequestMapping(value = "checkNews")
    public String getCheckNewsPage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}

        return "authc/system-bar/checkNews";
    }
    
    //添加新闻记录，然后自动返回添加页面
	@RequestMapping(value = "add", method = RequestMethod.POST)
   	public String addFinancialNews(Model model, HttpServletRequest request,
   			@RequestParam("title") final String title,
   			@RequestParam("origin") final String origin,
   			@RequestParam("recordDate") final String recordDate,
   			@RequestParam("content") final String content
   			) {
		model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	FinancialNews news = new FinancialNews(title, origin, recordDate, content);
    	pamsFinancialNewsService.insertFinancialNews(news);
    	model.addAttribute("msg", "添加新闻资讯成功");
    	
        return "authc/system-bar/makeNews";
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
	@RequestMapping(value = "searchFinancialNewsInfo")
	public String searchFinancialNewsInfo( HttpServletRequest request,
   			@RequestParam("startDate") final String startDate,
   			@RequestParam("endDate") final String endDate,
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
    	
    	List<FinancialNews> allList = pamsFinancialNewsService.getFinancialNewsListInPeriod(startDate, endDate);
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<FinancialNews> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	for(FinancialNews news : resultList) {
    		JSONObject json = new JSONObject();
    		json.put("newsId", news.getNewsId());
    		json.put("title", news.getTitle());
    		json.put("origin", news.getOrigin());
    		json.put("recordDate", news.getRecordDate());
    		array.add(json);
    	}
    	result.put("rows", array);
	    result.put("results", total);
	    result.put("hasError", false);
    	
    	return result.toString();
	}	
	
	//删除新闻记录
    @ResponseBody
	@RequestMapping(value = "deleteNewsInfo", method = RequestMethod.POST)
	public String deleteNewsInfo(HttpServletRequest request,
			@RequestParam(value="newsId") final Integer newsId
			) {
    	
		final JSONObject result = new JSONObject();	
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		ResultUtil.addResult(result, ResultEnum.SessionClose);
			return result.toString();
    	}
    	
    	FinancialNews news = pamsFinancialNewsService.getFinancialNewsByNewsId(newsId);
    	String pictureName = news.getPictureName();
    	pamsFinancialNewsService.deleteFinancialNewsByNewsId(newsId);
    	
    	//若新闻存在图片，则同时删除图片
    	if(!FinancialNews.NonePicture.equals(pictureName)) {
    		FileUtil.deleteFile(DataFileConstant.NEWS_PICTURE + "/" + pictureName);
    	}
		ResultUtil.addSuccess(result);
		return result.toString();
	}
    
   //返回新闻内容详情页
    @RequestMapping(value = "getNewsInfo")
    public String getNewsInfoPage(HttpServletRequest request, Model model,
    		@RequestParam(value = "newsId") final Integer newsId){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    
    	FinancialNews news = pamsFinancialNewsService.getFinancialNewsByNewsId(newsId);
    	model.addAttribute("news", news);
    	
        return "authc/system-bar/news-template";
    }
    
    /**
   	 * 用户图片上传
   	 * @param request
   	 * @return
   	 * @throws IOException
   	 */
   	@RequestMapping(value = "newsPictureUpload", method = RequestMethod.POST)
    @ResponseBody
    public synchronized String imgUpload(HttpServletRequest request,
    		@RequestParam("newsId") Integer newsId){
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
                  
            String realPath = DataFileConstant.NEWS_PICTURE;
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
            FinancialNews news = pamsFinancialNewsService.getFinancialNewsByNewsId(newsId);
            if(null == news) {
            	ResultUtil.addResult(result, ResultEnum.UploadPictureError);
	        	return result.toString();
            } else {
            	String oldName = news.getPictureName();
            	pamsFinancialNewsService.setPictureNameByNewsId(newName, newsId);
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
     * 由于无法直接访问本地资源，需要通过web请求转换
     * @param pictureName
     * @param request
     * @param response
     */
    @RequestMapping("getNewsPicture")  
    public void getNewsPicture(@RequestParam("pictureName") String pictureName,HttpServletRequest request,
    		HttpServletResponse response){  
        response.setContentType("application/octet-stream;charset=UTF-8");  
        if(FinancialNews.NonePicture.equals(pictureName)) {
        	pictureName = "none.png";
        }
        try {  
           FileInputStream inputStream = new FileInputStream(DataFileConstant.NEWS_PICTURE + "/" + pictureName);
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