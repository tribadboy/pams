package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.model.socaility.FriendLetter;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsFriendLetterDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_FRIEND_LETTER;

    public static final String COL_ALL = " letter_id, send_user_id, send_user_name, receive_user_id,"
    		+ " receive_user_name, status, content, create_time, update_time ";
    
    /**
     * 根据letterId查询某一条好友私信
     * @param letterId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " letter_id = #{letterId} "
            + "")
    public FriendLetter getFriendLetterByLetterId(@Param("letterId") Integer letterId);
    
    /**
     * 根据sendUserName查询某个用户发出的所有私信,按照创建时间降序排序
     * @param sendUserName
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " send_user_name = #{sendUserName} "
            + " ORDER BY "
            + " create_time DESC "
            + "")
    public List<FriendLetter> getFriendLettersBySendUserName(@Param("sendUserName") String sendUserName);
    
    /**
     * 根据receiveUserName查询某个用户收到的所有私信，按照创建时间降序排序
     * @param receiveUserName
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " receive_user_name = #{receiveUserName} "
            + " ORDER BY "
            + " create_time DESC "
            + "")
    public List<FriendLetter> getPamsLettersByReceiveUserName(@Param("receiveUserName") String receiveUserName);
    

    /**
     * 插入好友私信，letter_id create_time update_time由数据库操作
     * @param friendLetter
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{letterId}, "
    		+ " #{sendUserId}, "
    		+ " #{sendUserName}, "
    		+ " #{receiveUserId}, "
    		+ " #{receiveUserName}, "
    		+ " #{status}, "
    		+ " #{content}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "letter_id", keyProperty = "letterId")
    public void insertFriendLetter(FriendLetter friendLetter);
    
    
    /**
     * 删除好友私信
     * @param letterId
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " letter_id = #{letterId} "
    		+ "")
    public void deleteFriendLetterByLetterId(@Param("letterId") Integer letterId);
    
    /**
     * 根据letterId设置该好友私信的状态为已读
     * @param letterId
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " status = 1 "
    		+ " WHERE "
    		+ " letter_id = #{letterId} "
    		+ "")
    public void setReadForFriendLetterByLetterId(@Param("letterId") Integer letterId);
}
