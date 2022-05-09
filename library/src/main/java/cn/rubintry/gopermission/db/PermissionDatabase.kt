package cn.rubintry.gopermission.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import cn.rubintry.gopermission.db.PermissionDao

@Database(entities = [Permission::class], version = 2, exportSchema = true)
abstract class PermissionDatabase : RoomDatabase() {
    abstract val permissionDao: PermissionDao?
}