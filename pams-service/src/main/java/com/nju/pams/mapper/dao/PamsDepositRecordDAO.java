package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.model.asset.DepositRecord;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsDepositRecordDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_DEPOSIT_RECORD;

    public static final String COL_ALL = " deposit_id, user_id, status, deposit_time_id,"
    		+ " current_profit_percent, fixed_profit_percent, message, create_time, update_time ";
    
    /**
     * 根据depositId查询用户的存储账户
     * @param depositId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " deposit_id = #{depositId} "
            + "")
    public DepositRecord getDepositRecordByDepositId(@Param("depositId") Integer depositId);
    
    /**
     * 根据userId查询该用户的所有有效的存储账户
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
            + " AND "
            + " status = 0 "
            + "")
    public List<DepositRecord> getValidDepositRecordsByUserId(@Param("userId") Integer userId);
    
    /**
     * 根据userId查询该用户的所有存储账户
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
    public List<DepositRecord> getAllDepositRecordsByUserId(@Param("userId") Integer userId);
    

    /**
     * 插入存储账户，deposit_id create_time update_time由数据库操作
     * @param depositRecord
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{depositId}, "
    		+ " #{userId}, "
    		+ " #{status}, "
    		+ " #{depositTimeId}, "
    		+ " #{currentProfitPercent}, "
    		+ " #{fixedProfitPercent}, "
    		+ " #{message}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "deposit_id", keyProperty = "depositId")
    public void insertDepositRecord(DepositRecord depositRecord);
    
    /**
     * 将账户设置为无效状态（表示已全部转出)
     * @param depositId
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " status = 1 "
    		+ " WHERE "
    		+ " deposit_id = #{depositId} "
    		+ "")
    public void closeDepositRecordByDepositId(@Param("depositId") Integer depositId);
    
    /**
     * 删除存储账户
     * @param depositRecord
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " deposit_id = #{depositId} "
    		+ "")
    public void deleteDepositRecordByDepositId(@Param("depositId") Integer depositId);
}
