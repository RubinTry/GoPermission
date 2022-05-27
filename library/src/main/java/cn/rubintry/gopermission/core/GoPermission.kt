package cn.rubintry.gopermission.core

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import cn.rubintry.gopermission.core.request.IRequest
import cn.rubintry.gopermission.core.request.IntervalRequest
import cn.rubintry.gopermission.core.request.SimpleRequest
import cn.rubintry.gopermission.utils.Utils


class GoPermission private constructor(){


    companion object {

        private var builder : GoPermissionBuilder ?= null

        init {
            builder = GoPermissionBuilder()
        }


        /**
         * 初始化，该方法会自动调用，无需关心
         */
        internal fun initialize() {
            val curActivity = ActivityMonitor.getInstance().getTopActivity()
            checkNotNull(curActivity) { "Top Activity is null" }
            if (curActivity.javaClass.name.contains(Utils.getApp().packageName) && curActivity is FragmentActivity) {

                //用栈顶activity来创建一个可复用的fragment
                if (curActivity.supportFragmentManager.findFragmentByTag(PermissionFragment::class.java.name) == null) {
                    val fragment = PermissionFragment()
                    curActivity.supportFragmentManager.beginTransaction()
                        .add(fragment, PermissionFragment::class.java.name).attach(fragment)
                        .commit()
                }
            }
        }


        /**
         * 判断权限是否已经授予
         *
         * @param permission
         * @return
         */
        @JvmStatic
        fun isGranted(permission: String): Boolean {
            return ContextCompat.checkSelfPermission(
                Utils.getApp(),
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }


        /**
         * 是否需要在规定时间（默认48小时，时间可自定义，参考[intervalHours]参数）内无法再次发起请求
         *
         * @param intervalMode
         */
        @JvmStatic
        fun isIntervalMode(intervalMode: Boolean): GoPermissionBuilder {
            this.builder?.intervalMode = intervalMode
            return builder!!
        }


        /**
         * 要请求的权限组（看方法名也该知道了）
         *
         * @param permissions
         * @param intervalHours 时间间隔
         * @return
         */
        @JvmStatic
        fun permissions(vararg permission: String): IRequest {
            return builder?.permissions(*permission , intervalHours = null)!!
        }

    }















}