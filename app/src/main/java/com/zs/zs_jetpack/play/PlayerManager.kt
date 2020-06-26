package com.zs.zs_jetpack.play

import android.content.Context
import android.util.Log
import com.zs.base_library.play.IPlayer
import com.zs.base_library.play.IPlayerStatus
import com.zs.base_library.play.MediaPlayerHelper
import com.zs.zs_jetpack.play.bean.AudioBean

/**
 * des 音频管理
 *     通过单例模式实现,托管音频状态与信息,并且作为唯一的可信源
 *     通过观察者模式(一对多,严格来说是发布-订阅)统一对状态进行分发
 *     实则是一个代理,将目标对象Player与调用者隔离,并且在内部实现了对观察者的注册与通知
 * @author zs
 * @data 2020/6/25
 */
class PlayerManager private constructor() : IPlayerStatus {

    /**
     * 单例创建PlayerManager
     */
    companion object {
        val instance: PlayerManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            PlayerManager()
        }
    }

    /**
     * 音乐观察者集合,目前有三个
     * 1.播放界面
     * 2.悬浮窗
     * 3.通知栏
     */
    private val observers = mutableListOf<AudioObserver>()

    private val playerHelper: IPlayer = MediaPlayerHelper()

    /**
     * 当前播放的对象
     */
    private var currentAudioBean: AudioBean? = null


    /**
     * 播放列表
     */
    private lateinit var playList: PlayList

    fun init(context: Context) {
        playList = PlayList(context)
        playerHelper.setPlayStatus(this)
    }

    /**
     * 第一次进入,播放器未被初始化,默认模仿第一个
     */
    fun start() {

        playList.startAudio()?.let {
            play(it)
        }
    }

    /**
     * 播放一个新的音频
     */
    fun play(audioBean: AudioBean) {
        audioBean.path?.let { playerHelper.play(it) }
        sendAudioToObserver(audioBean)
    }

    /**
     * 从暂停切换为播放
     */
    fun resume() {
        playerHelper.resume()
        sendPlayingToObserver(true)
    }

    /**
     * 从播放切换为暂停
     */
    fun pause() {
        playerHelper.pause()
        sendPlayingToObserver(false)
    }

    /**
     * 跳转至指定播放位置
     */
    fun seekTo(duration: Int) {
        playerHelper.seekTo(duration)
    }

    /**
     * 跳转至指定播放位置
     */
    fun switchPlayMode() {
        sendPlayModeToObserver(playList.switchPlayMode())
    }

    /**
     * 获取当前正在播放的音频信息
     */
    fun getCurrentAudioBean(): AudioBean? {
        return currentAudioBean
    }

    /**
     * 重置并释放播放器
     */
    fun clear() {
        playerHelper.reset()
        playerHelper.release()
        playList.clear()
    }

    /**
     * 注册观察者
     */
    fun register(audioObserver: AudioObserver) {
        observers.add(audioObserver)
    }

    /**
     * 解除观察者
     */
    fun unregister(audioObserver: AudioObserver) {
        observers.remove(audioObserver)
    }

    /**
     * 给观察者发送音乐信息
     */
    private fun sendAudioToObserver(audioBean: AudioBean) {
        observers.forEach {
            it.onAudioBean(audioBean)
        }
    }

    /**
     * 给观察者发送播放状态
     */
    private fun sendPlayingToObserver(isPlaying: Boolean) {
        observers.forEach {
            it.onPlaying(isPlaying)
        }
    }

    /**
     * 给观察者发送进度
     */
    private fun sendProgressToObserver(duration: Int) {
        observers.forEach {
            it.onProgress(duration)
        }
    }

    /**
     * 给观察者发送播放模式
     */
    private fun sendPlayModeToObserver(playMode: Int) {
        observers.forEach {
            it.onPlayMode(playList.switchPlayMode())
        }
    }

    override fun onBufferingUpdate(percent: Int) {

    }

    override fun onComplete() {
        playList.nextAudio()?.let { play(it) }
    }

}