package com.nju.pams.mapper.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.model.PamsUserPhoto;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsUserPhotoDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_USER_PHOTO;

    public static final String COL_ALL = " user_id, photo_name, create_time, update_time ";
    
    /**
     * 根据userId查询用户照片
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
    public PamsUserPhoto getPamsUserPhotoByUserId(@Param("userId") Integer userId);
    
    
    /**
     * 插入用户图片，create_time update_time由数据库操作
     * @param pamsUserPhoto
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{userId}, "
    		+ " #{photoName}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    public void insertPamsUserPhoto(PamsUserPhoto pamsUserPhoto);
    
    /**
     * 更新图片名称，根据用户userId
     * @param pamsUserPhoto
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " photo_name = #{photoName} "
    		+ " WHERE "
    		+ " user_id = #{userId} "
    		+ "")
    public void updatePamsUserPhoto(PamsUserPhoto pamsUserPhoto);
   
}
