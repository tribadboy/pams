package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.finance.PamsStock;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsStockDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_STOCK;

    public static final String COL_ALL = " symbol_code, symbol_type, symbol_name, status ";

    
    /**
     * 根据symbolCode和symbolType查询某一支股票
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
            + " symbol_code = #{symbolCode} "
            + " AND "
            + " symbol_type = #{symbolType} "
            + "")
    public PamsStock getPamsStockBySymbolCodeAndSymbolType(@Param("symbolCode") String symbolCode, 
    		@Param("symbolType") Integer symbolType);
    
    /**
     * 查询所有某个证券市场下所有有效的股票
     * @param symbolType
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " status = 0 "
            + " AND "
            + " symbol_type = #{symbolType} "
            + " ORDER BY "
            + " symbol_code "
            + "")
    public List<PamsStock> getValidPamsStocksBySymbolType(@Param("symbolType") Integer symbolType);
    
    
    /**
     * 批量添加或替换股票信息
     * @param stocksList
     * @return
     */
    @Insert(""
    		+ " <script> "
            + " REPLACE INTO "
            + TABLE
    		+ " ( " + COL_ALL + " ) "
            + " VALUES "
            + " <foreach collection=\"stocksList\" item=\"item\" separator=\",\" > "
            + " ( " 
            + " #{item.symbolCode}, "
            + " #{item.symbolType}, "
            + " #{item.symbolName}, "
            + " #{item.status} "
            + " ) "
			+ " </foreach> "
            + " </script> "
			+ "")
	public int replaceStocksList(@Param("stocksList") List<PamsStock> stocksList);
    
    /**
     * 使得某个证券市场下的所有股票变为无效状态
     * @param symbolType
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " status = 1 "
    		+ " WHERE "
    		+ " symbol_type = #{symbolType} "
    		+ "")
    public void setStocksInvalidBySymbolType(@Param("symbolType") Integer symbolType);
}
