package cn.rubintry.gopermission.utils

import android.util.Log


object LogUtils {

    private val TAG= "LogUtils"

    @JvmStatic
    fun debug(msg: String){
        if(AppUtils.isAppDebug()){
            Log.d(TAG, msg)
        }
    }


    @JvmStatic
    fun error(msg: String){
        if(AppUtils.isAppDebug()){
            Log.e(TAG, msg)
        }
    }


    @JvmStatic
    fun warn(msg: String){
        if(AppUtils.isAppDebug()){
            Log.w(TAG, msg)
        }
    }


    @JvmStatic
    fun info(msg: String){
        if(AppUtils.isAppDebug()){
            Log.i(TAG, msg)
        }
    }
}