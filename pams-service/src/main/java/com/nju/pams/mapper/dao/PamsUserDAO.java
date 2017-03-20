package com.nju.pams.mapper.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.model.PamsUser;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsUserDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_USER;

    public static final String COL_ALL = " id, username, password, status, phone,"
    		+ " mail, create_time, update_time ";
    
    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " id = #{id}"
            + "")
    public PamsUser getPamsUserById(@Param("id") Integer id);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " username = #{username}"
            + "")
    public PamsUser getPamsUserByUsername(@Param("username") String username);
    
    /**
     * 插入用户，id create_time update_time由数据库操作
     * @param pamsUser
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{id}, "
    		+ " #{username}, "
    		+ " #{password}, "
    		+ " #{status}, "
    		+ " #{phone}, "
    		+ " #{mail}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    public void insertPamsUser(PamsUser pamsUser);
    
    /**
     * 更新用户信息，根据用户id和username
     * @param pamsUser
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " password = #{password}, "
    		+ " status = #{status}, "
    		+ " phone = #{phone}, "
    		+ " mail = #{mail} "
    		+ " WHERE "
    		+ " id = #{id} "
    		+ " AND "
    		+ " username = #{username} "
    		+ "")
    public void updatePamsUser(PamsUser pamsUser);
    
    /**
     * 暂时使用完全删除，之后考虑伪删除
     * @param username
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " username = #{username} "
    		+ "")
    public void deletePamsUserByUsername(@Param("username") String username);
}
