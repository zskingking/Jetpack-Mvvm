package com.zs.base_library.common

import android.content.Context
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
 * 当泛型集合为空或者长度为0
 */
fun<T> isGpListEmpty(list: List<T>?): Boolean = list == null || list.isEmpty()



/**
 * 将毫秒转换为分秒-00:00格式
 */
fun stringForTime(timeMs:Int):String{
    val totalSeconds = timeMs/1000
    val seconds = totalSeconds % 60
    val minutes = (totalSeconds/60)%60

    return Formatter().format("%02d:%02d",minutes,seconds).toString();
}

/**
 * 获取指定范围内的随机数
 */
fun getRandom(start:Int,end:Int):Int{
    return ((start+Math.random()*(end-start+1)).toInt())
}

/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun dip2px(context: Context, dpValue: Float): Int {
    val scale: Float = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}


/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun px2dip(context: Context, dpValue: Float): Int {
    val scale: Float = context.resources.displayMetrics.density
    return (dpValue / scale + 0.5f).toInt()

}
