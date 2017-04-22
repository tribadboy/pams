package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.model.system.Feedback;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsFeedbackDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_FEEDBACK;

    public static final String COL_ALL = " back_id, user_id, username, record_title, record_date,"
    		+ " feed_type_str, status, message, create_time, update_time ";
    
    /**
     * 根据backId查询用户的反馈
     * @param backId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " back_id = #{backId} "
            + "")
    public Feedback getFeedBackByBackId(@Param("backId") Integer backId);
    
    /**
     * 查询所有未查看的反馈
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " status = 0 "
            + " ORDER BY "
            + " record_date "
            + "")
    public List<Feedback> getUncheckedFeedbackList();
    
    /**
     * 查询所有已查看的反馈
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " status = 1 "
            + " ORDER BY "
            + " record_date "
            + "")
    public List<Feedback> getCheckedFeedbackList();
    
    
    /**
     * 查询所有用户反馈
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " ORDER BY "
            + " record_date "
            + "")
    public List<Feedback> getAllFeedbackList();
    

    /**
     * 插入反馈记录，back_id create_time update_time由数据库操作
     * @param feedback
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{backId}, "
    		+ " #{userId}, "
    		+ " #{username}, "
    		+ " #{recordTitle}, "
    		+ " #{recordDate}, "
    		+ " #{feedTypeStr}, "
    		+ " #{status}, "
    		+ " #{message}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "back_id", keyProperty = "backId")
    public void insertLoanRecord(Feedback feedback);
    
    /**
     * 将反馈设置为已查看
     * @param backId
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " status = 1 "
    		+ " WHERE "
    		+ " back_id = #{backId} "
    		+ "")
    public void setCheckedFeedbackByBackId(@Param("backId") Integer backId);
    
    /**
     * 删除贷款账户
     * @param feedback
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " back_id = #{backId} "
    		+ "")
    public void deleteFeedbackByBackId(@Param("backId") Integer backId);
}
