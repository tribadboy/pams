package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.model.asset.FixedAsset;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsFixedAssetDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_FIXED_ASSET;

    public static final String COL_ALL = " asset_id, user_id, record_name, record_value, record_time,"
    		+ " message, create_time, update_time ";
    
    /**
     * 根据assetId查询用户固定资产记录
     * @param assetId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " asset_id = #{assetId} "
            + "")
    public FixedAsset getFixedAssetByAssetId(@Param("assetId") Integer assetId);
    
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
     * 根据userId查询该用户的所有固定资产列表，并按照记录时间排序
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
    public List<FixedAsset> getFixedAssetListByUserId(@Param("userId") Integer userId);
    
    /**
     * 根据userId查询该用户的某个阶段记录的所有固定资产，并按照记录时间排序 [startDate, endDate)
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
    public List<FixedAsset> getFixedAssetListByUserIdInPeriodTime(@Param("userId") Integer userId,
    		@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 插入固定资产记录，asset_id create_time update_time由数据库操作
     * @param fixedAsset
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{assetId}, "
    		+ " #{userId}, "
    		+ " #{recordName}, "
    		+ " #{recordValue}, "
    		+ " #{recordTime}, "
    		+ " #{message}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "asset_id", keyProperty = "assetId")
    public void insertFixedAsset(FixedAsset fixedAsset);
    
    /**
     * 更新固定资产信息，根据assetId和userId
     * @param fixedAsset
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " record_name = #{recordName}, "
    		+ " record_value = #{recordValue}, "
    		+ " record_time = #{recordTime}, "
    		+ " message = #{message} "
    		+ " WHERE "
    		+ " asset_id = #{assetId} "
    		+ " AND "
    		+ " user_id = #{userId} "
    		+ "")
    public void updateFixedAsset(FixedAsset fixedAsset);
    
    /**
     * 删除固定资产记录
     * @param fixedAsset
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " asset_id = #{assetId} "
    		+ "")
    public void deleteFixedAssetByAssetId(@Param("assetId") Integer assetId);
}
