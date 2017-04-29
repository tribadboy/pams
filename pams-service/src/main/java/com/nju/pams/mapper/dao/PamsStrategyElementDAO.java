package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.nju.pams.finance.StrategyElement;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsStrategyElementDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_STRATEGY_ELEMENT;

    public static final String COL_ALL = " element_id, strategy_id, symbol_code,"
    		+ " symbol_type, percent, create_time, update_time ";
 

    
    /**
     * 获取某个策略包涵的所有股票
     * @param strategyId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " strategy_id = #{strategyId} "
            + " ORDER BY "
            + " percent DESC "
            + "")
    public List<StrategyElement> getStrategyElementListByStrategyId(@Param("strategyId") Integer strategyId);
    
   
    /**
     * 插入策略中的股票记录，element_id create_time update_time由数据库操作
     * @param strategyElement
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{elementId}, "
    		+ " #{strategyId}, "
    		+ " #{symbolCode}, "
    		+ " #{symbolType}, "
    		+ " #{percent}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "element_id", keyProperty = "elementId")
    public void insertStrategyElement(StrategyElement strategyElement);
  
    
    /**
     * 删除某条策略包涵的所有股票
     * @param strategyId
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " strategy_id = #{strategyId} "
    		+ "")
    public void deleteStrategyElementByStrategyId(@Param("strategyId") Integer strategyId);
    
}
