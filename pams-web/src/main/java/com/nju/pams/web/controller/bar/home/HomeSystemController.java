package com.nju.pams.web.controller.bar.home;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nju.pams.biz.service.PamsFinancialNewsService;
import com.nju.pams.model.asset.DepositRecord;
import com.nju.pams.model.asset.DepositTimeEnum;
import com.nju.pams.model.asset.LoanRecord;
import com.nju.pams.model.constant.DataFileConstant;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.model.system.FinancialNews;
import com.nju.pams.util.BigDecimalUtil;
import com.nju.pams.util.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_HOME_SYSTEM)
public class HomeSystemController {  
    
    private static final Logger logger = Logger.getLogger(HomeSystemController.class);
    
    @Autowired
    PamsFinancialNewsService pamsFinancialNewsService;
   
    //返回欢迎页面
    @RequestMapping(value = "welcome")
    public String getWelcomePage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
        return "authc/home-bar/welcome";
    }
    
    //返回通知与公告页面
    @RequestMapping(value = "notice")
    public String getNoticePage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
        return "authc/home-bar/notice";
    }
    
    //返回最新咨询页面
    @RequestMapping(value = "news")
    public String getNewsPage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
        return "authc/home-bar/news";
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
	@RequestMapping(value = "searchDepositRecordInfo")
	public String searchDepositRecordInfo( HttpServletRequest request,
   			@RequestParam("type") final Integer type,
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
    	
    	List<DepositRecord> allList = null;
    	if(type == 0) {
    		allList = pamsDepositService.getAllDepositRecordsByUserId(userId);
    	} else {
    		allList = pamsDepositService.getValidDepositRecordsByUserId(userId);
    	}
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<DepositRecord> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	for(DepositRecord record : resultList) {
    		JSONObject json = new JSONObject();
    		json.put("depositId", record.getDepositId());
    		json.put("depositName", record.getDepositName());
    		json.put("depositTimeName", DepositTimeEnum.getTimeFromIndex(record.getDepositTimeId()).getMsg());
    		json.put("currentProfitPercent", BigDecimalUtil.generateFormatNumber(record.getCurrentProfitPercent()));
    		json.put("fixedProfitPercent", BigDecimalUtil.generateFormatNumber(record.getFixedProfitPercent()));
    		json.put("currentAmount", pamsDepositService.computeDepositRecordValue(record.getDepositId(), 
    				LocalDate.now().toString(DateUtil.FormatString)));
    		json.put("statusName", DepositRecord.Status.getMsgFromInt(record.getStatus()));
    		json.put("message", record.getMessage());
    		array.add(json);
    	}
    	result.put("rows", array);
	    result.put("results", total);
	    result.put("hasError", false);
    	
    	return result.toString();
	}	
	
	//返回新闻内容详情页
    @RequestMapping(value = "newsTemplate")
    public String getNewsTemplatePage(HttpServletRequest request, Model model,
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
    	
        return "authc/home-bar/news-template";
    }
    
    /**
     * 获取用户头像的请求
     * 由于无法直接访问本地资源，需要通过web请求转换
     * @param photoName
     * @param request
     * @param response
     */
    @RequestMapping("getNewsPhoto")  
    public void getNewsPhoto(@RequestParam("pictureName") String pictureName,HttpServletRequest request,
    		HttpServletResponse response){  
    	if(pictureName == FinancialNews.NonePicture) {
    		logger.info("所查看的新闻页面没有图片资源");
    		return;
    	}
        response.setContentType("application/octet-stream;charset=UTF-8");  
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