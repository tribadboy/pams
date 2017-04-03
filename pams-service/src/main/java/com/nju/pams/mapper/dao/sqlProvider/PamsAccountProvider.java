package com.nju.pams.mapper.dao.sqlProvider;

import java.util.Map;

import org.apache.log4j.Logger;

import com.nju.pams.mapper.dao.PamsAccountDAO;
import com.nju.pams.model.asset.ConsumptionCondition;

public class PamsAccountProvider {
	
	private static final Logger logger = Logger.getLogger(PamsAccountProvider.class);
	
	public String selectAccountByCondition(Map<String, ConsumptionCondition> map) {
		//构造多条件查询的sql语句
		StringBuffer strBuf = new StringBuffer(512);
		strBuf.append(" SELECT " + PamsAccountDAO.COL_ALL + " FROM "+ PamsAccountDAO.TABLE + " WHERE 1 = 1 ");
		
		//注解从map中获取传入的参数
		ConsumptionCondition condition = map.get("consumptionCondition");
		if(null == condition) {
			logger.info("多条件查询消费账目时，没有从参数中找到条件对象，默认查询全部账目");
			return strBuf.append(" ; ").toString();
		}
		if(condition.getUserId() != 0) {
			strBuf.append(" AND user_id = " + condition.getUserId());
		}
		if(condition.getConsumptionIds() != null && condition.getConsumptionIds().size() > 0) {
			StringBuffer listStr = new StringBuffer(64);
			listStr.append("(");
			for(Integer i : condition.getConsumptionIds()) {
				listStr.append(i).append(",");
			}
			listStr.deleteCharAt(listStr.length()-1);	//delete last ','  eg:  ' (1,2,3) '
			listStr.append(")");
			strBuf.append(" AND consumption_id in " + listStr);
		}
		if(condition.getStartTime() != null) {
			strBuf.append(" AND spend_time >= \"" + condition.getStartTime() + "\"");
		}
		if(condition.getEndTime() != null) {
			strBuf.append(" AND spend_time <= \"" + condition.getEndTime() + "\"");
		}
		if(condition.getMinCost() != null) {
			strBuf.append(" AND cost >= " + condition.getMinCost());
		}
		if(condition.getMaxCost() != null) {
			strBuf.append(" AND cost <= " + condition.getMaxCost());
		}
		strBuf.append(" ORDER BY user_id, spend_time, consumption_id, cost ASC ");
		strBuf.append(" ; ");
		logger.info("消费账目的多条件查询，sql语句为：" + strBuf.toString());
		return strBuf.toString();
	}
}
