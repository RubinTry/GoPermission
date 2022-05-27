package cn.rubintry.gopermission.core.request

import android.annotation.SuppressLint
import androidx.fragment.app.FragmentActivity
import cn.rubintry.gopermission.core.*
import cn.rubintry.gopermission.core.DbManager
import cn.rubintry.gopermission.ext.checkNotEmpty
import cn.rubintry.gopermission.ext.toPermissionList
import cn.rubintry.gopermission.utils.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*


/**
 * 带有间隔性的请求
 * @constructor
 * @param permissions 权限组
 * @param hours 时间间隔，单位：小时
 */
class IntervalRequest(vararg permissions: String, private val hours: Int = 48) : IRequest {

    private var before: WeakReference<BeforeRequest>?= null
    private val permissionList = mutableListOf<String>()

    private val INTERVAL_MILLS = hours * 60 * 60 * 1000

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
        CoroutineScope(Dispatchers.IO).launch{
            requestPermission(callback)
        }
    }

    private fun requestPermission(callback: Callback) {
        val curActivity = ActivityMonitor.getInstance().getTopActivity()
        if (curActivity !is FragmentActivity) {
            throw IllegalArgumentException("Activity must be FragmentActivity")
        }
        //用创建好的全透明无背景的fragment进行权限请求
        val existFragment = curActivity.supportFragmentManager.findFragmentByTag(PermissionFragment::class.java.name)

        if (existFragment != null && existFragment is PermissionFragment) {
            realPermissionRequest(existFragment , callback)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun realPermissionRequest(existFragment: PermissionFragment, callback: Callback) {
        val realRequestPermissions = mutableListOf<String>()
        if(permissionList.isEmpty()){
            return
        }
        /**
         * 在所请求的权限组内找出被拒绝的权限，并筛选出[hours]以外的权限进行再次请求
         */
        val cachePermissions = DbManager.getInstance().db.permissionDao?.findAllTarget()
        if(cachePermissions.isNullOrEmpty()){
            realRequestPermissions.addAll(permissionList)
        }else{
            for (permission in cachePermissions) {
                if(permission.isDenied){
                    try {
                        val lastRequestTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(permission.requestTimeMills)
                        if(Date().time - lastRequestTime!!.time < INTERVAL_MILLS){
                            LogUtils.error("由于权限${permission.permissionName}在48小时内被拒绝过，不可再次申请")
                            LogUtils.error("上一次申请时间为：${permission.requestTimeMills}")
                            continue
                        }
                        realRequestPermissions.add(permission.permissionName)
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }
        }
        if(realRequestPermissions.isEmpty()){
            return
        }
        //有beforeRequestCallback，拉起[权限描述弹窗]
        if(null != before){
            CoroutineScope(Dispatchers.Main).launch{
                before?.get()?.showDialog()?.setPermissions(realRequestPermissions.toPermissionList())
                DialogHooker.hookCancelListener(WeakReference(before?.get()?.showDialog()), {
                    existFragment.requestNow(realRequestPermissions.toTypedArray(), callback)
                }, {
                    callback.onResult(false , emptyArray() , realRequestPermissions.toTypedArray())
                })
            }
        }else{
            //没有beforeRequestCallback，直接发起请求
            existFragment.requestNow(realRequestPermissions.toTypedArray(), callback)
        }
    }
}