package cn.rubintry.gopermission.utils

import android.app.Application
import cn.rubintry.gopermission.core.ActivityMonitor

class Utils {


    companion object{
        private var app: Application ?= null
        @JvmStatic
        fun init(app: Application){
            Companion.app = app
            ActivityMonitor.getInstance().unRegister(app)
            ActivityMonitor.getInstance().register(app)
        }

        @JvmStatic
        fun getApp():Application{
            if(app == null){
                throw IllegalArgumentException("Context must not be null")
            }
            return app!!
        }
    }
}