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
     */
    fun onAudioBean(audioBean: AudioBean)

    /**
     * 播放状态-暂停/播放
     */
    fun onPlaying(playing:Boolean)

    /**
     * 当前播放进度
     */
    fun onProgress(currentDuration:Int)

    /**
     * 播放模式
     */
    fun onPlayMode(playMode:Int)


}