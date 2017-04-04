package com.nju.pams.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nju.pams.biz.service.PamsUserService;
import com.nju.pams.model.PamsUser;
import com.nju.pams.model.constant.ExcelEnum;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.util.ExcelUtil; 
  
@Controller  
@RequestMapping(PathConstant.WEB_DOWNLOAD)
public class WebDownloadController {  
	
    @Autowired
    PamsUserService pamsUserService;
	
    private static final Logger logger = Logger.getLogger(WebDownloadController.class);
    
	@RequestMapping("excel")
	public String downLoadExcel(@RequestParam final Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		
		//检查session中是否存在username，不存在返回登录页面
		String username = (String) request.getSession().getAttribute("username");
		if(null == username) {
			logger.info("没有在session中找到用户，返回登录页面");
			return "anon/login";
		}
		
		//根据file模版名称进行下载
		String file = params.get("fileModel");  //获取下载的file模版名称
		ExcelEnum excelType = ExcelEnum.getExcelEnumFromModel(file);
		List<Integer> noTabList = Lists.newArrayList();
		Map<Integer, List<Integer>> columnMap = Maps.newHashMap();
		
		switch(excelType) {
			case TestFile:
				downloadTestFile(excelType.getModel(), noTabList, columnMap, request, response);
				break;
			case Unknown:
				break;
		}
		//返回null，否则在jsp请求该接口时会造成outputstream重复调用异常
		return null;
	}
	
	// 下载持仓数据,添加propertyId版本
    private void downloadTestFile(String fileModel,List<Integer> noTabList,  Map<Integer, List<Integer>> columnMap,
    		HttpServletRequest request, HttpServletResponse response) {

				Map<String, Object> dataMap = new HashMap<String, Object>();
				PamsUser user = pamsUserService.getPamsUserByUsername((String) request.getSession().getAttribute("username"));
				dataMap.put("username", user.getUsername());
				dataMap.put("password", user.getPassword());
				dataMap.put("phone", user.getPhone());
				dataMap.put("mail", user.getMail());
				columnMap.put(0 , Lists.<Integer>newArrayList(0, 1));
				
				try {
					ExcelUtil.generateExcelFile(fileModel, dataMap, noTabList, columnMap, request,response);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
}  