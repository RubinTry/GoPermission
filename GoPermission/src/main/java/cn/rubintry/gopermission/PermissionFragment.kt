package cn.rubintry.gopermission

import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class PermissionFragment : Fragment() {
    private var mPermissions: Array<String>? = null
    private var callback: Callback? = null
    private var mutex = Mutex()
    private var index = 0
    fun requestNow(permissions: Array<String>, callback: Callback?) = runBlocking {
        this@PermissionFragment.mPermissions = permissions
        this@PermissionFragment.callback = callback
        mutex.withLock {
            if(permissions.isNotEmpty()){
                requestPermissions(permissions , 1)
            }
            index++
            Log.d("TAG", "requestNow: ${index}")
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            val deniedPermission = mutableListOf<String>()
            if (permissions.isNotEmpty()) {
                //将收到的结果分成已授予和未授予两类
                var allGranted = true
                permissions.forEach { permission ->

                    if (ActivityCompat.checkSelfPermission(
                            activity!!,
                            permission
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        allGranted = false
                        deniedPermission.add(permission)
                    }
                }
                if (allGranted) {
                    callback?.onAllGrant()
                }
                if (deniedPermission.isNotEmpty()) {
                    callback?.onDenied(deniedPermission.toTypedArray())
                }
            }
        }

    }

}