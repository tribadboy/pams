package com.nju.pams.mapper.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.finance.StockCapital;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsStockCapitalDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_STOCK_CAPITAL;

    public static final String COL_ALL = " user_id, amount, create_time, update_time ";
    
    /**
     * 根据userId查询用户股票资金
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
    public StockCapital getStockCapitalByUserId(@Param("userId") Integer userId);
    
  
    /**
     * 插入用户资金记录，create_time update_time由数据库操作
     * @param stockCapital
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{userId}, "
    		+ " #{amount}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "user_id", keyProperty = "userId")
    public void insertStockCapital(StockCapital stockCapital);
    
    /**
     * 更新用户资金数据，根据userId
     * @param stockCapital
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " amount = #{amount} "
    		+ " WHERE "
    		+ " user_id = #{userId} "
    		+ "")
    public void updateStockCapital(StockCapital stockCapital);
    
}
