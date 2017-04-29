package com.nju.pams.mapper.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.finance.PamsStrategy;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsStrategyDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_STRATEGY;

    public static final String COL_ALL = " strategy_id, strategy_name, user_id, username, status, strategy_type,"
    		+ " start_date, end_date1, end_date2, avg_profit, message, create_time, update_time ";
    
    /**
     * 根据strategyId查询策略
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
            + "")
    public PamsStrategy getPamsStrategyByStrategyId(@Param("strategyId") Integer strategyId);
    
    /**
     * 获取所有策略
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " ORDER BY "
            + " start_date ASC "
            + "")
    public List<PamsStrategy> getPamsStrategyList();
    
    
    /**
     * 根据userId查询该用户的所有策略
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
            + " start_date ASC "
            + "")
    public List<PamsStrategy> getPamsStrategyListByUserId(@Param("userId") Integer userId);
    
    /**
     * 查询某个用户在某个类别下的所有策略
     * @param userId
     * @param strategyType
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
            + " strategy_type = #{strategyType} "
            + " ORDER BY "
            + " start_date ASC "
            + "")
    public List<PamsStrategy> getPamsStrategyListByUserIdAndStrategyType(@Param("userId") Integer userId,
    		@Param("strategyType") Integer strategyType);
    
    /**
     * 查询某个类别下的所有策略
     * @param strategyType
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " strategy_type = #{strategyType} "
            + " ORDER BY "
            + " start_date ASC "
            + "")
    public List<PamsStrategy> getPamsStrategyListByStrategyType(@Param("strategyType") Integer strategyType);
    
    /**
     * 查询某个状态下的所有策略
     * @param status
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " status = #{status} "
            + " ORDER BY "
            + " start_date ASC "
            + "")
    public List<PamsStrategy> getPamsStrategyListByStatus(@Param("status") Integer status);
    
    /**
     * 获取某个类别某个状态下的所有策略
     * @param status
     * @param strategyType
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " status = #{status} "
            + " AND "
            + " strategy_type = #{strategyType} "
            + " ORDER BY "
            + " start_date ASC "
            + "")
    public List<PamsStrategy> getPamsStrategyListByStatusAndStrategyType(@Param("status") Integer status,
    		@Param("strategyType") Integer strategyType);
    
    /**
     * 获取某个用户某个类别下的某个状态的全部策略
     * @param status
     * @param strategyType
     * @param userId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " status = #{status} "
            + " AND "
            + " strategy_type = #{strategyType} "
            + " AND "
            + " user_id = #{userId} "
            + " ORDER BY "
            + " start_date ASC "
            + "")
    public List<PamsStrategy> getPamsStrategyListByStatusAndStrategyTypeAndUserId(@Param("status") Integer status,
    		@Param("strategyType") Integer strategyType, @Param("userId") Integer userId);
    
    /**
     * 模糊查询某个类别下的所有策略
     * @param key
     * @param strategyType
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " username LIKE #{key} "
            + " AND "
            + " strategy_type = #{strategyType} "
            + " ORDER BY "
            + " start_date ASC "
            + "")
    public List<PamsStrategy> getPamsStrategyByKeyAndStrategyType(@Param("key") String key,
    		@Param("strategyType") Integer strategyType);
    
    

    /**
     * 插入策略，strategy_id create_time update_time由数据库操作
     * @param pamsStrategy
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{strategyId}, "
    		+ " #{strategyName}, "
    		+ " #{userId}, "
    		+ " #{username}, "
    		+ " #{status}, "
    		+ " #{strategyType}, "
    		+ " #{startDate}, "
    		+ " #{endDate1}, "
    		+ " #{endDate2}, "
    		+ " #{avgProfit}, "
    		+ " #{message}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "strategy_id", keyProperty = "strategyId")
    public void insertPamsStrategy(PamsStrategy pamsStrategy);
    
    /**
     * 修改策略的状态
     * @param strategyId
     * @param status
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " status = #{status} "
    		+ " WHERE "
    		+ " strategy_id = #{strategyId} "
    		+ "")
    public void setStatusByStrategyId(@Param("strategyId") Integer strategyId,
    		@Param("status") Integer status);
    
    /**
     * 修改策略的总和收益
     * @param strategyId
     * @param avgProfit
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " avg_profit = #{avgProfit} "
    		+ " WHERE "
    		+ " strategy_id = #{strategyId} "
    		+ "")
    public void setAvgProfitByStrategyId(@Param("strategyId") Integer strategyId,
    		@Param("avgProfit") BigDecimal avgProfit);
    
    /**
     * 删除策略
     * @param strategyId
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " strategy_id = #{strategyId} "
    		+ "")
    public void deletePamsStrategyByStrategyId(@Param("strategyId") Integer strategyId);
    
    
    /**
     * 更新未开始的策略
     * @param today
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " status = 2 "
    		+ " WHERE "
    		+ " start_date > #{today} "
    		+ "")
    public void setNotStartByTodayStr(@Param("today") String today);
    
    /**
     * 更新进行中的策略
     * @param today
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " status = 3 "
    		+ " WHERE "
    		+ " start_date <= #{today} "
    		+ " AND "
    		+ " end_date1 > #{today} "
    		+ "")
    public void setOngoingByTodayStr(@Param("today") String today);
    
    /**
     * 更新收尾中的策略
     * @param today
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " status = 4 "
    		+ " WHERE "
    		+ " end_date1 <= #{today} "
    		+ " AND "
    		+ " end_date2 >= #{today} "
    		+ "")
    public void setWindUpByTodayStr(@Param("today") String today);
    
    /**
     * 更新已结束的策略
     * @param today
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " status = 5 "
    		+ " WHERE "
    		+ " end_date2 < #{today} "
    		+ "")
    public void setClosedByTodayStr(@Param("today") String today);
}
