package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.model.socaility.FriendRequest;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsFriendRequestDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_FRIEND_REQUEST;

    public static final String COL_ALL = " request_id, request_user_id, request_user_name, response_user_id,"
    		+ " response_user_name, status, message, create_time, update_time ";
    
    /**
     * 根据requestId查询某一条好友请求记录
     * @param requestId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " request_id = #{requestId} "
            + "")
    public FriendRequest getFriendRequestByRequestId(@Param("requestId") Integer requestId);
    
    /**
     * 根据requestUserName和respnseUserName查询是否存在正在请求中的好友请求
     * @param requestUserName
     * @param responseUserName
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " request_user_name = #{requestUserName} "
            + " AND "
            + " response_user_name = #{responseUserName} "
            + " AND "
            + " status = 0 "
            + "")
    public FriendRequest getFriendRequestingByBothUsername(@Param("requestUserName") String requestUserName,
    		@Param("responseUserName") String responseUserName);
    
    /**
     * 根据requestUserName查询某个用户发出的所有好友请求,按照创建时间倒叙排序
     * @param requestUserName
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " request_user_name = #{requestUserName} "
            + " ORDER BY "
            + " create_time DESC "
            + "")
    public List<FriendRequest> getPamsFriendsByRequestUserName(@Param("requestUserName") String requestUserName);
    
    /**
     * 根据responseUserName查询某个用户收到的所有好友请求，按照创建时间倒叙排序
     * @param responseUserName
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " response_user_name = #{responseUserName} "
            + " ORDER BY "
            + " create_time DESC "
            + "")
    public List<FriendRequest> getPamsFriendsByResponseUserName(@Param("responseUserName") String responseUserName);
    

    /**
     * 插入好友请求记录，requset_id create_time update_time由数据库操作
     * @param friendRequest
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{requestId}, "
    		+ " #{requestUserId}, "
    		+ " #{requestUserName}, "
    		+ " #{responseUserId}, "
    		+ " #{responseUserName}, "
    		+ " #{status}, "
    		+ " #{message}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "request_id", keyProperty = "requestId")
    public void insertFriendRequest(FriendRequest friendRequest);
    
    
    /**
     * 删除好友请求记录
     * @param requestId
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " request_id = #{requestId} "
    		+ "")
    public void deleteFriendRequestByRequestId(@Param("requestId") Integer requestId);
    
    /**
     * 根据requestId设置该好友请求的状态为已拒绝
     * @param requestId
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " status = 1 "
    		+ " WHERE "
    		+ " request_id = #{requestId} "
    		+ "")
    public void setRejectForFriendRequestByRequestId(@Param("requestId") Integer requestId);
}
