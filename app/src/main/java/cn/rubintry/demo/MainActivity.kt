package cn.rubintry.demo

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Toast
import cn.rubintry.gopermission.Callback
import cn.rubintry.gopermission.GoPermission
import cn.rubintry.gopermission.LogUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this , MainActivity2::class.java))
    }

}