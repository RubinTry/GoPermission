package cn.rubintry.gopermission.ext

import java.lang.reflect.Method


/**
 * 获取对象中的某字段
 *
 * @param T
 * @param fieldName
 * @return
 */
inline fun <reified T> Any.getField(fieldName: String) : T? {
    val clazz = this.javaClass
    return getFieldFromClass(clazz , fieldName , this) as? T
}


/**
 * 递归获取声明的字段
 *
 * @param clazz 类
 * @param fieldName 字段名
 * @param obj 字段所在的对象实例
 * @return
 */
fun getFieldFromClass(clazz: Class<*>? , fieldName: String , obj: Any) : Any?{
    try {
        val field = clazz?.getDeclaredField(fieldName)
        field?.isAccessible = true
        return field?.get(obj)
    }catch (e: Exception){
        if(null != clazz?.superclass){
            return getFieldFromClass(clazz.superclass , fieldName , obj)
        }
        return null
    }
}


/**
 * 获取对象中声明的方法
 *
 * @param methodName
 * @return
 */
fun Any.getDeclaredMethod(methodName: String): Method?{
    val clazz = this.javaClass
    return getMethodFromClass(clazz , methodName , this)
}

fun getMethodFromClass(clazz: Class<*>?, methodName: String, obj: Any) : Method?{
    return try {
        val method = clazz?.getDeclaredMethod(methodName)
        method?.isAccessible = true
        method
    }catch (e: NoSuchMethodException){
        if(null != clazz?.superclass){
            getMethodFromClass(clazz.superclass, methodName , obj)
        }else{
            null
        }
    }
}