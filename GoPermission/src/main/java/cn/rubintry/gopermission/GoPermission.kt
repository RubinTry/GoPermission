package cn.rubintry.gopermission

import androidx.fragment.app.FragmentActivity

object GoPermission {

    private var permission: MutableList<String> = mutableListOf()


    @JvmStatic
    fun initialize() {
        val curActivity = ActivityMonitor.getInstance().topActivity
        if (curActivity !is FragmentActivity) {
            throw IllegalArgumentException("Activity must be FragmentActivity")
        }
        if (curActivity.supportFragmentManager.findFragmentByTag(PermissionFragment::class.java.name) == null) {
            val fragment = PermissionFragment()
            curActivity.supportFragmentManager.beginTransaction()
                .add(fragment, PermissionFragment::class.java.name).attach(fragment).commit()
        }
    }

    @JvmStatic
    fun permissions(vararg permissions: String): GoPermission {
        for (permission in permissions) {
            if (permission.isBlank()) {
                throw IllegalArgumentException("Permission should not empty!!!")
            }
        }
        permission.clear()
        this.permission.addAll(permissions)
        return this
    }

    @JvmStatic
    fun callback(callback: Callback): PermissionRequest {
        return PermissionRequest(callback , permission.toTypedArray())
    }




}