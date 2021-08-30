package cn.rubintry.gopermission.utils

import android.app.Application
import androidx.core.content.FileProvider

class UtilFileProvider : FileProvider() {

    override fun onCreate(): Boolean {
        Utils.init(this.context?.applicationContext!! as Application)
        return true
    }


}