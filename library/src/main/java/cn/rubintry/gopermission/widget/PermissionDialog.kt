package cn.rubintry.gopermission.widget

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import cn.rubintry.gopermission.PermissionDialogListener
import cn.rubintry.gopermission.db.Permission


/**
 * 权限描述弹窗基类
 *
 * @constructor
 *
 * @param context
 * @param cancelable
 * @param cancelListener
 * @param permissionListener
 */
abstract class PermissionDialog(
    context: Context,
    cancelable: Boolean = false,
    cancelListener: DialogInterface.OnCancelListener?,
    permissionListener: PermissionDialogListener?
) : AlertDialog(context, cancelable, cancelListener) , IPermissionDialogInterface,
    View.OnClickListener {

    private var mPermissions: MutableList<Permission> = mutableListOf()
    private var positiveButton: View ?= null
    private var negativeButton: View ?= null

    private var permissionDialogListener : PermissionDialogListener ?= null

    init {
        this.permissionDialogListener = permissionListener
    }

    override fun setPermissionDialogListener(listener: PermissionDialogListener){
        this.permissionDialogListener = listener
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutImpl())
        positiveButton = findViewById(getPositiveButtonId())
        negativeButton = findViewById(getNegativeButtonId())
        positiveButton?.setOnClickListener(this)
        negativeButton?.setOnClickListener(this)

        onViewCreated()
    }

    abstract fun onViewCreated()

    override fun onClick(v: View?) {
        when(v?.id){
            getPositiveButtonId() -> {
                permissionDialogListener?.onGranted()
                this.cancel()
            }

            getNegativeButtonId() -> {
                permissionDialogListener?.onDenied()
                this.cancel()
            }
        }
    }

    override fun isShow(): Boolean {
        return isShowing
    }

    override fun show() {
        super.show()
    }


    override fun setPermissions(permissions: MutableList<Permission>) {
        this.mPermissions = permissions
    }

    override fun getPermissions(): List<Permission> {
        return mPermissions
    }
}