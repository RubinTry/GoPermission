package cn.rubintry.gopermission.core

import androidx.appcompat.app.AlertDialog
import cn.rubintry.gopermission.ext.getCancelListener

class AlertDialogHooker {



    companion object{

        /**
         * 如方法名所示，hook掉dialog已经赋过值的[android.content.DialogInterface.OnCancelListener]，并将外部赋值
         * 的[android.content.DialogInterface.OnCancelListener]里面原有的逻辑整合进我们自己的[android.content.DialogInterface.OnCancelListener]中
         *
         * @param dialog
         * @param onCancel
         */
        @JvmStatic
        fun hookCancelListener(dialog : AlertDialog?, onCancel : () -> Unit){
            dialog?.let { it ->
                //获取dialog中已有的cancelListener，然后在hook时我们再调用一次，原因：重新对dialog
                // 调用setOnCancelListener后，原有的OnCancelListener会被替换掉，具体请自己看dialog源码
                val cachedDialogCancelListener = it.getCancelListener()
                it.setOnCancelListener {
                    cachedDialogCancelListener?.onCancel(it)
                    onCancel.invoke()
                }
                if(!it.isShowing){
                    it.show()
                }
            }
        }
    }
}


