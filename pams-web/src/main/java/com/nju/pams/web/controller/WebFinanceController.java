package com.nju.pams.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nju.pams.biz.finance.service.PamsStockHistoryService;
import com.nju.pams.biz.finance.service.PamsStockService;
import com.nju.pams.finance.PamsStock;
import com.nju.pams.finance.StockHistory;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.util.ResultUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_FINANCE)
public class WebFinanceController {  
	
	@Autowired
	PamsStockService pamsStockService;
	
	@Autowired
	PamsStockHistoryService pamsStockHistoryService;
    
    private static final Logger logger = Logger.getLogger(WebFinanceController.class);
    
    //TODO  本页面部分接口应限制管理员权限
    //    暂时股票数据仅考虑 沪市A股 600 601 603 开头的股票  时间为2013年至今
 
    /**
     * 重置所有股票基本信息和历史信息，需保证联网，同时需要保证在下午三点之前history 的api中不会出现当天数据 *********************************************
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "resetStockData", method = RequestMethod.GET)
	public String resetStockInfo() {
    	logger.info("股票最新信息与历史信息重置工作开始-----------------------------------------------");
		final JSONObject result = new JSONObject();	
		List<Map<String, Object>> mapsList = new LinkedList<Map<String, Object>>();
		//通过网易api获取股票信息
		for(int i = 600000; i < 604000; i += 1000) {
			String stockData = searchStockInfo(i, i+1000);
			if(null != stockData) {
				mapsList.addAll(getStocksFromStockInfo(stockData));
			}
		}
		
		if(CollectionUtils.isNotEmpty(mapsList)) {
			logger.info("从网易财经获取的最新股票数据成功，共获得股票数量：" + mapsList.size());
			List<PamsStock> stocksList = new ArrayList<PamsStock>(mapsList.size());
			for(Map<String, Object> map : mapsList) {
				stocksList.add(new PamsStock((String)map.get("symbolCode"), (int)map.get("symbolType"),
						(String)map.get("symbolName"), (int)map.get("status")));
			}
			
			//解析数据有效，将数据库中原本的所有股票信息设置为无效 
			pamsStockService.setStocksInvalidBySymbolType(PamsStock.SymbolType.SH.getIndex());
			//更新数据库中的所有的股票信息
			pamsStockService.replaceStocksList(stocksList);		

			//遍历所有有效的股票，将其历史信息更新
			int index = 0;
			for(PamsStock stock : stocksList) {
				//若该股票当前状态有效，从网易财经api获取其历史信息，并补充到数据库
				if(stock.getStatus() == PamsStock.Status.Valid.getIndex()) {
					int todayYear = Calendar.getInstance().get(Calendar.YEAR);
					for(int year = todayYear; year <= todayYear; year++) {
						String historyData = searchStockHistoryInfo(stock.getSymbolCode(), year);
						if(null == historyData) {
							//该股票在这一年没有数据，跳过
							continue;
						}
						List<StockHistory> stockHistoryList = getStockHistorysFromStockHistoryInfo(historyData);
						if(CollectionUtils.isNotEmpty(stockHistoryList)) {
							pamsStockHistoryService.insertIgnoreStockHistoryList(stockHistoryList);
						}
					}
				}
				logger.info("股票的历史数据补充已经完成：" + ++index);
			}
		}
		logger.info("股票最新数据与历史数据重置工作已完成------------------------------");
		ResultUtil.addSuccess(result);
		return result.toString();
	}
    
    
    /**
     * 搜索沪市股票代码在[startCode, endCode)区间内，最新的数据信息
     * @return
     */
    private String searchStockInfo(int startCode, int endCode){
  		StringBuffer strBuf = new StringBuffer();
  		for(int i = startCode; i < endCode; i++) {
  			strBuf.append("0").append(i).append(",");
  		}
  		String allList = strBuf.toString();
  		String localUrl = "http://api.money.126.net/data/feed/" + allList + ",money.api";	
  		String result = null;
        HttpClient client = new HttpClient();
        HttpMethod httpMethod = new GetMethod(localUrl);
        try {
        	int httpCode = client.executeMethod(httpMethod);
        	if (httpCode == 200) {
        		result = httpMethod.getResponseBodyAsString();
        	}
        } catch (IOException e) {
        	logger.error("获取沪市股票的最新信息失败");
        	e.printStackTrace();
        	return null;
        }
        return result;
  	}
    
