package com.zs.zs_jetpack.play.bean

/**
 * des 进度模型
 * author zs
 * date 2021/4/20
 */
data class ProgressBean(
    /**
     * 当前时间
     */
    var currentDuration: Int = 0,

    /**
     * 总时间
     */
    var totalDuration: Int = 0
)