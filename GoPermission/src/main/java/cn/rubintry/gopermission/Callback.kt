package cn.rubintry.gopermission


interface Callback {
     fun onAllGrant()


    fun onDenied(permissions: Array<String>)
}