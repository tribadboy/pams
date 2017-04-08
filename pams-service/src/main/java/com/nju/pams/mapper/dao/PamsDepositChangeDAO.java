package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.nju.pams.model.asset.DepositChange;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsDepositChangeDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_DEPOSIT_CHANGE;

    public static final String COL_ALL = " change_id, deposit_id, change_type_id, change_amount,"
    		+ " change_time, create_time, update_time ";
    
    /**
     * 根据changeId查询用户账户变更记录
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
    public DepositChange getDepositChangeByChangeId(@Param("changeId") Integer changeId);
    
    /**
     * 获取某个存款的最新一次变更时间
     * @param depositId
     * @return
     */
    @Select(""
            + " SELECT "
            + " MAX(change_time) "
            + " FROM "
            + TABLE
            + " WHERE "
            + " deposit_id = #{depositId} "
            + "")
    public String getMaxChangeDateByDepositId(@Param("depositId") Integer depositId);
    
    /**
     * 根据depositId查询该存款的所有的变更记录，并按照变更时间排序
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
            + " ORDER BY "
            + " change_time ASC "
            + "")
    public List<DepositChange> getDepositChangeListByDepositId(@Param("depositId") Integer depositId);
    
   
    /**
     * 插入存款变更记录，change_id create_time update_time由数据库操作
     * @param depositChange
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{changeId}, "
    		+ " #{depositId}, "
    		+ " #{changeTypeId}, "
    		+ " #{changeAmount}, "
    		+ " #{changeTime}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "change_id", keyProperty = "changeId")
    public void insertDepositChange(DepositChange depositChange);
  
    
    /**
     * 删除存款变更记录
     * @param changeId
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " change_id = #{changeId} "
    		+ "")
    public void deleteDepositChangeByChangeId(@Param("changeId") Integer changeId);
    
    /**
     * 删除某个存款的所有变更记录
     * @param depositId
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " deposit_id = #{depositId} "
    		+ "")
    public void deleteDepositChangeByDepositId(@Param("depositId") Integer depositId);
}
