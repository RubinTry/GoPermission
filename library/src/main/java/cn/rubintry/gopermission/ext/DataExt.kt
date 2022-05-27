package cn.rubintry.gopermission.ext

import cn.rubintry.gopermission.db.Permission
import kotlin.contracts.contract

fun checkNotEmpty(value: String): Boolean {
    if(value.isBlank()){
        throw IllegalArgumentException("String $${value} is empty!!")
    }
    return true
}

/**
 * 权限转换
 *
 * @return
 */
internal fun MutableList<String>.toPermissionList(): MutableList<Permission>{
    val permissionList = mutableListOf<Permission>()
    for (p in this) {
        permissionList.add(Permission(p , "0" ,false))
    }
    return permissionList
}