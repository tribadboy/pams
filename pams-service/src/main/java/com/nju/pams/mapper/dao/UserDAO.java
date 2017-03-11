package com.nju.pams.mapper.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.nju.pams.model.User;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface UserDAO {
	public static final String TABLE = " pams_db.user_t ";

    public static final String COL_ALL = " id, username, password, age ";
    
    @Select(""
            + " select "
            + COL_ALL
            + " from "
            + TABLE
            + " where "
            + " id = #{id}"
            + "")
    public User getUserById(@Param("id") int id);
}
