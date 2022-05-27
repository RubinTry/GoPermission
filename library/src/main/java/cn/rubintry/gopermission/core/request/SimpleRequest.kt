package cn.rubintry.gopermission.core.request

import androidx.fragment.app.FragmentActivity
import cn.rubintry.gopermission.core.*
import cn.rubintry.gopermission.core.DialogHooker
import cn.rubintry.gopermission.ext.checkNotEmpty
import cn.rubintry.gopermission.ext.toPermissionList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference


/**
 * 权限请求
 * 该类的方法注释都在父接口中，请移步[IRequest]查看
 * @constructor
 * @param permissions
 */
class SimpleRequest(vararg permissions: String) : IRequest {
    private var before: WeakReference<BeforeRequest> ?= null
    private val permissionList = mutableListOf<String>()

    init {
        permissions.forEach { checkNotEmpty(it) }
        permissionList.addAll(permissions)
    }

    /**
     * 该类的方法注释都在父接口中，请移步[IRequest]查看
     *
     * @param before
     * @return
     */
    override fun beforeRequest(before: BeforeRequest): IRequest {
        this.before = WeakReference(before)
        return this
    }

    override fun request(callback: Callback) {
        if(permissionList.isEmpty()){
            throw IllegalArgumentException("Please invoke method permissions() first.")
        }
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO){
            requestPermission(callback)
        }
    }
    /**
     * 请求权限
     *
     */
    private fun requestPermission(callback: Callback) {
        val curActivity = ActivityMonitor.getInstance().getTopActivity()
        if (curActivity !is FragmentActivity) {
            throw IllegalArgumentException("Activity must be FragmentActivity")
        }
        //用创建好的全透明无背景的fragment进行权限请求
        val existFragment = curActivity.supportFragmentManager.findFragmentByTag(PermissionFragment::class.java.name)

        if (existFragment != null && existFragment is PermissionFragment) {
            if(permissionList.isEmpty()){
                return
            }
            //有beforeRequestCallback，拉起[权限描述弹窗]
            if(null != before){
                CoroutineScope(Dispatchers.Main).launch{
                    before?.get()?.showDialog()?.setPermissions(permissionList.toPermissionList())
                    DialogHooker.hookCancelListener(WeakReference(before?.get()?.showDialog()), {
                        existFragment.requestNow(permissionList.toTypedArray(), callback)
                    }, {
                        callback.onResult(false , emptyArray() , permissionList.toTypedArray())
                    })
                }
            }else{
                //没有beforeRequestCallback，直接发起请求
                existFragment.requestNow(permissionList.toTypedArray(), callback)
            }
        }
    }




}