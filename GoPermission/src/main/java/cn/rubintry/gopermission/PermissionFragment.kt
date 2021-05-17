package cn.rubintry.gopermission

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

class PermissionFragment: Fragment() {
    private var mPermissions : Array<String> ?= null
    private var mPermissionRequest : PermissionRequest ?= null
    fun requestNow(permissions: Array<String>, permissionRequest: PermissionRequest) {
        this.mPermissions = permissions
        this.mPermissionRequest = permissionRequest
        requestPermissions(permissions , 1)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val deniedPermission = mutableListOf<String>()
        if (permissions.isNotEmpty()) {
            //将收到的结果分成已授予和未授予两类
                var allGranted = true
            permissions.forEach { permission ->

                if (ActivityCompat.checkSelfPermission(
                        activity!! ,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    allGranted = false
                    deniedPermission.add(permission)
                }
            }
            if(allGranted){
                mPermissionRequest?.getCallback()?.onAllGrant()
            }
           if(deniedPermission.isNotEmpty()){
               mPermissionRequest?.getCallback()?.onDenied(deniedPermission.toTypedArray())
           }
        }


    }

}