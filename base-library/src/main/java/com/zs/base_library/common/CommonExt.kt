package com.zs.base_library.common

import java.util.*

/**
 * 通用扩展方法
 * @date 2020/5/28
 * @author zs
 */


/**
 * 当集合为空或者长度为0
 */
fun isListEmpty(list: List<Any>?):Boolean = list==null|| list.isEmpty()


/**
 * 将毫秒转换为分秒-00:00格式
 */
fun stringForTime(timeMs:Int):String{
    val totalSeconds = timeMs/1000
    val seconds = totalSeconds % 60
    val minutes = (totalSeconds/60)%60

    return Formatter().format("%02d:%02d",minutes,seconds).toString();
}

fun getRandom(start:Int,end:Int):Int{
    return ((start+Math.random()*(end-start+1)).toInt())
}
