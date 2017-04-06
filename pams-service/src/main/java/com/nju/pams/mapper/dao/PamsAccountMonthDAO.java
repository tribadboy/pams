package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.model.asset.AccountOfMonth;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsAccountMonthDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_ACCOUNT_MONTH;

    public static final String COL_ALL = " id, user_id, consumption_id, cost, spend_month,"
    		+ " number_of_account, days_of_month, create_time, update_time ";
    
    /**
     * 根据id查询用户某月账目
     * @param id
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " id = #{id} "
            + "")
    public AccountOfMonth getAccountOfMonthById(@Param("id") Integer id);
    
    /**
     * 查询某个用户在某一消费类别下的某个月的总和账目
     * @param userId
     * @param consumptionId
     * @param spendMonth
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
            + " consumption_id = #{consumptionId} "
            + " AND "
            + " spend_month = #{spendMonth} "
            + " LIMIT 1 "
            + "")
    public AccountOfMonth getAccountOfMonthByMonth(@Param("userId") Integer userId,
    		@Param("consumptionId") Integer consumptionId,
    		@Param("spendMonth") String spendMonth);
    
    /**
     * 查询某个用户在某个月的总和账目
     * @param userId
     * @param spendMonth
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
            + " spend_month = #{spendMonth} "
            + " ORDER BY "
            + " consumption_id ASC "
            + "")
    public List<AccountOfMonth> getAccountOfMonthListByCertainMonth(@Param("userId") Integer userId,
    		@Param("spendMonth") String spendMonth);
    
    /**
     * 查询某个用户在某几个月的账目总和 	[startMonth, endMonth)
     * @param userId
     * @param startMonth
     * @param endMonth
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
            + " spend_month >= #{startMonth} "
            + " AND "
            + " spend_month < #{endMonth} "
            + " ORDER BY "
            + " spend_month, consumption_id ASC "
            + "")
    public List<AccountOfMonth> getAccountOfMonthListByPeriodMonth(@Param("userId") Integer userId,
    		@Param("startMonth") String startMonth, @Param("endMonth") String endMonth);
    
    /**
     * 根据userId查询该用户的所有月份账目列表，并按照账目时间排序
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
            + " spend_month, consumption_id ASC "
            + "")
    public List<AccountOfMonth> getAccountOfMonthListByUserId(
    		@Param("userId") Integer userId);
    

    /**
     * 插入月份账目，id create_time update_time由数据库操作
     * @param accountOfMonth
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{id}, "
    		+ " #{userId}, "
    		+ " #{consumptionId}, "
    		+ " #{cost}, "
    		+ " #{spendMonth}, "
    		+ " #{numberOfAccount}, "
    		+ " #{daysOfMonth}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    public void insertAccountOfMonth(AccountOfMonth accountOfMonth);
    
    /**
     * 更新月份账目信息，根据id
     * @param accountOfMonth
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " cost = #{cost}, "
    		+ " number_of_account = #{numberOfAccount} "
    		+ " WHERE "
    		+ " id = #{id} "
    		+ "")
    public void updateAccountOfMonth(AccountOfMonth accountOfMonth);
    
    /**
     * 删除月份账目
     * @param id
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " id = #{id} "
    		+ "")
    public void deleteAccountOfMonthById(@Param("id") Integer id);
}
