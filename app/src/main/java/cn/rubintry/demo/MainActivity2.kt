package cn.rubintry.demo

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import cn.rubintry.gopermission.PermissionDialogListener
import cn.rubintry.gopermission.core.GoPermission
import cn.rubintry.gopermission.utils.LogUtils


class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        GoPermission
            .permissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .beforeRequest {
                DemoDialog.Builder(this).create()
            }
            .request { allGrant, grantedPermissions , deniedPermissions ->
                if (allGrant) {
                    Toast.makeText(this, "All permissions granted", Toast.LENGTH_SHORT).show()
                    LogUtils.debug("allGrant: $allGrant")
                } else {
                    for (deniedPermission in deniedPermissions) {
                        LogUtils.error("被拒绝的权限有: $deniedPermission")
                    }
                }
            }
    }
}