package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.model.asset.RegularIncome;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsRegularIncomeDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_REGULAR_INCOME;

    public static final String COL_ALL = " income_id, user_id, record_amount, record_time,"
    		+ " message, create_time, update_time ";
    
    /**
     * 根据incomeId查询用户收入记录
     * @param incomeId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " income_id = #{incomeId} "
            + "")
    public RegularIncome getRegularIncomeByIncomeId(@Param("incomeId") Integer incomeId);
    
    /**
     * 根据userId查询该用户的所有收入记录，并按照记录时间排序
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
            + " record_time ASC "
            + "")
    public List<RegularIncome> getRegularIncomeListByUserId(@Param("userId") Integer userId);
    
    /**
     * 获取用户记录的最大日期
     * @param userId
     * @return
     */
    @Select(""
            + " SELECT "
            + " MAX(record_time) "
            + " FROM "
            + TABLE
            + " WHERE "
            + " user_id = #{userId} "
            + "")
    public String getMaxDateByUserId(@Param("userId") Integer userId);
    
    /**
     * 获取用户记录的最小日期
     * @param userId
     * @return
     */
    @Select(""
            + " SELECT "
            + " MIN(record_time) "
            + " FROM "
            + TABLE
            + " WHERE "
            + " user_id = #{userId} "
            + "")
    public String getMinDateByUserId(@Param("userId") Integer userId);
    
    /**
     * 根据userId查询该用户的某个阶段记录的所有收入，并按照记录时间排序 [startDate, endDate)
     * @param userId
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
            + " user_id = #{userId} "
            + " AND "
            + " record_time >= #{startDate} "
            + " AND "
            + " record_time < #{endDate} "
            + " ORDER BY "
            + " record_time ASC "
            + "")
    public List<RegularIncome> getRegularIncomeListByUserIdInPeriodTime(@Param("userId") Integer userId,
    		@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 插入收入记录，income_id create_time update_time由数据库操作
     * @param regularIncome
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{incomeId}, "
    		+ " #{userId}, "
    		+ " #{recordAmount}, "
    		+ " #{recordTime}, "
    		+ " #{message}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "income_id", keyProperty = "incomeId")
    public void insertRegularIncome(RegularIncome regularIncome);
    
    /**
     * 更新收入信息，根据incomeId和userId
     * @param regularIncome
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " record_amount = #{recordAmount}, "
    		+ " record_time = #{recordTime}, "
    		+ " message = #{message} "
    		+ " WHERE "
    		+ " income_id = #{incomeId} "
    		+ " AND "
    		+ " user_id = #{userId} "
    		+ "")
    public void updateRegularIncome(RegularIncome regularIncome);
    
    /**
     * 删除收入记录
     * @param regularIncome
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " income_id = #{incomeId} "
    		+ "")
    public void deleteRegularIncomeByIncomeId(@Param("incomeId") Integer incomeId);
}
