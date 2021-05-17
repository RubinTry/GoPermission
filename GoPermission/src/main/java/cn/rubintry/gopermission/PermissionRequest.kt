package cn.rubintry.gopermission

import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class PermissionRequest(private val callback: Callback, private val permissions: Array<String>) {

    fun getCallback(): Callback {
        return callback
    }

    fun request() = runBlocking {
        measureMethodTime {
            async {
                val notGrantPermission = mutableListOf<String>()
                val grantPermission = mutableListOf<String>()
                //将传入的权限分为已授予和未授予两类
                permissions.forEach { p ->
                    if (ActivityCompat.checkSelfPermission(
                            Utils.getApplicationContext(),
                            p
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        notGrantPermission.add(p)
                    } else {
                        grantPermission.add(p)
                    }
                }

                //若未授予的权限不为空，则先去请求权限，否则直接回调onGrant
                if (notGrantPermission.isNotEmpty()) {
                    requestPermission()
                } else {
                    callback.onAllGrant()
                }
            }
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
            existFragment.requestNow(permissions , this)
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