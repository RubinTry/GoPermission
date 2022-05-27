package cn.rubintry.gopermission.core.request

import cn.rubintry.gopermission.core.BeforeRequest
import cn.rubintry.gopermission.core.Callback


/**
 * 权限请求接口
 *
 */
interface IRequest {

    /**
     * 请求前示意弹窗，如：[各项权限的描述]、[用意]等
     *
     * @param before 在此接口中声明好弹窗并return
     * @return 返回当前请求
     */
    fun beforeRequest(before: BeforeRequest) : IRequest


    /**
     * 发起权限请求
     *
     * @param callback
     */
    fun request(callback: Callback)
}