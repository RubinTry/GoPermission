package cn.rubintry.gopermission.utils

import android.content.pm.ApplicationInfo
import cn.rubintry.gopermission.isSpace

object AppUtils {


    @JvmStatic
    fun isAppDebug(packageName: String): Boolean {
        if (packageName.isSpace()) {
            return false
        }

        val pm = Utils.getApp().packageManager
        val ai = pm.getApplicationInfo(packageName, 0)

        return ai.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    }

    @JvmStatic
    fun isAppDebug(): Boolean {
        return isAppDebug(Utils.getApp().packageName)
    }


}