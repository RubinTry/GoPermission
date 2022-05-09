package cn.rubintry.gopermission.widget

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import cn.rubintry.gopermission.PermissionDialogListener
import cn.rubintry.gopermission.db.Permission


/**
 * 权限弹窗统一接口
 *
 */
interface IPermissionDialogInterface {

    /**
     * 设置弹窗回调
     *
     * @param listener
     */
    fun setPermissionDialogListener(listener: PermissionDialogListener)


    /**
     * 获取待请求权限
     *
     * @return
     */
    fun getPermissions(): List<Permission>


    /**
     * 设置待请求权限
     *
     * @return
     */
    fun setPermissions(permissions: MutableList<Permission>)


    /***
     * 布局实现，这里返回布局id
     */
    @LayoutRes
    fun getLayoutImpl(): Int


    /**
     * 返回一个"授予"按钮的id
     *
     * @return
     */
    @IdRes
    fun getPositiveButtonId(): Int


    /**
     * 返回一个"拒绝"按钮的id
     *
     * @return
     */
    @IdRes
    fun getNegativeButtonId(): Int

    /**
     * 是否正在展示
     *
     * @return
     */
    fun isShow(): Boolean

    fun show()
}