    /**
     * 搜索某只股票的历史信息 沪市
     * @return
     */
    private String searchStockHistoryInfo(String id, int year){
  		String localUrl = "http://img1.money.126.net/data/hs/kline/day/history/" 
  				+ year + "/0" + id + ".json";	
  		String result = null;
        HttpClient client = new HttpClient();
        HttpMethod httpMethod = new GetMethod(localUrl);
        try {
        	int httpCode = client.executeMethod(httpMethod);
        	if (httpCode == 200) {
        		result = httpMethod.getResponseBodyAsString();
        	}
        } catch (IOException e) {
        	logger.error("获取股票的历史信息失败");
        	e.printStackTrace();
        	return null;
        }
        return result;
  	}
    
    /**
     * 将最新的股票数据转化成股票对象集合
     * @param result
     * @return
     */
    private List<Map<String, Object>> getStocksFromStockInfo(String result) {
        List<Map<String, Object>> stockList = new LinkedList<Map<String, Object>>();
        if(null == result) {
        	return stockList;
        }
    	int start = result.indexOf("(");
    	int end = result.indexOf(")");
    	result = result.substring(start+1, end);
        JSONObject json = JSONObject.fromObject(result);
        if(null != json) {
        	for(Object key : json.keySet()) {
        		JSONObject stockInfo = json.getJSONObject((String) key);
        		if(stockInfo.containsKey("symbol") && stockInfo.containsKey("name") && stockInfo.containsKey("status")
        				&& stockInfo.containsKey("time") && stockInfo.containsKey("volume") && stockInfo.containsKey("open")
        				&& stockInfo.containsKey("price") && stockInfo.containsKey("high") && stockInfo.containsKey("low")
        				&& stockInfo.containsKey("percent")) {
        			HashMap<String, Object> map = new HashMap<String, Object>();
        			map.put("symbolCode", stockInfo.getString("symbol"));
        			map.put("symbolType", PamsStock.SymbolType.SH.getIndex());
        			map.put("symbolName", stockInfo.getString("name"));		
        			if(stockInfo.getInt("status") == 0) {
        				map.put("status", PamsStock.Status.Valid.getIndex());
        			} else {
        				map.put("status", PamsStock.Status.Invalid.getIndex());
        			}
        			String time = stockInfo.getString("time").substring(0, 10).replace("/", "-");
        			map.put("time", time);
        			map.put("open", BigDecimal.valueOf(stockInfo.getDouble("open")));
        			map.put("price", BigDecimal.valueOf(stockInfo.getDouble("price")));
        			map.put("high", BigDecimal.valueOf(stockInfo.getDouble("high")));
        			map.put("low", BigDecimal.valueOf(stockInfo.getDouble("low")));
        			map.put("volume", stockInfo.getInt("volume"));
        			map.put("percent", BigDecimal.valueOf(stockInfo.getDouble("percent") * 100));
        			stockList.add(map);
        		}
        	}
        }
        return stockList;
    }
    
    
    /**
     * 将历史股票数据转化成股票历史对象集合
     * @param result
     * @return
     */
    private List<StockHistory> getStockHistorysFromStockHistoryInfo(String result) {
        List<StockHistory> resultList = new LinkedList<StockHistory>();
        if(null == result) {
        	return resultList;
        }
        JSONObject json = JSONObject.fromObject(result);
        if(null != json) {
        	JSONArray jsonArray = json.getJSONArray("data");
        	String symbolCode = json.getString("symbol");
        	int symbolType = PamsStock.SymbolType.SH.getIndex();
        	if(null != jsonArray) {
        		for(int i = 0; i < jsonArray.size(); i++) {
        			JSONArray data = jsonArray.getJSONArray(i);
        			String originDate = data.getString(0);
        			String symbolDate = originDate.substring(0, 4) + "-" + originDate.substring(4, 6) + "-" + originDate.substring(6);
        			BigDecimal open  = BigDecimal.valueOf(data.getDouble(1));
        			BigDecimal close = BigDecimal.valueOf(data.getDouble(2));
        			BigDecimal high = BigDecimal.valueOf(data.getDouble(3));
        			BigDecimal low = BigDecimal.valueOf(data.getDouble(4));
        			int volumn = data.getInt(5);
        			BigDecimal risePercent = BigDecimal.valueOf(data.getDouble(6));
        			StockHistory his = new StockHistory(symbolDate, symbolCode, symbolType,
        					open, close, high, low, risePercent, volumn);
        			resultList.add(his);
        		}
        	}
        }
        return resultList;
    }
    
  
}  