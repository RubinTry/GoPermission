package cn.rubintry.gopermission


import android.app.Application

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
        fun getApp():Application{
            if(app == null){
                throw IllegalArgumentException("Context must not be null")
            }
            return app!!
        }
    }
}