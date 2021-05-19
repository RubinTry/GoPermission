package cn.rubintry.gopermission

import android.app.Activity
import android.app.Application
import android.os.Bundle

class ActivityMonitor {
    private val TAG = this.javaClass.simpleName
    companion object {
        @Volatile
        private var instance: ActivityMonitor? = null


        @JvmStatic
        fun getInstance(): ActivityMonitor {
            if (null == instance) {
                synchronized(ActivityMonitor::class.java) {
                    if (null == instance) {
                        instance = ActivityMonitor()
                    }
                }
            }
            return instance!!
        }
    }

    var topActivity: Activity? = null

    private val callback = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            topActivity = activity
            GoPermission.initialize()
        }

        override fun onActivityStarted(activity: Activity) {
            topActivity = activity
            GoPermission.initialize()
        }

        override fun onActivityResumed(activity: Activity) {
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityDestroyed(activity: Activity) {
            
        }

    }

    fun register(app: Application) {
        app.registerActivityLifecycleCallbacks(callback)
    }

    fun unRegister(app: Application) {
        app.unregisterActivityLifecycleCallbacks(callback)
    }
}