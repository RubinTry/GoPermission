package cn.rubintry.gopermission.core

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import cn.rubintry.gopermission.db.Permission
import cn.rubintry.gopermission.utils.LogUtils
import cn.rubintry.gopermission.utils.Utils
import java.lang.ref.WeakReference

object GoPermission {

    private  var beforeRequestCallback: WeakReference<BeforeRequestCallback> ?= null

    private var permission: MutableList<String> = mutableListOf()

    private var isPermissionsInvoke = false

    /**
     * 初始化，该方法会自动调用，无需关心
     */
    @JvmStatic
    internal fun initialize() {
        //只在栈顶的activity中创建PermissionFragment，避免资源浪费
        val curActivity = ActivityMonitor.getInstance().getTopActivity()
        checkNotNull(curActivity) { "Top Activity is null" }
        if(curActivity.javaClass.name.contains(Utils.getApp().packageName) && curActivity is FragmentActivity){

            //用栈顶activity来创建一个可复用的fragment
            if (curActivity.supportFragmentManager.findFragmentByTag(PermissionFragment::class.java.name) == null) {
                val fragment = PermissionFragment()
                curActivity.supportFragmentManager.beginTransaction()
                    .add(fragment, PermissionFragment::class.java.name).attach(fragment).commit()
            }
        }
    }


    internal fun getPermissions(): List<String> {
        return permission
    }


    /**
     * 要请求的权限组（看方法名也该知道了）
     *
     * @param permissions
     * @return
     */
    @JvmStatic
    fun permissions(vararg permissions: String): GoPermission {
        isPermissionsInvoke = true
        permission.clear()
        for (p in permissions) {
            if (p.isBlank()) {
                throw IllegalArgumentException("Permission should not be empty!!!")
            }
            permission.add(p)
        }
        return this
    }


    /**
     * 判断权限是否已经授予
     *
     * @param permission
     * @return
     */
    @JvmStatic
    fun isGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
                Utils.getApp(),
                permission
        ) == PackageManager.PERMISSION_GRANTED
    }


    /**
     * 发起权限请求
     *
     */
    fun request(callback: Callback) {
        check(isPermissionsInvoke){"Please invoke method permissions() first."}
        isPermissionsInvoke = false
        requestPermission(callback)
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
            val permissionList = mutableListOf<String>()
            for (s in permission) {
                val cachedPermission = PermissionManager.getInstance().db.permissionDao?.findPermissionByName(s)
                if(null != cachedPermission){
                    if(cachedPermission.requestCount < 2 && !cachedPermission.grantedOnDialog){
                        cachedPermission.requestCount ++
                        PermissionManager.getInstance().db.permissionDao?.insertPermission(cachedPermission)
                        permissionList.add(cachedPermission.permissionName)
                    }else{
                        LogUtils.error("权限${cachedPermission.permissionName}被拒绝次数已超2次，已中断请求")
                    }
                }else{
                    if(!isGranted(s)){
                        PermissionManager.getInstance().db.permissionDao?.insertPermission(Permission(permissionName = s , requestCount = 1 , false))
                        permissionList.add(s)
                    }
                }
            }
            if(permissionList.isEmpty()){
                return
            }
            if(null != beforeRequestCallback){
                beforeRequestCallback?.get()?.onBefore()?.setPermissions(permission.toPermissionList())
                DialogHooker.hookCancelListener(WeakReference(beforeRequestCallback?.get()?.onBefore()), {
                    existFragment.requestNow(permissionList.toTypedArray(), callback)
                }, {
                    callback.onResult(false , emptyArray() , permissionList.toTypedArray())
                })
            }else{
                existFragment.requestNow(permissionList.toTypedArray(), callback)
            }
        }
    }



    /**
     * 请求权限之前进行的弹窗提示，用于告知用户权限的具体用途（别说没必要，现在好多机构都在查）
     *
     * @param beforeRequestCallback
     * @return
     */
    fun beforeRequest(beforeRequestCallback : BeforeRequestCallback): GoPermission {
        this.beforeRequestCallback = WeakReference(beforeRequestCallback)
        return this
    }


    /**
     * 权限转换
     *
     * @return
     */
    private fun MutableList<String>.toPermissionList(): MutableList<Permission>{
        val permissionList = mutableListOf<Permission>()
        for (p in this) {
            permissionList.add(Permission(p , 0 ,false))
        }
        return permissionList
    }

}