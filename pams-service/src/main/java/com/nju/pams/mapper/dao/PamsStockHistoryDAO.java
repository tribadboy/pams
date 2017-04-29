package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.nju.pams.finance.StockHistory;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsStockHistoryDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_STOCK_HISTORY;

    public static final String COL_ALL = " symbol_date, symbol_code, symbol_type, open,"
    		+ " close, high, low, rise_percent, volume ";

    
    /**
     * 根据symbolDate,symbolCode和symbolType查询某一支股票的某一天的历史信息
     * @param symbolDate
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
            + " symbol_date = #{symbolDate} "
            + " AND "
            + " symbol_code = #{symbolCode} "
            + " AND "
            + " symbol_type = #{symbolType} "
            + "")
    public StockHistory getStockHittoryByPK(@Param("symbolDate") String symbolDate, 
    		@Param("symbolCode") String symbolCode, @Param("symbolType") Integer symbolType);
    
    /**
     * 根据symbolCode和symbolType查询某一支股票的阶段历史信息 [startDate,endDate)
     * @param symbolCode
     * @param symbolType
     * @param startDate
     * @param endDate
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
            + " AND "
            + " symbol_date >= #{startDate} "
            + " AND "
            + " symbol_date < #{endDate} "
            + " ORDER BY "
            + " symbol_date ASC "
            + "")
    public List<StockHistory> getPeriodStockHittoryByPK(@Param("symbolCode") String symbolCode, 
    		@Param("symbolType") Integer symbolType, @Param("startDate") String startDate,
    		@Param("endDate") String endDate);
    
    @Select(""
            + " SELECT "
            + " MIN(symbol_date) "
            + " FROM "
            + TABLE
            + " WHERE "
            + " symbol_code = #{symbolCode} "
            + " AND "
            + " symbol_type = #{symbolType} "
            + " AND "
            + " symbol_date >= #{targetDate} "
            + "")
    public String getCellDateByPK(@Param("symbolCode") String symbolCode, 
    		@Param("symbolType") Integer symbolType, @Param("targetDate") String targetDate);
    
    @Select(""
            + " SELECT "
            + " MAX(symbol_date) "
            + " FROM "
            + TABLE
            + " WHERE "
            + " symbol_code = #{symbolCode} "
            + " AND "
            + " symbol_type = #{symbolType} "
            + " AND "
            + " symbol_date <= #{targetDate} "
            + "")
    public String getFloorDateByPK(@Param("symbolCode") String symbolCode, 
    		@Param("symbolType") Integer symbolType, @Param("targetDate") String targetDate);
    
    
    /**
     * 获取某只股票的全部历史数据
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
            + " ORDER BY "
            + " symbol_date ASC "
            + "")
    public List<StockHistory> getAllStockHittoryByPK(@Param("symbolCode") String symbolCode, 
    		@Param("symbolType") Integer symbolType);
    
    /**
     * 获取某只股票历史数据中的最大日期
     * @param symbolCode
     * @param symbolType
     * @return
     */
    @Select(""
            + " SELECT "
            + " MAX(symbol_date) "
            + " FROM "
            + TABLE
            + " WHERE "
            + " symbol_code = #{symbolCode} "
            + " AND "
            + " symbol_type = #{symbolType} "
            + "")
    public String getMaxDateByPK(@Param("symbolCode") String symbolCode, @Param("symbolType") Integer symbolType);
    
    /**
     * 获取某只股票历史数据中的最小日期
     * @param symbolCode
     * @param symbolType
     * @return
     */
    @Select(""
            + " SELECT "
            + " MIN(symbol_date) "
            + " FROM "
            + TABLE
            + " WHERE "
            + " symbol_code = #{symbolCode} "
            + " AND "
            + " symbol_type = #{symbolType} "
            + "")
    public String getMinDateByPK(@Param("symbolCode") String symbolCode, @Param("symbolType") Integer symbolType);
    
    /**
     * 获取历史数据的最大时间
     * @return
     */
    @Select(""
            + " SELECT "
            + " MAX(symbol_date) "
            + " FROM "
            + TABLE
            + "")
    public String getMaxDate();
    
    /**
     * 批量添加股票历史数据
     * @param stockHistoryList
     * @return
     */
    @Insert(""
    		+ " <script> "
            + " INSERT IGNORE INTO "
            + TABLE
    		+ " ( " + COL_ALL + " ) "
            + " VALUES "
            + " <foreach collection=\"stockHistoryList\" item=\"item\" separator=\",\" > "
            + " ( " 
            + " #{item.symbolDate}, "
            + " #{item.symbolCode}, "
            + " #{item.symbolType}, "
            + " #{item.open}, "
            + " #{item.close}, "
            + " #{item.high}, "
            + " #{item.low}, "
            + " #{item.risePercent}, "
            + " #{item.volume} "
            + " ) "
			+ " </foreach> "
            + " </script> "
			+ "")
	public int insertIgnoreStockHistoryList(@Param("stockHistoryList") List<StockHistory> stockHistoryList);
    
 
}
