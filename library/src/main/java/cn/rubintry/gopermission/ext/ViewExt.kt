package cn.rubintry.gopermission.ext

import android.app.Dialog
import android.content.DialogInterface
import android.os.Message
import androidx.appcompat.app.AlertDialog


internal fun AlertDialog?.hasCancelListener() : Boolean{
    if(this == null){
        return false
    }

    return null != getCancelListener()
}


internal fun Dialog?.getCancelListener() : DialogInterface.OnCancelListener?{
    return try {
        val mCancelMessage = this?.getField<Message>("mCancelMessage")
        val onCancelListener = mCancelMessage?.obj
        onCancelListener as? DialogInterface.OnCancelListener
    }catch (e : Exception){
        null
    }
}