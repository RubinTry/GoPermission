package cn.rubintry.gopermission.db

import androidx.room.*


@Dao
interface PermissionDao {
    /**
     * 插入权限列表
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPermission(permission: Permission)

    /**
     * 插入权限列表
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPermissions(permission: List<Permission>)


    /**
     * 根据指定字段获取对象
     */
    @Query("SELECT * FROM go_permission_data WHERE permissionName = :name LIMIT 1")
    fun findPermissionByName(name: String?): Permission?


    @Delete(entity = Permission::class)
    fun delete(permission: Permission)


    @Query("DELETE FROM go_permission_data WHERE permissionName = :permissionName")
    fun deleteByName(permissionName: String)


    @Query("SELECT * FROM go_permission_data")
    fun findAllTarget(): List<Permission>
}