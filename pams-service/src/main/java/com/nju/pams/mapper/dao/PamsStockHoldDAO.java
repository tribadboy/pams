package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.finance.StockHold;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsStockHoldDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_STOCK_HOLD;

    public static final String COL_ALL = " hold_id, user_id, symbol_code, symbol_type,"
    		+ " quantity, create_time, update_time ";

    
    /**
     * 根据holdId查询用户的某条持股信息
     * @param holdId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " hold_id = #{holdId} "
            + "")
    public StockHold getStockHoldByHoldId(@Param("holdId") Integer holdId);
    
    /**
     * 根据userId查询用户的所有持股列表
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
            + "")
    public List<StockHold> getStockHoldListByUserId(@Param("userId") Integer userId);
    
    /**
     * 根据userId和股票信息查询用户的持股
     * @param userId
     * @param symbolCode
     * @param symbolType
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " user_id = #{userId} "
            + " AND "
            + " symbol_code = #{symbolCode} "
            + " AND "
            + " symbol_type =#{symbolType} "
            + " LIMIT 1 "
            + "")
    public StockHold getStockHoldByUserIdAndStockInfo(@Param("userId") Integer userId,
    		@Param("symbolCode") String symbolCode, @Param("symbolType") Integer symbolType);
    
  
    /**
     * 插入用户持股数据，hold_id, create_time update_time由数据库操作
     * @param stockHold
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{holdId}, "
    		+ " #{userId}, "
    		+ " #{symbolCode}, "
    		+ " #{symbolType}, "
    		+ " #{quantity}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "hold_id", keyProperty = "holdId")
    public void insertStockHold(StockHold stockHold);
    
    /**
     * 更新用户持股数据，根据holdId
     * @param holdId
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " quantity = #{quantity} "
    		+ " WHERE "
    		+ " hold_id = #{holdId} "
    		+ "")
    public void updateStockHold(StockHold stockHold);
    
    /**
     * 删除某条持股数据
     * @param holdId
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " hold_id = #{holdId} "
    		+ "")
    public void deleteStockHoldByHoldId(@Param("holdId") Integer holdId);
    
 
}
