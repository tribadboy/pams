package com.nju.pams.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nju.pams.model.PamsPermission;
import com.nju.pams.model.constant.DatabaseConstant;
import com.nju.pams.util.annotation.DAOMapper;

@DAOMapper
public interface PamsPermissionDAO {
	public static final String TABLE = DatabaseConstant.T_PAMS_PERMISSION;

    public static final String COL_ALL = " permission_id, permission_name, create_time, update_time ";
    
    /**
     * 根据permissionId查询权限
     * @param permissionId
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " permission_id = #{permissionId} "
            + "")
    public PamsPermission getPamsPermissionByPermissionId(@Param("permissionId") Integer permissionId);
    
    /**
     * 根据permissionName查询权限
     * @param permissionName
     * @return
     */
    @Select(""
            + " SELECT "
            + COL_ALL
            + " FROM "
            + TABLE
            + " WHERE "
            + " permission_name = #{permissionName} "
            + "")
    public PamsPermission getPamsPermissionByPermissionName(@Param("permissionName") String permissionName);
    
    /**
     * 插入权限，permission_id create_time update_time由数据库操作
     * @param pamsPermission
     */
    @Insert(""
    		+ " INSERT "
    		+ " INTO "
    		+ TABLE
    		+ " ( " + COL_ALL + " ) "
    		+ " VALUES "
    		+ " ( " 
    		+ " #{permissionId}, "
    		+ " #{permissionName}, "
    		+ " NOW(), "
    		+ " NOW() "
    		+ " ) "
    		+ "")
    @Options(useGeneratedKeys = true, keyColumn = "permission_id", keyProperty = "permissionId")
    public void insertPamsPermission(PamsPermission pamsPermission);
    
    /**
     * 更新权限名称，根据用户permissionId
     * @param pamsPermission
     */
    @Update(""
    		+ " UPDATE "
    		+ TABLE
    		+ " SET "
    		+ " permission_name = #{permissionName} "
    		+ " WHERE "
    		+ " permission_id = #{permissionId} "
    		+ "")
    public void updatePamsPermission(PamsPermission pamsPermission);
    
    /**
     * 删除权限名
     * @param permissionId
     */
    @Delete(""
    		+ " DELETE "
    		+ " FROM "
    		+ TABLE
    		+ " WHERE "
    		+ " permission_id = #{permissionId} "
    		+ "")
    public void deletePamsPermissionByPermissionId(@Param("permissionId") Integer permissionId);
    
    /**
     * 根据角色名查询该角色拥有的权限列表
     * @param roleName
     * @return
     */
    @Select(""
    		+ " SELECT "
    		+ " y.permission_id as permissionId, "
    		+ " y.permission_name as permissionName, "
    		+ " y.create_time as createTime, "
    		+ " y.update_time as updateTime "
    		+ " FROM "
    		+ DatabaseConstant.T_PAMS_ROLE_PERMISSION + " x "
    		+ " LEFT JOIN "
    		+ DatabaseConstant.T_PAMS_PERMISSION + " y "
    		+ " USING(permission_id) "
    		+" LEFT JOIN "
    		+ DatabaseConstant.T_PAMS_ROLE + " z "
    		+ " USING(role_id) "
    		+ " WHERE "
    		+ " z.role_name = #{roleName} "
    		+ "")
    public List<PamsPermission> listPermissionsForRoleByRoleName(@Param("roleName") String roleName);
    
    /**
     * 批量给某个角色添加多个权限
     * @param id 设置为null，为匹配数据库主键自增字段
     * @param roleId
     * @param permissionsList
     * @return
     */
    @Insert(""
    		+ " <script> "
            + " INSERT INTO "
            + DatabaseConstant.T_PAMS_ROLE_PERMISSION
            + " (`id`, `role_id`, `permission_id`) "
            + " VALUES "
            + " <foreach collection=\"permissionsList\" item=\"item\" separator=\",\" > "
            + " (#{id}, #{roleId}, #{item.permissionId}) "
			+ " </foreach> "
            + " </script> "
			+ "")
	public int addPermissionsListForRole(@Param("id") Integer id, @Param("roleId") Integer roleId,
			@Param("permissionsList") List<PamsPermission> permissionsList);
    
    /**
     * 批量给某个角色删除多个权限
     * @param roleId
     * @param permissionsList
     * @return
     */
    @Delete(""
    		+ " <script> "
            + " DELETE FROM " 
            + DatabaseConstant.T_PAMS_ROLE_PERMISSION
            + " WHERE "
            + " role_id = #{roleId} "
            + " AND "
            + " `permission_id` in "
            + " <foreach collection=\"permissionsList\" item=\"item\" separator=\",\" "
            + " open=\"(\" close=\")\" > "
            + " #{item.permissionId} "
            + " </foreach> "
            + " </script> "
            + "")
    public int deletePermissionsListForRole(@Param("roleId") Integer roleId,
			@Param("permissionsList") List<PamsPermission> permissionsList);
}
