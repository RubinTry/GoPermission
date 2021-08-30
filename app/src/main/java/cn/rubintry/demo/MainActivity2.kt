package cn.rubintry.demo

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import cn.rubintry.gopermission.GoPermission
import cn.rubintry.gopermission.LogUtils

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        GoPermission
            .permissions(*arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .request { allGrant, grantedPermissions , deniedPermissions ->
                if (allGrant) {
                    Toast.makeText(this, "All permissions granted", Toast.LENGTH_SHORT).show()
                    LogUtils.debug("allGrant: $allGrant")
                } else {
                    Toast.makeText(this, "您拒绝了一些权限，将导致部分功能无法正常使用", Toast.LENGTH_SHORT).show()
                    for (deniedPermission in deniedPermissions) {
                        LogUtils.error("被拒绝的权限有: $deniedPermission")
                    }
                }
            }
    }
}