package cn.rubintry.gopermission.core

import cn.rubintry.gopermission.PermissionDialogListener
import cn.rubintry.gopermission.ext.getField
import cn.rubintry.gopermission.widget.IPermissionDialogInterface
import cn.rubintry.gopermission.widget.PermissionDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

internal class DialogHooker {



    companion object{

        /**
         * 获取[PermissionDialog]内部已有的[PermissionDialogListener]，在此基础上重新改造
         * @param dialog
         * @param onGranted
         */
        @JvmStatic
        fun hookCancelListener(weakDialog : WeakReference<IPermissionDialogInterface>?, onGranted : () -> Unit , onDenied : () -> Unit){
            val dialog = weakDialog?.get()
            //获取dialog中已有的PermissionDialogListener，然后在hook时我们再调用一次，原因：重新对dialog
            // 调用setPermissionDialogListener后，原有的PermissionDialogListener会被替换掉，具体请自己看PermissionDialog源码
            val cachedDialogCancelListener = dialog?.getField<PermissionDialogListener>("permissionDialogListener")

            dialog?.setPermissionDialogListener(object : PermissionDialogListener{
                override fun onGranted() {
                    dialog.getPermissions().forEach {
                        it.grantedOnDialog = true
                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        PermissionManager.getInstance().db.permissionDao?.insertPermissions(dialog.getPermissions())
                    }
                    cachedDialogCancelListener?.onGranted()
                    onGranted.invoke()
                }

                override fun onDenied() {
                    dialog.getPermissions().forEach {
                        it.grantedOnDialog = false
                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        PermissionManager.getInstance().db.permissionDao?.insertPermissions(dialog.getPermissions())
                    }
                    cachedDialogCancelListener?.onDenied()
                    onDenied.invoke()
                }

            })
            if(dialog?.isShow() == false){
                dialog.show()
            }
        }
    }
}


