package cn.rubintry.demo

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import cn.rubintry.gopermission.Callback
import cn.rubintry.gopermission.GoPermission

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GoPermission
                .permissions(*arrayOf(Manifest.permission.CAMERA))
                .request { allGrant, grantedPermissions, deniedPermissions ->
                    if(allGrant){
                        Toast.makeText(this, "All permissions granted", Toast.LENGTH_SHORT).show()
                        Log.d("TAG", "allGrant: ${allGrant}")
                    }
                }
    }

    fun requestPermission(view: View) {

    }
}