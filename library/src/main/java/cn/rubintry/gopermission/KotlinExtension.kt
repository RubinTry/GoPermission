package cn.rubintry.gopermission


/**
 * 判断字符串是否为空格
 */
fun String.isSpace(): Boolean {
    val s = this ?: return true
    var i = 0
    val len = s.length
    while (i < len) {
        if (!Character.isWhitespace(s[i])) {
            return false
        }
        ++i
    }
    return true
}


/**
 * 判断某个集合是否不包含某元素
 */
fun <E> List<E>.notContains(e: E): Boolean {
    return !this.contains(e)
}