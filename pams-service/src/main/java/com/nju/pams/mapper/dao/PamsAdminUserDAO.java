package com.nju.pams.mapper.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.nju.pams.model.PamsAdminUser;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsAdminUserDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_ADMIN_USER;

    public static final String COL_ALL = " user_id, username ";
    
    /**
     * 根据userId查询管理员
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
    public PamsAdminUser getPamsAdminUserByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户名查询管理员
     * @param username
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " username = #{username} "
            + "")
    public PamsAdminUser getPamsAdminUserByUsername(@Param("username") String username);
    
    /**
     * 插入管理员数据
     * @param pamsAdminUser
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{userId}, "
    		+ " #{username} "
    		+ " ) "
    		+ "")
    public void insertPamsAdminUser(PamsAdminUser pamsAdminUser);
    
}
