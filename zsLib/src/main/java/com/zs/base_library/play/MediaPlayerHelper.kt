package com.zs.base_library.play

import android.media.MediaPlayer
import android.media.MediaPlayer.*
import android.util.Log
import com.zs.base_library.common.toast

/**
 * des 基于MediaPlayer实现的音频播放
 * @author zs
 * @data 2020/6/25
 */
class MediaPlayerHelper : IPlayer,
    OnCompletionListener,
    OnBufferingUpdateListener,
    OnErrorListener,
    OnPreparedListener{

    private val mediaPlayer by lazy { MediaPlayer() }
    private var iPlayStatus: IPlayerStatus? = null

    init {
        //播放完成监听
        mediaPlayer.setOnCompletionListener(this)
        //缓冲更新监听
        mediaPlayer.setOnBufferingUpdateListener(this)
        //错误监听
        mediaPlayer.setOnErrorListener(this)
        //播放器准备完成监听
        mediaPlayer.setOnPreparedListener(this)
    }

    override fun setPlayStatus(iPlayStatus: IPlayerStatus) {
        this.iPlayStatus = iPlayStatus
    }

    override fun play(path: String) {
        mediaPlayer.reset()
        //可能会抛FileNotFound异常
        kotlin.runCatching {
            mediaPlayer.setDataSource(path)
        }.onSuccess {
            mediaPlayer.prepare()
        }.onFailure {
            Log.i("error","it:${it.printStackTrace()}")
            toast("无效文件")
        }
    }

    override fun resume() {
        mediaPlayer.start()
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun stop() {
        mediaPlayer.stop()
    }

    override fun seekTo(duration: Int) {
        mediaPlayer.seekTo(duration)
    }

    override fun reset() {
        mediaPlayer.reset()
    }

    override fun release() {
        mediaPlayer.release()
    }

    /**
     * 获取是否正在播放
     */
    override fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }

    /**
     * 获取当前播放进度
     */
    override fun getProgress(): Int {
        return mediaPlayer.currentPosition
    }

    /**
     * 播放完成
     */
    override fun onCompletion(mp: MediaPlayer?) {
        iPlayStatus?.onComplete()
    }

    /**
     * 缓冲更新
     */
    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
        iPlayStatus?.onBufferingUpdate(percent)

    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return true
    }

    /**
     * mediaPlayer准备完毕直接播放
     */
    override fun onPrepared(mp: MediaPlayer?) {
        mediaPlayer.start()
    }

}