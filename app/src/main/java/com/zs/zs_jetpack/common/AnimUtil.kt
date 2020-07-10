package com.zs.zs_jetpack.common

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator

/**
 * des 重复旋转动画
 * @author zs
 * @data 2020/7/10
 */
object AnimUtil {


    /**
     * 获取重复旋转动画
     * @param duration 一圈的时间
     */
    @JvmStatic
    fun getRepeatRotate(view: View, duration:Int):ValueAnimator{
        val anim = ValueAnimator.ofFloat(0f,360f)
        anim.duration = duration.toLong()
        //无限循环
        anim.repeatCount = 100000
        //匀速插值器
        anim.interpolator = LinearInterpolator()
        anim.addUpdateListener {
            view.rotation = it.animatedValue as Float
        }
        return anim
    }
}