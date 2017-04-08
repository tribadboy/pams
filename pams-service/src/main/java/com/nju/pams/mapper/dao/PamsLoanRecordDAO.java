package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.model.asset.LoanRecord;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsLoanRecordDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_LOAN_RECORD;

    public static final String COL_ALL = " loan_id, user_id, status, direction,"
    		+ " except_repay_amount, except_keep_time, message, create_time, update_time ";
    
    /**
     * 根据loanId查询用户的贷款账户
     * @param loanId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " loan_id = #{loanId} "
            + "")
    public LoanRecord getLoanRecordByLoanId(@Param("loanId") Integer loanId);
    
    /**
     * 根据userId查询该用户的所有有效的贷款账户
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
    public List<LoanRecord> getValidLoanRecordsByUserId(@Param("userId") Integer userId);
    

    /**
     * 插入贷款账户，load_id create_time update_time由数据库操作
     * @param loanRecord
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{loanId}, "
    		+ " #{userId}, "
    		+ " #{status}, "
    		+ " #{direction}, "
    		+ " #{exceptRepayAmount}, "
    		+ " #{exceptKeepTime}, "
    		+ " #{message}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "loan_id", keyProperty = "loanId")
    public void insertLoanRecord(LoanRecord loanRecord);
    
    /**
     * 更新贷款账户
     * @param loanRecord
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " status = #{status}, "
    		+ " direction = #{direction}, "
    		+ " except_repay_amount = #{exceptRepayAmount}, "
    		+ " except_keep_time = #{exceptKeepTime}, "
    		+ " message = #{message} "
    		+ " WHERE "
    		+ " loan_id = #{loanId} "
    		+ " AND "
    		+ " user_id = #{userId} "
    		+ "")
    public void updateLoanRecord(LoanRecord loanRecord);
    
    /**
     * 将贷款设置为无效状态（表示已结束)
     * @param loanId
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " status = 1 "
    		+ " WHERE "
    		+ " loan_id = #{loanId} "
    		+ "")
    public void closeLoanRecordByLoanId(@Param("loanId") Integer loanId);
    
    /**
     * 删除贷款账户
     * @param loanRecord
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " loan_id = #{loanId} "
    		+ "")
    public void deleteLoanRecordByLoanId(@Param("loanId") Integer loanId);
}
