package cn.rubintry.gopermission.core

import android.content.pm.PackageManager

import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import cn.rubintry.gopermission.utils.LogUtils
import cn.rubintry.gopermission.utils.Utils
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

object GoPermission {
    private var permission: MutableList<String> = mutableListOf()

    @JvmStatic
    fun initialize() {
        val curActivity = ActivityMonitor.getInstance().getTopActivity()
        checkNotNull(curActivity) { "Top Activity is null" }
        if(curActivity.javaClass.name.contains(Utils.getApp().packageName)){
            if (curActivity !is FragmentActivity) {
                throw IllegalArgumentException("Activity must be FragmentActivity")
            }

            //用栈顶activity来创建一个可复用的fragment
            if (curActivity.supportFragmentManager.findFragmentByTag(PermissionFragment::class.java.name) == null) {
                val fragment = PermissionFragment()
                curActivity.supportFragmentManager.beginTransaction()
                    .add(fragment, PermissionFragment::class.java.name).attach(fragment).commit()
            }
        }
    }

    @JvmStatic
    fun permissions(vararg permissions: String): GoPermission {
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
    fun request(callback: Callback) = runBlocking {
        val time = measureMethodTime {
            requestPermission(callback)
        }

        LogUtils.debug("方法调用耗时:${time / 1000f}秒 ")
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
        var existFragment =
                curActivity.supportFragmentManager.findFragmentByTag(PermissionFragment::class.java.name)


        if (existFragment != null && existFragment is PermissionFragment) {
            existFragment.requestNow(permission.toTypedArray(), callback)
        }
    }


    /**
     * 测量方法耗时
     *
     * @param action
     */
    private suspend fun measureMethodTime(action: suspend () -> Unit): Long {
        return measureTimeMillis {
            coroutineScope {
                launch {
                    action()
                }
            }
        }
    }



}