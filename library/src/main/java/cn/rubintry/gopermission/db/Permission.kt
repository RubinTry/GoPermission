package cn.rubintry.gopermission.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "go_permission_data")
data class Permission(@PrimaryKey val permissionName: String, var requestCount: Int, var grantedOnDialog: Boolean)