package com.zs.zs_jetpack.play

import com.zs.zs_jetpack.play.bean.AudioBean

/**
 * des 音频播放观察者对象,可实时观察到音频信息、播放状态、播放进度
 * @author zs
 * @data 2020/6/25
 */
interface AudioObserver {

    /**
     * 歌曲信息
     * 空实现,部分界面可不用实现
     */
    fun onAudioBean(audioBean: AudioBean){}

    /**
     * 播放状态-暂停/播放
     * 空实现,部分界面可不用实现
     */
    fun onPlaying(playing:Boolean){}

    /**
     * 当前播放进度
     * 空实现,部分界面可不用实现
     */
    fun onProgress(currentDuration: Int,totalDuration:Int){}

    /**
     * 播放模式
     */
    fun onPlayMode(playMode:Int)


}