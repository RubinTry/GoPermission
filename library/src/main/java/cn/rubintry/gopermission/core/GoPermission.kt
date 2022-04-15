package cn.rubintry.gopermission.core

import android.content.pm.PackageManager

import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import cn.rubintry.gopermission.utils.Utils

object GoPermission {
    private  var beforeRequestCallback: BeforeRequestCallback ?= null
    private var permission: MutableList<String> = mutableListOf()

    private var isPermissionsInvoke = false

    @JvmStatic
    fun initialize() {
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


    /**
     * 要请求的权限组（看方法名也该知道了）
     *
     * @param permissions
     * @return
     */
    @JvmStatic
    fun permissions(vararg permissions: String): GoPermission {
        isPermissionsInvoke = true
        for (permission in permissions) {
            if (permission.isBlank()) {
                throw IllegalArgumentException("Permission should not be empty!!!")
            }
        }
        permission.clear()
        permission.addAll(permissions)
        return this
    }


    /**
     * 权限是否只申请一次
     *
     */
    fun onlyOne(){

    }

    /**
     * 判断权限是否已经授予
     *
     * @param permission
     * @return
     */
    @JvmStatic
    fun isGranted(permission: String): Boolean {
        check(isPermissionsInvoke){"Please invoke method permissions() first."}
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
        if(null != beforeRequestCallback){
            //hook一下，以便在我们自定义的弹窗取消后再发起请求
            AlertDialogHooker.hookCancelListener(beforeRequestCallback?.onBefore()) {
                //hook成功后我们再发起请求
                requestPermission(callback)
            }
        }else{
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
            existFragment.requestNow(permission.toTypedArray(), callback)
        }
    }


    /**
     * 请求权限之前进行的弹窗提示，用于告知用户权限的具体用途（别说没必要，现在好多机构都在查）
     *
     * @param beforeRequestCallback
     * @return
     */
    fun beforeRequest(beforeRequestCallback : BeforeRequestCallback): GoPermission {
        this.beforeRequestCallback = beforeRequestCallback
        return this
    }



}