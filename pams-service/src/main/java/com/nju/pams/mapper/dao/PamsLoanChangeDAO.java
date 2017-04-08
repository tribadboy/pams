package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.nju.pams.model.asset.LoanChange;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsLoanChangeDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_LOAN_CHANGE;

    public static final String COL_ALL = " change_id, loan_id, change_type_id, change_amount,"
    		+ " change_time, create_time, update_time ";
    
    /**
     * 根据changeId查询用户贷款变更记录
     * @param changeId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " change_id = #{changeId} "
            + "")
    public LoanChange getLoanChangeByChangeId(@Param("changeId") Integer changeId);
    
    /**
     * 根据loanId查询该贷款的所有的变更记录，并按照变更时间排序
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
            + " ORDER BY "
            + " change_time ASC "
            + "")
    public List<LoanChange> getLoanChangeListByLoanId(@Param("loanId") Integer loanId);
    
   
    /**
     * 插入贷款变更记录，change_id create_time update_time由数据库操作
     * @param loanChange
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{changeId}, "
    		+ " #{loanId}, "
    		+ " #{changeTypeId}, "
    		+ " #{changeAmount}, "
    		+ " #{changeTime}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "change_id", keyProperty = "changeId")
    public void insertLoanChange(LoanChange loanChange);
  
    
    /**
     * 删除贷款变更记录
     * @param changeId
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " change_id = #{changeId} "
    		+ "")
    public void deleteLoanChangeByChangeId(@Param("changeId") Integer changeId);
    
    /**
     * 删除某个贷款的所有变更记录
     * @param loadId
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " loan_id = #{loanId} "
    		+ "")
    public void deleteLoanChangeByLoanId(@Param("loanId") Integer loanId);
}
