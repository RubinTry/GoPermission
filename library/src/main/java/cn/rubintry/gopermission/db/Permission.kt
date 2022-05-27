package cn.rubintry.gopermission.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "go_permission_data" , )
data class Permission(
    @PrimaryKey
    @ColumnInfo(name = "permissionName")
    var permissionName: String,
    @ColumnInfo(name = "requestTimeMills" , defaultValue = "0")
    var requestTimeMills: String ,
    @ColumnInfo(name = "isDenied" , defaultValue = "false")
    var isDenied : Boolean)