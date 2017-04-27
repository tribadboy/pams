package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.model.system.PamsInform;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsInformDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_INFORM;

    public static final String COL_ALL = " inform_id, status, inform_type_id,"
    		+ " record_date, message, create_time, update_time ";
    
    /**
     * 根据informId查询通知
     * @param informId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " inform_id = #{informId} "
            + "")
    public PamsInform getPamsInformByInformId(@Param("informId") Integer informId);
    
    /**
     * 获取所有有效的通知
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " status = 0 "
            + " ORDER BY "
            + " record_date DESC "
            + "")
    public List<PamsInform> getValidPamsInforms();
    
    /**
     * 获取所有有效的类型为全体的通知
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " status = 0 "
            + " AND "
            + " inform_type_id = 0 "
            + " ORDER BY "
            + " record_date DESC "
            + "")
    public List<PamsInform> getValidTypeTotalPamsInforms();
    
    /**
     * 获取所有有效的类型为特定的通知
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " status = 0 "
            + " AND "
            + " inform_type_id = 1 "
            + " ORDER BY "
            + " record_date DESC "
            + "")
    public List<PamsInform> getValidTypeSpecialPamsInforms();
    
    /**
     * 获取所有通知
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + "")
    public List<PamsInform> getAllPamsInforms();
    
    /**
     * 获取所有针对全体用户的通知
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " inform_type_id = 0 "
            + " ORDER BY "
            + " record_date DESC "
            + "")
    public List<PamsInform> getAllTypeTotalPamsInforms();
    
    /**
     * 获取所有针对特定用户的通知
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " inform_type_id = 1 "
            + " ORDER BY "
            + " record_date DESC "
            + "")
    public List<PamsInform> getAllTypeSpecialPamsInforms();
    
    /**
     * 插入通知，inform_id create_time update_time由数据库操作
     * @param pamsInform
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{informId}, "
    		+ " #{status}, "
    		+ " #{informTypeId}, "
    		+ " #{recordDate}, "
    		+ " #{message}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "inform_id", keyProperty = "informId")
    public void insertPamsInform(PamsInform pamsInform);
    
    /**
     * 更新通知日期和内容
     * @param pamsInform
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " record_date = #{recordDate}, "
    		+ " message = #{message} "
    		+ " WHERE "
    		+ " inform_id = #{informId} "
    		+ "")
    public void updatePamsInform(PamsInform pamsInform);
    
    /**
     * 更新通知状态
     * @param pamsInform
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " status = #{status} "
    		+ " WHERE "
    		+ " inform_id = #{informId} "
    		+ "")
    public void setPamsInformStatus(PamsInform pamsInform);
    
    /**
     * 更新通知类型
     * @param pamsInform
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " inform_type_id = #{informTypeId} "
    		+ " WHERE "
    		+ " inform_id = #{informId} "
    		+ "")
    public void setPamsInformType(PamsInform pamsInform);
    
    /**
     * 删除通知
     * @param informId
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " inform_id = #{informId} "
    		+ "")
    public void deletePamsInformByInformId(@Param("informId") Integer informId);
}
