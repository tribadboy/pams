package com.nju.pams.biz.finance.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.finance.service.PamsStockAPIService;
import com.nju.pams.finance.PamsStock;
import com.nju.pams.finance.StockHistory;
import com.nju.pams.model.constant.RedisCacheConstant;
import com.nju.pams.util.BigDecimalUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsStockAPIServiceImpl implements PamsStockAPIService {
	
	private static final Logger logger = Logger.getLogger(PamsStockAPIServiceImpl.class);
	
	/**
	 * 获取沪市600开头的股票信息
	 */
	@Override
	@Cacheable(value = RedisCacheConstant.CACHE_STOCK, key = RedisCacheConstant.CACHE_STOCK_KEY)
	public List<Map<String, Object>> getSpecialStockData() {
		List<Map<String, Object>> mapsList = new ArrayList<Map<String, Object>>(5000);
		for(int i = 600000; i < 604000; i += 1000) {
			String stockData = searchStockInfoBetweenStartCodeAndEndCodeUsingAPI(i, i+1000);
			if(null != stockData) {
				mapsList.addAll(getStocksFromStockInfo(stockData));
			}
		}
		return mapsList;
	}

	/**
	 * 通过api获取[startCode, endCode)的最新股票数据  沪市A股 
	 * 数据量应不应超过1000
	 * 该方法使用redis缓存，缓存时间为300s
	 * 缓存 name: cache_stock  ,   key: cache_stock_key_600000_601000
	 */
	@Override
	public String searchStockInfoBetweenStartCodeAndEndCodeUsingAPI(int startCode, int endCode) {
		StringBuffer strBuf = new StringBuffer();
		//针对沪市A股，代码前补0，且用“，”分隔
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
        		//result = httpMethod.getResponseBodyAsString();
        		BufferedReader reader = new BufferedReader(new InputStreamReader(httpMethod.getResponseBodyAsStream()));  
        		StringBuffer stringBuffer = new StringBuffer();  
        		String str = "";  
        		while((str = reader.readLine())!=null){  
        		    stringBuffer.append(str);  
        		}  
        		result = stringBuffer.toString();  
        		reader.close();
        	}
        } catch (IOException e) {
        	logger.error("获取沪市股票的最新信息失败");
        	e.printStackTrace();
        	return null;
        }
        return result;
	}


	/**
	 * 通过api获取某只股票在某一年的历史数据   沪市A股
	 */
	@Override
	public String searchStockHistoryInCertainYearUsingAPI(String code, int year) {
		String localUrl = "http://img1.money.126.net/data/hs/kline/day/history/" 
  				+ year + "/0" + code + ".json";	
  		String result = null;
        HttpClient client = new HttpClient();
        HttpMethod httpMethod = new GetMethod(localUrl);
        try {
        	int httpCode = client.executeMethod(httpMethod);
        	if (httpCode == 200) {
        		//result = httpMethod.getResponseBodyAsString();
        		BufferedReader reader = new BufferedReader(new InputStreamReader(httpMethod.getResponseBodyAsStream()));  
        		StringBuffer stringBuffer = new StringBuffer();  
        		String str = "";  
        		while((str = reader.readLine())!=null){  
        		    stringBuffer.append(str);  
        		}  
        		result = stringBuffer.toString();  
        		reader.close();
        	}
        } catch (IOException e) {
        	logger.error("获取股票的历史信息失败");
        	e.printStackTrace();
        	return null;
        }
        return result;
	}

	/**
	 * 将获取的字符串数据转换成股票对象集合
	 */
	@Override
	public List<Map<String, Object>> getStocksFromStockInfo(String result) {
		List<Map<String, Object>> stockList = new ArrayList<Map<String, Object>>();
        if(null == result) {
        	return stockList;
        }
        //去掉返回字符串中的回调标记
    	int start = result.indexOf("(");
    	int end = result.indexOf(")");
    	result = result.substring(start+1, end);
        JSONObject json = JSONObject.fromObject(result);
        if(null != json) {
        	for(Object key : json.keySet()) {
        		//从json解析所有可能用到的数据
        		JSONObject stockInfo = json.getJSONObject((String) key);
        		if(stockInfo.containsKey("symbol") && stockInfo.containsKey("name") && stockInfo.containsKey("status")
        				&& stockInfo.containsKey("time") && stockInfo.containsKey("volume") && stockInfo.containsKey("open")
        				&& stockInfo.containsKey("price") && stockInfo.containsKey("high") && stockInfo.containsKey("low")
        				&& stockInfo.containsKey("percent") && stockInfo.containsKey("arrow") && stockInfo.containsKey("updown")) {
        			HashMap<String, Object> map = new HashMap<String, Object>();
        			map.put("symbolCode", stockInfo.getString("symbol"));
        			map.put("symbolType", PamsStock.SymbolType.SH.getIndex());
        			map.put("symbolName", stockInfo.getString("name"));		
        			map.put("arrow", stockInfo.getString("arrow"));
        			//if(null != stockInfo.get("status") && stockInfo.getInt("status") == 0) {
        			if(Integer.valueOf(0).equals(stockInfo.get("status"))) {
        				map.put("status", PamsStock.Status.Valid.getIndex());
        			} else {
        				map.put("status", PamsStock.Status.Invalid.getIndex());
        			}
        			String time = stockInfo.getString("time").substring(0, 10).replace("/", "-");
        			map.put("time", time);
        			map.put("originTime", stockInfo.getString("time"));
        			map.put("open", BigDecimalUtil.generateFormatNumber(stockInfo.getDouble("open")));
        			map.put("price", BigDecimalUtil.generateFormatNumber(stockInfo.getDouble("price")));
        			map.put("high", BigDecimalUtil.generateFormatNumber(stockInfo.getDouble("high")));
        			map.put("low", BigDecimalUtil.generateFormatNumber(stockInfo.getDouble("low")));
        			map.put("updown", BigDecimalUtil.generateFormatNumber(stockInfo.getDouble("updown")));
        			map.put("volume", stockInfo.getInt("volume"));
        			map.put("percent", BigDecimalUtil.generateFormatNumber(stockInfo.getDouble("percent") * 100));
        			stockList.add(map);
        		}
        	}
        }
        return stockList;
	}

	/**
	 * 将获取的字符串数据转换成股票历史对象集合
	 */
	@Override
	public List<StockHistory> getStockHistorysFromStockHistoryInfo(String result) {
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
