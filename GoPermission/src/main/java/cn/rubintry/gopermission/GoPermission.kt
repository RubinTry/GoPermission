package cn.rubintry.gopermission

import android.util.Log
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

object GoPermission {

    private var permission: MutableList<String> = mutableListOf()
    private var callback : Callback ?= null
    @JvmStatic
    fun initialize() {
        val curActivity = ActivityMonitor.getInstance().topActivity
        if (curActivity !is FragmentActivity) {
            throw IllegalArgumentException("Activity must be FragmentActivity")
        }
        if (curActivity.supportFragmentManager.findFragmentByTag(PermissionFragment::class.java.name) == null) {
            val fragment = PermissionFragment()
            curActivity.supportFragmentManager.beginTransaction()
                .add(fragment, PermissionFragment::class.java.name).attach(fragment).commit()
        }
    }

    @JvmStatic
    fun permissions(vararg permissions: String): GoPermission {
        for (permission in permissions) {
            if (permission.isBlank()) {
                throw IllegalArgumentException("Permission should not empty!!!")
            }
        }
        permission.clear()
        this.permission.addAll(permissions)
        return this
    }

    @JvmStatic
    fun callback(callback: Callback): GoPermission {
        this.callback = callback
        return this
    }


    /**
     * 发起权限请求
     *
     */
    @JvmStatic
    fun request() = runBlocking {
        measureMethodTime {
            requestPermission()
        }
    }



    /**
     * 请求权限
     *
     */
    private fun requestPermission() {
        val curActivity = ActivityMonitor.getInstance().topActivity
        if (curActivity !is FragmentActivity) {
            throw IllegalArgumentException("Activity must be FragmentActivity")
        }

        val existFragment =
            curActivity.supportFragmentManager.findFragmentByTag(PermissionFragment::class.java.name)
        if (existFragment != null && existFragment is PermissionFragment) {
            existFragment.requestNow(permission.toTypedArray() , callback)
        }
    }


    /**
     * 测量方法调用时长
     *
     * @param action
     */
    private suspend fun measureMethodTime(action: suspend () -> Unit) {
        val time = measureTimeMillis {
            coroutineScope {
                launch {
                    action()
                }
            }
        }

        Log.d(this.javaClass.simpleName, "请求耗时${time / 1000f}秒: ")
    }

}