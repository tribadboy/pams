package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.nju.pams.model.PamsLoginInfo;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsLoginInfoDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_LOGIN_INFO;

    public static final String COL_ALL = " info_id, user_id, ip, login_time, create_time, update_time ";
    
    /**
     * 根据infoId查询用户登录信息
     * @param infoId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " info_id = #{infoId} "
            + "")
    public PamsLoginInfo getPamsLoginInfoByInfoId(@Param("infoId") Integer infoId);

    /**
     * 根据用户名查询该用户最近的10条登录信息
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
            + " login_time DESC "
            + " LIMIT 10 "
            + "")
    public List<PamsLoginInfo> getPamsLoginInfoListByUserId(@Param("userId") Integer userId);
    
    /**
     * 根据用户名查询该用户的注册时间
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
            + " login_time ASC "
            + " LIMIT 1 "
            + "")
    public PamsLoginInfo getPamsRegisterTimeByUserId(@Param("userId") Integer userId);
    
    /**
     * 插入用户登录信息，info_id create_time update_time由数据库操作
     * @param pamsLoginInfo
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{infoId}, "
    		+ " #{userId}, "
    		+ " #{ip}, "
    		+ " #{loginTime}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "info_id", keyProperty = "infoId")
    public void insertPamsLoginInfo(PamsLoginInfo pamsLoginInfo);
  
}
