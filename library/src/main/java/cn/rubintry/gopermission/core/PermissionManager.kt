package cn.rubintry.gopermission.core

import androidx.room.AutoMigration
import androidx.room.Room
import androidx.room.migration.AutoMigrationSpec
import cn.rubintry.gopermission.db.PermissionDatabase
import cn.rubintry.gopermission.db.PermissionMigration
import cn.rubintry.gopermission.utils.Utils


/**
 * 权限缓存管理类
 *
 */
internal class PermissionManager {
    //下面注释表示允许主线程进行数据库操作，但是不推荐这样做。
    //他可能造成主线程lock以及anr
    //所以我们的操作都是在新线程完成的
    val db: PermissionDatabase by lazy {
        Room.databaseBuilder(
            Utils.getApp(),
            PermissionDatabase::class.java,
            "go-permission-database"
        ) //下面注释表示允许主线程进行数据库操作，但是不推荐这样做。
            //他可能造成主线程lock以及anr
            //所以我们的操作都是在新线程完成的
            .allowMainThreadQueries()
            .addMigrations(PermissionMigration(1 , 2))
            .build()
    }

    companion object{
        @Volatile
        private var instance: PermissionManager?= null

        @JvmStatic
        fun getInstance(): PermissionManager{
            if(null == instance){
                synchronized(PermissionManager::class.java){
                    if(null == instance){
                        instance = PermissionManager()
                    }
                }
            }
            return instance!!
        }
    }


}