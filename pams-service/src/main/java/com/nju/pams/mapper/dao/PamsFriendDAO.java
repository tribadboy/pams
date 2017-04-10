package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.model.socaility.PamsFriend;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsFriendDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_FRIEND;

    public static final String COL_ALL = " id, user_id, friend_id, friend_name,"
    		+ " friend_message, create_time, update_time ";
    
    /**
     * 根据id查询某一条好友记录
     * @param id
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " id = #{id} "
            + "")
    public PamsFriend getPamsFriendById(@Param("id") Integer id);
    
    /**
     * 根据userId和friendId查询某一条好友记录
     * @param userId
     * @param friendId
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
            + " friend_id = #{friendId} "
            + " LIMIT 1 "
            + "")
    public PamsFriend getPamsFriendByUserIdAndFriendId(@Param("userId") Integer userId, 
    		@Param("friendId") Integer friendId);
    
    /**
     * 根据userId查询你的所有朋友,按照好友姓名升序排序
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
            + " friend_name ASC "
            + "")
    public List<PamsFriend> getPamsFriendsByUserId(@Param("userId") Integer userId);
    

    /**
     * 插入好友记录，id create_time update_time由数据库操作
     * @param pamsFriend
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{id}, "
    		+ " #{userId}, "
    		+ " #{friendId}, "
    		+ " #{friendName}, "
    		+ " #{friendMessage}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    public void insertPamsFriend(PamsFriend pamsFriend);
    
    
    /**
     * 删除好友记录
     * @param id
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " id = #{id} "
    		+ "")
    public void deletePamsFriendById(@Param("id") Integer id);
    
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " friend_message = #{friendMessage} "
    		+ " WHERE "
    		+ " id = #{id}"
    		+ "")
    public void updatePamsFriendMessageById(@Param("id") Integer id, @Param("friendMessage") String friendMessage);
}
