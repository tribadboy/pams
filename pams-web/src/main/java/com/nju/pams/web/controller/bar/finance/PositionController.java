package com.nju.pams.web.controller.bar.finance;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nju.pams.biz.finance.service.PamsStockCapitalService;
import com.nju.pams.biz.finance.service.PamsStockChangeService;
import com.nju.pams.biz.finance.service.PamsStockHistoryService;
import com.nju.pams.biz.finance.service.PamsStockHoldService;
import com.nju.pams.biz.finance.service.PamsStockService;
import com.nju.pams.finance.PamsStock;
import com.nju.pams.finance.StockChange;
import com.nju.pams.finance.StockHold;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.util.BigDecimalUtil;
import com.nju.pams.util.DateUtil;
import com.nju.pams.util.ResultUtil;
import com.nju.pams.util.constant.ResultEnum;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_FINANCE_POSITION)
public class PositionController {  
    
    private static final Logger logger = Logger.getLogger(PositionController.class);
    
    @Autowired
    PamsStockChangeService pamsStockChangeService;
    
    @Autowired
    PamsStockService pamsStockService;
    
    @Autowired
    PamsStockHoldService pamsStockHoldService;
    
    @Autowired
    PamsStockCapitalService pamsStockCapitalService;
    
    @Autowired
    PamsStockHistoryService pamsStockHistoryService;
   
    //返回持仓概览页面
    @RequestMapping(value = "getPositionOverall")
    public String getPositionOverallPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	BigDecimal investment = pamsStockChangeService.getTotalInvestmentByUserId(userId);
    	BigDecimal remain = pamsStockCapitalService.getStockCapitalAmountByUserId(userId);
    	BigDecimal stockValue = BigDecimal.ZERO;
    	List<StockHold> holdList = pamsStockHoldService.getStockHoldListByUserId(userId);
    	JSONArray array = new JSONArray();
    	if(CollectionUtils.isNotEmpty(holdList)) {
    		for(StockHold hold : holdList) {
    			JSONObject obj = new JSONObject();
    			String symbolCode = hold.getSymbolCode();
    			int symbolType = hold.getSymbolType();
    			
    			obj.put("symbolCode", symbolCode);
    			obj.put("symbolTypeName", PamsStock.SymbolType.getMsgFromIndex(symbolType));
    			PamsStock stock = pamsStockService.getPamsStockBySymbolCodeAndSymbolType(symbolCode, symbolType);
    			obj.put("symbolName", stock.getSymbolName());
    			obj.put("quantity", hold.getQuantity());
    			
    			String maxDate = pamsStockHistoryService.getMaxDateByPK(symbolCode, symbolType);
    			if(StringUtils.isEmpty(maxDate)) {
    				obj.put("time", "");
        			obj.put("price", "");
        			obj.put("value", "");
    			} else {
    				obj.put("time", "" + maxDate);
    				BigDecimal price = pamsStockHistoryService.getStockHistoryByPK(maxDate, symbolCode, symbolType).getClose();
    				obj.put("price", BigDecimalUtil.generateFormatString(price));
    				BigDecimal value = price.multiply(BigDecimal.valueOf(hold.getQuantity()));
    				stockValue = stockValue.add(value);
    				obj.put("value", BigDecimalUtil.generateFormatString(value));
    			}
    			array.add(obj);
    		}
    	}
    	
