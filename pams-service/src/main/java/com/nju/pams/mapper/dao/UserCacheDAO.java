package com.nju.pams.mapper.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.model.cacheable.UserCache;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface UserCacheDAO {
	
	public static final String TABLE = DatabaseConstant.DB_NAME + "." + DatabaseConstant.T_USER;
    public static final String COL_ALL = " id, username, password, age ";
    
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " id = #{id} "
            + "")
    public UserCache getUserCacheById(@Param("id") Integer id);
    
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
    		+ " #{age} "
    		+ " ) "
    		+ "")
    public void insertUserCache(UserCache userCache);
    
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " id = #{id} "
    		+ "")
    public void deleteUserCacheById(@Param("id") Integer id);
    
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " username = #{username}, "
    		+ " password = #{password}, "
    		+ " age = #{age} "
    		+ " WHERE "
    		+ " id = #{id} "
    		+ "")
    public void updateUserCache(UserCache userCache);
    
}
