package cn.rubintry.gopermission

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.*


class ActivityMonitor {

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

    private val linkedList = LinkedList<Activity>()

    private val callback = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            if(linkedList.notContains(activity)){
                linkedList.add(activity)
            }

            LogUtils.debug("onActivityCreated : ${activity.javaClass.simpleName}")

        }

        override fun onActivityStarted(activity: Activity) {

            LogUtils.debug("onActivityStarted : ${activity.javaClass.simpleName}")
        }

        override fun onActivityResumed(activity: Activity) {
            LogUtils.debug("onActivityResumed : ${activity.javaClass.simpleName}")
        }

        override fun onActivityPaused(activity: Activity) {
            LogUtils.debug("onActivityPaused : ${activity.javaClass.simpleName}")
        }

        override fun onActivityStopped(activity: Activity) {
            LogUtils.debug("onActivityStopped : ${activity.javaClass.simpleName}")
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityDestroyed(activity: Activity) {
            LogUtils.debug("onActivityDestroyed : ${activity.javaClass.simpleName}")
            if(linkedList.contains(activity)){
                linkedList.remove(activity)
            }
        }

    }

    fun register(app: Application) {
        app.registerActivityLifecycleCallbacks(callback)
    }

    fun unRegister(app: Application) {
        app.unregisterActivityLifecycleCallbacks(callback)
    }


    /**
     * 获取栈顶activity
     */
    fun getTopActivity(): Activity?{
        return linkedList.lastOrNull()
    }
}