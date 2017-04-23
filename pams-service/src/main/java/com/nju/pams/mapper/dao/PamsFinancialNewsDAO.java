package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.model.system.FinancialNews;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsFinancialNewsDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_FINANCIAL_NEWS;

    public static final String COL_ALL = " news_id, title, origin, record_date, picture_name,"
    		+ " content, create_time, update_time ";
    
    /**
     * 根据newsId查询新闻
     * @param newsId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " news_id = #{newsId} "
            + "")
    public FinancialNews getFinancialNewsByNewsId(@Param("newsId") Integer newsId);
 
 
    /**
     * 查询所有新闻，并按照账目时间降序排列
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " ORDER BY "
            + " record_date DESC "
            + "")
    public List<FinancialNews> getFinancialNewsList();
  
    /**
     * 插入新闻，news_id create_time update_time由数据库操作
     * @param financialNews
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{newsId}, "
    		+ " #{title}, "
    		+ " #{origin}, "
    		+ " #{recordDate}, "
    		+ " #{pictureName}, "
    		+ " #{content}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "news_id", keyProperty = "newsId")
    public void insertFinancialNews(FinancialNews financialNews);
    
    /**
     * 删除新闻
     * @param newsId
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " news_id = #{newsId} "
    		+ "")
    public void deleteFinancialNewsByNewsId(@Param("newsId") Integer newsId);
}
