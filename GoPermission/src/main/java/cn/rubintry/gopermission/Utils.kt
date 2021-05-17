package cn.rubintry.gopermission


import android.app.Application
import android.content.Context

class Utils {


    companion object{
        private var app: Application ?= null
        @JvmStatic
        fun init(app: Application){
            this.app = app
            ActivityMonitor.getInstance().unRegister(app)
            ActivityMonitor.getInstance().register(app)
        }

        @JvmStatic
        fun getApplicationContext():Context{
            if(app == null){
                throw IllegalArgumentException("Context must not be null")
            }
            return app?.applicationContext!!
        }
    }
}