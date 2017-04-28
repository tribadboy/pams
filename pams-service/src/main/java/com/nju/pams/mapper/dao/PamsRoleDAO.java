package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.model.PamsRole;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsRoleDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_ROLE;

    public static final String COL_ALL = " role_id, role_name, create_time, update_time ";
    
    /**
     * 根据roleId查询角色
     * @param roleId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " role_id = #{roleId} "
            + "")
    public PamsRole getPamsRoleByRoleId(@Param("roleId") Integer roleId);
    
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + "")
    public List<PamsRole> getAllPamsRole();
    
    /**
     * 根据roleName查询角色
     * @param roleName
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " role_name = #{roleName} "
            + " LIMIT 1 "
            + "")
    public PamsRole getPamsRoleByRoleName(@Param("roleName") String roleName);
    
    /**
     * 插入角色，roleId create_time update_time由数据库操作
     * @param pamsRole
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{roleId}, "
    		+ " #{roleName}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "role_id", keyProperty = "roleId")
    public void insertPamsRole(PamsRole pamsRole);
    
    /**
     * 更新角色名称，根据用户roleId
     * @param pamsRole
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " role_name = #{roleName} "
    		+ " WHERE "
    		+ " role_id = #{roleId} "
    		+ "")
    public void updatePamsRole(PamsRole pamsRole);
    
    /**
     * 删除角色名
     * @param roleId
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " role_id = #{roleId} "
    		+ "")
    public void deletePamsRoleByRoleId(@Param("roleId") Integer roleId);
    
    /**
     * 根据用户名查询该用户拥有的角色列表
     * @param username
     * @return
     */
    @Select(""
    		+ " SELECT "
    		+ " y.role_id as roleId, "
    		+ " y.role_name as roleName, "
    		+ " y.create_time as createTime, "
    		+ " y.update_time as updateTime "
    		+ " FROM "
    		+ DatabaseConstant.T_PAMS_USER_ROLE + " x "
    		+ " LEFT JOIN "
    		+ DatabaseConstant.T_PAMS_ROLE + " y "
    		+ " USING(role_id) "
    		+" LEFT JOIN "
    		+ DatabaseConstant.T_PAMS_USER + " z "
    		+ " USING(user_id) "
    		+ " WHERE "
    		+ " z.username = #{username} "
    		+ "")
    public List<PamsRole> listRolesForUserByUsername(@Param("username") String username);
    
    /**
     * 批量给用户添加多个角色的权限
     * @param id 设置为null，为匹配数据库主键自增字段
     * @param userId
     * @param rolesList
     * @return
     */
    @Insert(""
    		+ " <script> "
            + " INSERT INTO "
            + DatabaseConstant.T_PAMS_USER_ROLE
            + " (`id`, `user_id`, `role_id`) "
            + " VALUES "
            + " <foreach collection=\"rolesList\" item=\"item\" separator=\",\" > "
            + " (#{id}, #{userId}, #{item.roleId}) "
			+ " </foreach> "
            + " </script> "
			+ "")
	public int addRolesListForUser(@Param("id") Integer id, @Param("userId") Integer userId,
			@Param("rolesList") List<PamsRole> rolesList);
    
    /**
     * 批量给用户删除多个角色的权限
     * @param userId
     * @param rolesList
     * @return
     */
    @Delete(""
    		+ " <script> "
            + " DELETE FROM " 
            + DatabaseConstant.T_PAMS_USER_ROLE
            + " WHERE "
            + " user_id = #{userId} "
            + " AND "
            + " `role_id` in "
            + " <foreach collection=\"rolesList\" item=\"item\" separator=\",\" "
            + " open=\"(\" close=\")\" > "
            + " #{item.roleId} "
            + " </foreach> "
            + " </script> "
            + "")
    public int deleteRolesListForUser(@Param("userId") Integer userId,
			@Param("rolesList") List<PamsRole> rolesList);
}
