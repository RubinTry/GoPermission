package cn.rubintry.gopermission.core

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import cn.rubintry.gopermission.db.Permission
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.text.SimpleDateFormat
import java.util.*

class PermissionFragment : Fragment() {
    private var launcher: ActivityResultLauncher<Array<String>> ?= null
    private var callback: Callback? = null
    private var permissionSize: Int = 0
    private var mutex = Mutex()


    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launcher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val deniedPermission = mutableListOf<String>()
            val grantedPermission = mutableListOf<String>()
            if (it.isNotEmpty()) {
                var allGranted = false
                var grantedCount = 0
                CoroutineScope(Dispatchers.IO).launch {
                    for (permission in it.keys) {
                        //将收到的结果分成已授予和未授予两类
                        if (GoPermission.isGranted(permission)) {
                            grantedCount ++
                            grantedPermission.add(permission)
                        } else {
                            deniedPermission.add(permission)
                        }
                        try {
                            val requestTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
                            DbManager.getInstance().db.permissionDao?.insertPermission(Permission(permission , requestTime , !GoPermission.isGranted(permission)))
                        }catch (e: Exception){
                            e.printStackTrace()
                        }
                    }

                    allGranted = grantedCount == permissionSize
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.onResult(
                            allGranted,
                            grantedPermission.toTypedArray(),
                            deniedPermission.toTypedArray()
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        launcher?.unregister()
    }

    /**
     * 立即请求权限
     *
     * @param permissions 要请求的权限
     * @param callback
     */
    fun requestNow(permissions: Array<String>, callback: Callback?) = runBlocking {
        this@PermissionFragment.callback = callback
        this@PermissionFragment.permissionSize = permissions.size
        mutex.withLock {
            if(permissions.isNotEmpty()){
                launcher?.launch(permissions)
            }
        }
    }


}