package cn.rubintry.gopermission

interface PermissionDialogListener {

    /**
     * 用户点击"授予"或者类似名称的按钮时回调这个方法
     *
     */
    fun onGranted()


    /**
     * 用户点击"拒绝"或者类似名称的按钮时回调这个方法
     *
     */
    fun onDenied()
}