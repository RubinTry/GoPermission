package cn.rubintry.gopermission.db

import android.database.sqlite.SQLiteException
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


/**
 * 数据库迁移类
 *
 * @constructor
 *
 * @param startVersion
 * @param endVersion
 */
class PermissionMigration(startVersion: Int, endVersion: Int) : Migration(startVersion, endVersion) {
    override fun migrate(database: SupportSQLiteDatabase) {
        try {
            database.execSQL("DROP TABLE 'Permission'")
        }catch (e : SQLiteException){
            e.printStackTrace()
        }
        database.execSQL("CREATE TABLE 'Permission'('permissionName' TEXT NOT NULL , `grantedOnDialog` INTEGER NOT NULL, PRIMARY KEY(`permissionName`))")
    }
}