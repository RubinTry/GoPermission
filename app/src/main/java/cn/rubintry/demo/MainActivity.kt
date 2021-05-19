package cn.rubintry.demo

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import cn.rubintry.gopermission.Callback
import cn.rubintry.gopermission.GoPermission

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GoPermission
                .permissions(*arrayOf(Manifest.permission.CAMERA))
                .request { allGrant, grantedPermissions, deniedPermissions ->
                    Log.d("TAG", "onGrant: ${allGrant}")
                    startActivity(Intent(this@MainActivity , SecondActivity::class.java))
                }
    }

    fun requestPermission(view: View) {

    }
}