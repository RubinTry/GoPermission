package cn.rubintry.gopermission

import android.content.pm.ApplicationInfo

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