    	model.addAttribute("investment", BigDecimalUtil.generateFormatString(investment));
    	model.addAttribute("remain", BigDecimalUtil.generateFormatString(remain));
    	model.addAttribute("stockValue", BigDecimalUtil.generateFormatString(stockValue));
    	model.addAttribute("dataList", array);
    	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
        return "authc/finance-bar/positionOverall";
    }
   
    
    //返回交易记录页面
    @RequestMapping(value = "getTransactionRecord")
    public String getTransactionRecordPage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	
        return "authc/finance-bar/transactionRecord";
    }
    
    //返回创建交易记录数据页面
    @RequestMapping(value = "makeTransaction")
    public String makeTransaction(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
        return "authc/finance-bar/makeTransaction";
    }
    
    //返回创建交易记录页面
    @RequestMapping(value = "makeTransaction2")
    public String makeTransaction2(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
        return "authc/finance-bar/makeTransaction2";
    }
    
    //添加转入与转出记录，然后自动返回添加页面
  	@RequestMapping(value = "addInflowAndOutflow", method = RequestMethod.POST)
     	public String addInflowAndOutflow(Model model, HttpServletRequest request,
     			@RequestParam("changeTypeId") final Integer changeTypeId,
     			@RequestParam("tradeTime") final String tradeTime,
     			@RequestParam("total") final Double total
     			) {

  		String username = (String) request.getSession().getAttribute("username");
      	Integer userId = (Integer) request.getSession().getAttribute("userId");
      	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
      	if(null == username || null == userId) {
      		logger.info("session失效，需要用户重新登录");
      		SecurityUtils.getSubject().logout();
     	        return "error/logout";
      	}
      	
      	
      	StockChange change = new StockChange(userId, changeTypeId, BigDecimalUtil.generateFormatNumber(total), tradeTime);
      	if(pamsStockChangeService.insertStockChange(change)) {
      		model.addAttribute("msg", "添加转入转出记录成功");
      	} else {
      		model.addAttribute("msg", "添加转入转出记录失败，请检查交易时间是否最新，且当前资金是否满足转出额度");
      	}
          	
        return "authc/finance-bar/makeTransaction";
  	}
  	
   //添加买入与卖出记录，然后自动返回添加页面
  	@RequestMapping(value = "addBuyAndSell", method = RequestMethod.POST)
    public String addBuyAndSell(Model model, HttpServletRequest request,
     			@RequestParam("changeTypeId") final Integer changeTypeId,
     			@RequestParam("symbolCode") final String symbolCode,
     			@RequestParam("symbolType") final Integer symbolType,
     			@RequestParam("price") final Double price,
     			@RequestParam("quantity") final Integer quantity,
     			@RequestParam("tradeTime") final String tradeTime
     			) {

  		String username = (String) request.getSession().getAttribute("username");
      	Integer userId = (Integer) request.getSession().getAttribute("userId");
      	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
      	if(null == username || null == userId) {
      		logger.info("session失效，需要用户重新登录");
      		SecurityUtils.getSubject().logout();
     	        return "error/logout";
      	}
      	
      	PamsStock stock = pamsStockService.getPamsStockBySymbolCodeAndSymbolType(symbolCode, symbolType);    	
      	if(changeTypeId == StockChange.ChangeType.Purchase.toIntValue()) {
      		//买入股票
      		//首先检查股票当前是否有效   		
      		if(null == stock || stock.getStatus() == PamsStock.Status.Invalid.getIndex()) {
      			model.addAttribute("msg", "买入股票失败，输入的股票代码错误或该股票已经无效");
      			return "authc/finance-bar/makeTransaction2";
      		} 
      		//检查时间是否合理
      		if(!pamsStockChangeService.stockTimeVerification(userId, tradeTime)) {
      			model.addAttribute("msg", "买入股票失败,买入时间不是最新的时间");
      			return "authc/finance-bar/makeTransaction2";
      		}
      		//检查股数是否复合要求
      		if(!pamsStockChangeService.stockQuantityVerification(userId, changeTypeId, symbolCode, symbolType, quantity)) {
      			model.addAttribute("msg", "买入股票失败,买入股数必须100的整数倍");
      			return "authc/finance-bar/makeTransaction2";
      		}     		
      		
      		//计算手续费和总花费
      		BigDecimal t_price = BigDecimalUtil.generateFormatNumber(price);
      		BigDecimal fee = pamsStockChangeService.getFeeForStock(changeTypeId, t_price, quantity);
      		BigDecimal total = pamsStockChangeService.getTotalForStock(changeTypeId, t_price, quantity);
      		StockChange change = new StockChange(userId, changeTypeId, symbolCode, symbolType, 
      	    		t_price, quantity, fee, total, tradeTime);
      		//检查资金是否充足
      		if(!pamsStockChangeService.stockCaptialVerification(userId, total)) {
      			model.addAttribute("msg", "买入股票失败,购买资金不足，总共需要资金：" + BigDecimalUtil.generateFormatString(total));
      			return "authc/finance-bar/makeTransaction2";
      		}
      		//通过所有检查，添加change记录
      		if(pamsStockChangeService.insertStockChange(change)) {
      			model.addAttribute("msg", "买入股票成功！");
      			return "authc/finance-bar/makeTransaction2";
      		} else {
      			model.addAttribute("msg", "买入股票失败,请检查操作");
      			return "authc/finance-bar/makeTransaction2";
      		}  		
      	} else if(changeTypeId == StockChange.ChangeType.Sell.toIntValue()) {
      		//卖出股票
      		//检查时间是否合理
      		if(!pamsStockChangeService.stockTimeVerification(userId, tradeTime)) {
      			model.addAttribute("msg", "卖出股票失败,卖出时间不是最新的时间");
      			return "authc/finance-bar/makeTransaction2";
      		}
      		
      		//检查股数是否复合要求
      		if(!pamsStockChangeService.stockQuantityVerification(userId, changeTypeId, symbolCode, symbolType, quantity)) {
      			model.addAttribute("msg", "卖出股票失败,持有的股数不足以卖出指定数额");
      			return "authc/finance-bar/makeTransaction2";
      		}    
      		
      		//计算手续费和总花费
      		BigDecimal t_price = BigDecimalUtil.generateFormatNumber(price);
      		BigDecimal fee = pamsStockChangeService.getFeeForStock(changeTypeId, t_price, quantity);
      		BigDecimal total = pamsStockChangeService.getTotalForStock(changeTypeId, t_price, quantity);
      		StockChange change = new StockChange(userId, changeTypeId, symbolCode, symbolType, 
      	    		t_price, quantity, fee, total, tradeTime);
      		
      		//通过所有检查，添加change记录
      		if(pamsStockChangeService.insertStockChange(change)) {
      			model.addAttribute("msg", "卖出股票成功！");
      			return "authc/finance-bar/makeTransaction2";
      		} else {
      			model.addAttribute("msg", "卖出股票失败,请检查操作");
      			return "authc/finance-bar/makeTransaction2";
      		}  		
      	}
          	
        return "authc/finance-bar/makeTransaction2";
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
	@RequestMapping(value = "searchTransactionRecordInfo")
	public String searchTransactionRecordInfo( HttpServletRequest request,
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
    	
    	List<StockChange> allList = pamsStockChangeService.getStockChangeListByUserId(userId);
    	Collections.reverse(allList);
    	
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<StockChange> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	boolean flag = true;
    	for(StockChange change : resultList) {
    		JSONObject json = new JSONObject();
    		if(flag) {
    			json.put("flag", true);
    			flag = false;
    		} else {
    			json.put("flag", false);
    		}
    		json.put("changeId", change.getChangeId());
    		json.put("tradeTime", change.getTradeTime());
    		json.put("changeTypeName", StockChange.ChangeType.getMsgFromIndex(change.getChangeTypeId()));
    		String symbolCode = change.getSymbolCode();
    		json.put("symbolCode", symbolCode);
    		if(StringUtils.isEmpty(symbolCode)) {
    			json.put("symbolTypeName", "");
    			json.put("symbolName", "");
    		} else {
    			json.put("symbolTypeName", PamsStock.SymbolType.getMsgFromIndex(change.getSymbolType()));
    			PamsStock stock = pamsStockService.getPamsStockBySymbolCodeAndSymbolType(symbolCode, 
    					change.getSymbolType());
    			if(null != stock) {
    				json.put("symbolName", stock.getSymbolName());
    			} else {
    				json.put("symbolName", "");
    			}	
    		}
    		
    		json.put("price", change.getPrice());
    		json.put("quantity", change.getQuantity());
    		json.put("fee", change.getFee());
    		json.put("total", BigDecimalUtil.generateFormatString(change.getTotal()));
    		array.add(json);
    	}
    	result.put("rows", array);
	    result.put("results", total);
	    result.put("hasError", false);
    	
    	return result.toString();
	}	
	
	//撤销某条交易记录
    @ResponseBody
	@RequestMapping(value = "deleteStockChangeInfo", method = RequestMethod.POST)
	public String deleteStockChangeInfo(HttpServletRequest request,
			@RequestParam(value="changeId") final Integer changeId
			) {
    	
		final JSONObject result = new JSONObject();	
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		ResultUtil.addResult(result, ResultEnum.SessionClose);
			return result.toString();
    	}
    	
    	if(!pamsStockChangeService.cancelStockChange(changeId)) {
    		ResultUtil.addResult(result, ResultEnum.DeleteStockChangeError);
			return result.toString();
    	}
    	
		ResultUtil.addSuccess(result);
		return result.toString();
	}
	
}  