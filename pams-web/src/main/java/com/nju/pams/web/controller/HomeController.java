package com.nju.pams.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nju.pams.util.ResultUtil;
import com.nju.pams.util.constant.ResultEnum;

import net.sf.json.JSONObject;


@Controller
@RequestMapping(value = "/web")
public class HomeController {
	
	/**
	 * 首页
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView("home");
		//返回 home.jsp
		return modelAndView;
	}
	
	//浏览器重定向到登录页面
    @RequestMapping(value = "/test")
    public String doLogout(HttpServletRequest request, Model model) {
        return "test";
    }
    
    //测试json返回值
    @ResponseBody
	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public String getDateCanRedeem(@RequestParam("a") final Integer a,
								   @RequestParam("b") final Integer b) {
		final JSONObject result = new JSONObject();

		if(null == a || null == b) {
			ResultUtil.addResult(result, ResultEnum.NullParameter);
			return result.toString();
		}
		result.put("hello", "你好");
		result.put("a", a);
		result.put("b", b);
		ResultUtil.addSuccess(result);
		return result.toString();
	}
    
	/**
	 * ajax测试页面
	 */
	@RequestMapping(value = "/ajax", method = RequestMethod.GET)
	public ModelAndView ajax() {
		ModelAndView modelAndView = new ModelAndView("ajax");
		//返回 ajax.jsp
		return modelAndView;
	}
	
	//测试json返回值
    @ResponseBody
	@RequestMapping(value = "/getAjaxJson1", method = RequestMethod.GET)
	public String getAjaxJson1(@RequestParam("a") final Integer a,
							   @RequestParam("b") final Integer b) {
		final JSONObject result = new JSONObject();
		if(null == a || null == b) {
			ResultUtil.addResult(result, ResultEnum.NullParameter);
			return result.toString();
		}
		result.put("a", a);
		result.put("b", b);
		ResultUtil.addSuccess(result);
		return result.toString();
	}
    
    //测试json返回值
    @ResponseBody
	@RequestMapping(value = "/getAjaxJson2", method = RequestMethod.GET)
	public String getAjaxJson2(@RequestParam("a") final String a,
							   @RequestParam("b") final String b) {
		final JSONObject result = new JSONObject();
		if(null == a || null == b) {
			ResultUtil.addResult(result, ResultEnum.NullParameter);
			return result.toString();
		}
		result.put("a", a);
		result.put("b", b);
		ResultUtil.addSuccess(result);
		return result.toString();
	}
}
