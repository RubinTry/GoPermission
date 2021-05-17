package cn.rubintry.demo

import android.Manifest
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

    }

    fun requestPermission(view: View) {
//        for (index in 0 until 10000) {
//            print("MainActivity-----")
//            GoPermission.permissions(*arrayOf(Manifest.permission.CALL_PHONE))
//                .callback(object :
//                    Callback {
//                    override fun onAllGrant() {
//
//                    }
//
//                    override fun onDenied(permissions: Array<String>) {
//                        for (permission in permissions) {
//                            Log.d(this@MainActivity.javaClass.simpleName, "onDenied: ${permission}")
//                        }
//                        val intent = Intent(this@MainActivity , SecondActivity::class.java)
//                        startActivity(intent)
//                    }
//
//                })
//                .request()
//        }

        GoPermission
            .permissions(*arrayOf(Manifest.permission.CAMERA))
            .callback(object : Callback {
                override fun onAllGrant() {
                    Log.d("TAG", "onAllGrant: ")
                }

                override fun onDenied(permissions: Array<String>) {
                    Log.d("TAG", "onDenied: ")
                }

            })
            .request()
    }
}