package cn.rubintry.demo

import android.content.Context
import cn.rubintry.gopermission.PermissionDialogListener
import cn.rubintry.gopermission.widget.PermissionDialog
import java.lang.ref.WeakReference

class DemoDialog private constructor(context: Context, listener: PermissionDialogListener?) : PermissionDialog(context , false , {} , listener) {
    private constructor(context: Context): this(context , null)

    constructor(builder: Builder) : this(builder.getContext())

    override fun onViewCreated() {

    }

    override fun getLayoutImpl(): Int {
        return R.layout.dialog_custom
    }

    override fun getPositiveButtonId(): Int {
        return R.id.tv_grant
    }

    override fun getNegativeButtonId(): Int {
        return R.id.tv_denied
    }

    class Builder(context: Context){
        private val weakContext = WeakReference(context)

        fun getContext(): Context {
            return weakContext.get()!!
        }

        fun create(): DemoDialog{
            return DemoDialog(this)
        }
    }


}