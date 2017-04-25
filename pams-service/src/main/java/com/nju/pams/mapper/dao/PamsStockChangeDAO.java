package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.nju.pams.finance.StockChange;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsStockChangeDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_STOCK_CHANGE;

    public static final String COL_ALL = " change_id, user_id, change_type_id, symbol_code,"
    		+ " symbol_type, price, quantity, fee, total, trade_time, create_time, update_time ";
    
    /**
     * 根据changeId查询用户股票变更记录
     * @param changeId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " change_id = #{changeId} "
            + "")
    public StockChange getStockChangeByChangeId(@Param("changeId") Integer changeId);
    
    /**
     * 获取用户的最新一次变更时间
     * @param userId
     * @return
     */
    @Select(""
            + " SELECT "
            + " MAX(trade_time) "
            + " FROM "
            + TABLE
            + " WHERE "
            + " user_id = #{userId} "
            + "")
    public String getMaxTradeTimeByUserId(@Param("userId") Integer userId);
    
    /**
     * 根据userId查询该用户的所有股票变更记录，并按照变更时间排序
     * @param userId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " user_id = #{userId} "
            + " ORDER BY "
            + " trade_time ASC "
            + "")
    public List<StockChange> getStockChangeListByUserId(@Param("userId") Integer userId);
    
   
    /**
     * 插入股票变更变更记录，change_id create_time update_time由数据库操作
     * @param stockChange
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{changeId}, "
    		+ " #{userId}, "
    		+ " #{changeTypeId}, "
    		+ " #{symbolCode}, "
    		+ " #{symbolType}, "
    		+ " #{price}, "
    		+ " #{quantity}, "
    		+ " #{fee}, "
    		+ " #{total}, "
    		+ " #{tradeTime}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "change_id", keyProperty = "changeId")
    public void insertStockChange(StockChange stockChange);
  
    
    /**
     * 删除股票变更记录
     * @param changeId
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " change_id = #{changeId} "
    		+ "")
    public void deleteStockChangeByChangeId(@Param("changeId") Integer changeId);
    
}
