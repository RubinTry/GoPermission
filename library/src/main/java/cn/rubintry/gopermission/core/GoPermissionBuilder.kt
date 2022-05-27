package cn.rubintry.gopermission.core

import cn.rubintry.gopermission.core.request.IRequest
import cn.rubintry.gopermission.core.request.IntervalRequest
import cn.rubintry.gopermission.core.request.SimpleRequest


/**
 * GoPermission构造器
 *
 */
class GoPermissionBuilder {
    var intervalMode = false

    /**
     * 要请求的权限组（看方法名也该知道了）
     *
     * @param permissions
     * @param intervalHours 时间间隔
     * @return
     */
    fun permissions(vararg permission: String): IRequest {
        return permissions(*permission , intervalHours = null)
    }

    /**
     * 要请求的权限组（看方法名也该知道了）
     *
     * @param permissions
     * @param intervalHours 时间间隔
     * @return
     */
    fun permissions(vararg permission: String , intervalHours: Int?): IRequest {
        return if (intervalMode){
            if (null != intervalHours) {
                IntervalRequest(*permission, hours = intervalHours)
            } else {
                IntervalRequest(*permission)
            }
        } else {
            SimpleRequest(*permission)
        }
    }
}