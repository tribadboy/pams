package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.model.system.InformUserRef;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsInformUserRefDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_INFORM_USER_REF;

    public static final String COL_ALL = " ref_id, inform_id, user_id, username ";
    
    /**
     * 根据refId查询通知用户关联对象
     * @param refId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " ref_id = #{refId} "
            + "")
    public InformUserRef getInformUserRefByRefId(@Param("refId") Integer refId);
    
    /**
     * 根据informId和userId查询通知用户关联对象
     * @param informId
     * @param userId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " inform_id = #{informId} "
            + " AND "
            + " user_id = #{userId} "
            + " LIMIT 1 "
            + "")
    public InformUserRef getInformUserRefByInformIdAndUserId(@Param("informId") Integer informId, 
    		@Param("userId") Integer userId);
    
    /**
     * 获取所有通知用户关联对象
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " ORDER BY "
            + " inform_id ASC "
            + "")
    public List<InformUserRef> getAllInformUserRefs();
    
    
    /**
     * 获取针对某个用户的所有特定通知informId
     * @return
     */
    @Select(""
            + " SELECT "
            + " DISTINCT inform_id "
            + " FROM "
            + TABLE
            + " WHERE "
            + " user_id = #{userId} "
            + " ORDER BY "
            + " inform_id ASC "
            + "")
    public List<Integer> getAllSpecialInformIdByUserId(@Param("userId") Integer userId);
    
    /**
     * 插入关联，ref_id 由数据库操作
     * @param informUserRef
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{refId}, "
    		+ " #{informId}, "
    		+ " #{userId}, "
    		+ " #{username} "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "ref_id", keyProperty = "refId")
    public void insertInformUserRef(InformUserRef informUserRef);
    
    /**
     * 删除关联
     * @param refId
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " ref_id = #{refId} "
    		+ "")
    public void deleteInformUserRefByRefId(@Param("refId") Integer refId);
    
    /**
     * 删除关联
     * @param informId
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " inform_id = #{informId} "
    		+ "")
    public void deleteInformUserRefByInformId(@Param("informId") Integer informId);
}
