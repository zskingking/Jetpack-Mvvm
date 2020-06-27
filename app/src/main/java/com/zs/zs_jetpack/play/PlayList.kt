package com.zs.zs_jetpack.play

import android.content.Context
import android.util.Log
import com.zs.base_library.common.getRandom
import com.zs.base_library.common.isListEmpty
import com.zs.base_library.common.toast
import com.zs.zs_jetpack.play.bean.AudioBean

/**
 * des 播放列表
 * @author zs
 * @data 2020/6/25
 */
class PlayList constructor(context: Context) {

    /**
     * 播放列表
     */
    private var audioList = mutableListOf<AudioBean>()

    /**
     * 播放模式，默认为顺序播放
     */
    private var playMode = PlayMode.ORDER_PLAY_MODE

    init {
        //读取播放列表
        readPlayList(context, audioList)
    }

    /**
     * 当前正在播放的音频 默认为null
     */
    private var currentAudio: AudioBean? = null

    /**
     * 当前正在播放的音频
     */
    fun currentAudio(): AudioBean? {
        return currentAudio
    }

    /**
     * 第一次进入播放的音频，默认为播放列表的第一个
     */
    fun startAudio(): AudioBean? {
        if (!isListEmpty(audioList)) {
            currentAudio = audioList[0]
        }
        return currentAudio
    }

    /**
     * 下一个音频
     */
    fun nextAudio(): AudioBean? {
        val index = getIndex()
        if (!isListEmpty(audioList)) {
            when(playMode){
                //顺序
                PlayMode.ORDER_PLAY_MODE->{
                    currentAudio = if (index < audioList.size - 1) {
                        audioList[index + 1]
                    } else {
                        audioList[0]
                    }
                }
                //单曲(不做处理)
                PlayMode.SINGLE_PLAY_MODE->{ }
                //随机
                PlayMode.RANDOM_PLAY_MODE->{
                    currentAudio = audioList[getRandom(0,audioList.size-1)]
                }
            }

        }
        return currentAudio
    }

    /**
     * 上一个音频
     */
    fun previousAudio(): AudioBean? {
        val index = getIndex()
        if (!isListEmpty(audioList)) {
            when(playMode){
                //顺序
                PlayMode.ORDER_PLAY_MODE->{
                    currentAudio = if (index > 0) {
                        audioList[index - 1]
                    } else {
                        audioList[audioList.size-1]
                    }
                }
                //单曲(不做处理)
                PlayMode.SINGLE_PLAY_MODE->{ }
                //随机
                PlayMode.RANDOM_PLAY_MODE->{
                    currentAudio = audioList[getRandom(0,audioList.size-1)]
                }
            }
        }
        return currentAudio
    }

    /**
     * 切换播放模式
     * 顺序播放点击 会切换至单曲
     * 单曲播放点击 会切换至随机
     * 随机播放点击 会切换至顺序
     */
    fun switchPlayMode(): Int {
        when (playMode) {
            PlayMode.ORDER_PLAY_MODE -> {
                toast("单曲循环")
                playMode = PlayMode.SINGLE_PLAY_MODE
            }
            PlayMode.SINGLE_PLAY_MODE -> {
                toast("随机播放")
                playMode = PlayMode.RANDOM_PLAY_MODE
            }
            PlayMode.RANDOM_PLAY_MODE -> {
                toast("列表循环")
                playMode = PlayMode.ORDER_PLAY_MODE
            }
        }
        return playMode
    }

    /**
     * 获取当前播放模式
     */
    fun getCurrentMode(): Int{
        return playMode
    }

    /**
     * 重置，将当前播放重置为null
     */
    fun clear() {
        currentAudio = null
    }

    /**
     * 通过currentAudio获取所在的index
     * 之所以没有全局开放一个index,是为了尽可能的降低 index 的操作权限
     */
    private fun getIndex(): Int {
        for (index in 0 until audioList.size) {
            if (currentAudio == audioList[index]) {
                return index
            }
        }
        //默认返回0
        return 0
    }

    /**
     * 获取播放列表长度
     */
    fun getPlayListSize(): Int {
        return audioList.size
    }

    /**
     * 获取播放列表长度
     */
    fun getPlayList(): MutableList<AudioBean> {
        return audioList
    }


    class PlayMode {
        companion object {

            /**
             * 顺序播放-默认
             */
            const val ORDER_PLAY_MODE = 9

            /**
             * 单曲播放
             */
            const val SINGLE_PLAY_MODE = 99

            /**
             * 随机播放
             */
            const val RANDOM_PLAY_MODE = 999
        }
    }
}