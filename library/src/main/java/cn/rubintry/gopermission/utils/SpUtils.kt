package cn.rubintry.gopermission.utils

import android.content.Context
import android.content.SharedPreferences


class SpUtils {
    private val GO_PERMISSION_SP = "GO_PERMISSION_SP"

    private var sp: SharedPreferences ?= null

    companion object{
        @Volatile
        private var instance : SpUtils ?= null

        @JvmStatic
        fun getInstance() : SpUtils{
            if(null == instance){
                synchronized(SpUtils::class.java){
                    if(null == instance){
                        instance = SpUtils()
                    }
                }
            }
            return instance!!
        }
    }

    private fun getSp(): SharedPreferences {
        if(null == sp){
            sp = Utils.getApp().getSharedPreferences(GO_PERMISSION_SP , Context.MODE_PRIVATE)
        }
        return sp!!
    }

    fun put(key: String , value: String){
        getSp().edit().putString(key , value).apply()
    }

    fun getString(key: String): String?{
        return getSp().getString(key , "")
    }


}