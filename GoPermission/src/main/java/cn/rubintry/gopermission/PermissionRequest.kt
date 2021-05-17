package cn.rubintry.gopermission

import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class PermissionRequest(private val callback: Callback, private val permissions: Array<String>) {


}