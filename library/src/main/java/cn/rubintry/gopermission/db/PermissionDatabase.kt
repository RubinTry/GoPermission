package cn.rubintry.gopermission.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameTable
import androidx.room.RoomDatabase

@Database(entities = [Permission::class], version = 3, exportSchema = true)
abstract class PermissionDatabase : RoomDatabase() {
    abstract val permissionDao: PermissionDao?


}