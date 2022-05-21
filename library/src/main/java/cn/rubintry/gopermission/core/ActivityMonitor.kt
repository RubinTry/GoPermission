package cn.rubintry.gopermission.core

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import cn.rubintry.gopermission.notContains
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
                for (ac in linkedList) {
                    if(ac is FragmentActivity){
                        val fragment = ac.supportFragmentManager.findFragmentByTag(PermissionFragment::class.java.name)
                        fragment?.let {
                            ac.supportFragmentManager.beginTransaction().remove(it)
                        }
                    }
                }
                linkedList.add(activity)
            }
            GoPermission.initialize()

        }

        override fun onActivityStarted(activity: Activity) {

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


    fun getBottomActivity(): Activity?{
        return linkedList.firstOrNull()
    }
}