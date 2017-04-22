package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.mapper.dao.sqlProvider.PamsAccountProvider;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.model.consumption.ConsumptionAccount;
import com.nju.pams.model.consumption.ConsumptionCondition;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsAccountDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_ACCOUNT;

    public static final String COL_ALL = " account_id, user_id, consumption_id, cost, spend_time,"
    		+ " message, create_time, update_time ";
    
    /**
     * 根据accountId查询用户账目
     * @param accountId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " account_id = #{accountId} "
            + "")
    public ConsumptionAccount getConsumptionAccountByAccountId(
    		@Param("accountId") Integer accountId);
    
    /**
     * 根据userId获取最大消费日期
     * @param userId
     * @return
     */
    @Select(""
            + " SELECT "
            + " MAX(spend_time) "
            + " FROM "
            + TABLE
            + " WHERE "
            + " user_id = #{userId} "
            + "")
    public String getMaxDateByUserId(@Param("userId") Integer userId);
    
    /**
     * 根据userId获取最小消费日期
     * @param userId
     * @return
     */
    @Select(""
            + " SELECT "
            + " MIN(spend_time) "
            + " FROM "
            + TABLE
            + " WHERE "
            + " user_id = #{userId} "
            + "")
    public String getMinDateByUserId(@Param("userId") Integer userId);
    
    /**
     * 根据userId查询该用户的所有账目列表，并按照账目时间排序
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
            + " spend_time, consumption_id ASC "
            + "")
    public List<ConsumptionAccount> getConsumptionAccountListByUserId(
    		@Param("userId") Integer userId);
    
    /**
     * 多条件查询消费
     * @param consumptionCondition
     * @return
     */
    @SelectProvider(type = PamsAccountProvider.class, method = "selectAccountByCondition")
    public List<ConsumptionAccount> selectAccountByCondition(@Param("consumptionCondition") ConsumptionCondition consumptionCondition);

    /**
     * 插入账目，account_id create_time update_time由数据库操作
     * @param consumptionAccount
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{accountId}, "
    		+ " #{userId}, "
    		+ " #{consumptionId}, "
    		+ " #{cost}, "
    		+ " #{spendTime}, "
    		+ " #{message}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "account_id", keyProperty = "accountId")
    public void insertConsumptionAccount(ConsumptionAccount consumptionAccount);
    
    /**
     * 更新账目信息，根据accountId和userId
     * @param consumptionAccount
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " consumption_id = #{consumptionId}, "
    		+ " cost = #{cost}, "
    		+ " spend_time = #{spendTime}, "
    		+ " message = #{message} "
    		+ " WHERE "
    		+ " account_id = #{accountId} "
    		+ " AND "
    		+ " user_id = #{userId} "
    		+ "")
    public void updateConsumptionAccount(ConsumptionAccount consumptionAccount);
    
    /**
     * 删除账目
     * @param accountId
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " account_id = #{accountId} "
    		+ "")
    public void deleteConsumptionAccountByAccountId(@Param("accountId") Integer accountId);
}
