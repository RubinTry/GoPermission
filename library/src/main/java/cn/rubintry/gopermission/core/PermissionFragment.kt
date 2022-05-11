package cn.rubintry.gopermission.core

import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import cn.rubintry.gopermission.notContains
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class PermissionFragment : Fragment() {
    private var launcher: ActivityResultLauncher<Array<String>> ?= null
    private var callback: Callback? = null
    private var mutex = Mutex()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launcher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val deniedPermission = mutableListOf<String>()
            val grantedPermission = mutableListOf<String>()
            if (it.isNotEmpty()) {
                var allGranted = false
                var grantedCount = 0
                for (permission in it.keys) {
                    //将收到的结果分成已授予和未授予两类
                    if (GoPermission.isGranted(permission)) {
                        grantedCount ++
                        grantedPermission.add(permission)
                    } else {
                        deniedPermission.add(permission)
                    }
                }

                CoroutineScope(Dispatchers.IO).launch {
                    val cachedPermissions = PermissionManager.getInstance().db.permissionDao?.findAllTarget()
                    cachedPermissions?.let {
                        for (permission in it) {
                            if(deniedPermission.notContains(permission.permissionName)){
                                if(permission.grantedOnDialog){
                                    permission.grantedOnDialog = false
                                    PermissionManager.getInstance().db.permissionDao?.insertPermission(permission)
                                }
                                deniedPermission.add(permission.permissionName)
                            }
                        }
                    }
                    allGranted = grantedCount == GoPermission.getPermissions().size
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
        mutex.withLock {
            if(permissions.isNotEmpty()){
                launcher?.launch(permissions)
            }
        }
    }


}