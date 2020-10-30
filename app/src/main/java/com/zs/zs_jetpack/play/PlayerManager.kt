package com.zs.zs_jetpack.play

import android.content.Context
import com.zs.base_library.play.IPlayer
import com.zs.base_library.play.IPlayerStatus
import com.zs.base_library.play.MediaPlayerHelper
import com.zs.zs_jetpack.play.bean.AudioBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * des 音频管理
 *     通过单例模式实现,托管音频状态与信息,并且作为唯一的可信源
 *     通过观察者模式统一对状态进行分发
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

        //播放器状态,当前共4种,可在此处随时扩展
        /**
         * 重置
         */
        const val RELEASE = 100

        /**
         * 从头开始播放
         */
        const val START = 200

        /**
         * 播放
         */
        const val RESUME = 300

        /**
         * 暂停
         */
        const val PAUSE = 400
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
     * 用于关闭rxJava
     */
    private var disposable: Disposable? = null


    /**
     * 播放状态，默认为重置
     */
    private var playStatus = RELEASE
    /**
     * 播放列表
     */
    private lateinit var playList: PlayList

    fun init(context: Context) {
        playList = PlayList.instance
        playerHelper.setPlayStatus(this)
        startTimer()
    }

    /**
     * 开启定时器,用于更新进度
     * 每1000毫秒更新一次
     */
    private fun startTimer() {
        disposable = Observable.interval(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                //仅在播放状态下通知观察者
                if (playerHelper.isPlaying()) {
                    sendProgressToObserver(playerHelper.getProgress())
                }
            }
    }

    /**
     * 与播放按钮相对应,点击播放按钮存在三种场景
     * 1.播放器还未被初始化: 播放第一首音频,与start()对应
     * 2.暂停状态: 切换为播放状态,与resume()对应
     * 3.播放状态: 切换为暂停状态,与pause()对应
     *
     * 此方法意图是将所有播放逻辑全部控制在内部,视图层不做任何逻辑处理,只专注于事件监听与ui渲染
     */
    fun controlPlay() {
        //对应场景1
        if (playList.currentAudio() == null) {
            start()
        } else {
            //对应场景3
            if (playerHelper.isPlaying()) {
                pause()
            }
            //对应场景2
            else {
                resume()
            }
        }
    }

    /**
     * 播放上一首
     */
    fun previous() {
        play(playList.previousAudio())
    }

    /**
     * 播放下一首
     */
    fun next() {
        play(playList.nextAudio())
    }

    /**
     * 第一次进入,播放器未被初始化,默认模仿第一个
     */
    private fun start() {
        play(playList.startAudio())
    }

    /**
     * 播放一个新的音频
     */
    fun play(audioBean: AudioBean?) {
        if (audioBean == null){
            //重置
            playerHelper.reset()
            sendResetToObserver()
        }else{
            playStatus = START
            playList.setCurrentAudio(audioBean)
            audioBean.path?.let { playerHelper.play(it) }
            sendAudioToObserver(audioBean)
            sendPlayStatusToObserver()
        }

    }

    /**
     * 从暂停切换为播放
     */
    private fun resume() {
        playStatus = RESUME
        playerHelper.resume()
        sendPlayStatusToObserver()
    }

    /**
     * 从播放切换为暂停
     */
    private fun pause() {
        playStatus = PAUSE
        playerHelper.pause()
        sendPlayStatusToObserver()
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
        return playList.currentAudio()
    }

    /**
     * 获取当前正在播放的音频信息
     */
    fun getPlayListSize(): Int? {
        return playList.getPlayListSize()
    }

    /**
     * 获取播放列表
     */
    fun getPlayList(): MutableList<AudioBean> {
        return playList.getPlayList()
    }

    /**
     * 重置并释放播放器
     */
    fun clear() {
        disposable?.dispose()
        playList.clear()
        playerHelper.reset()
        playerHelper.release()
        playList.clear()
    }

    /**
     * 注册观察者
     */
    fun register(audioObserver: AudioObserver) {
        observers.add(audioObserver)
        //TODO 注册时手动更新观察者,相当于粘性通知
        notifyObserver(audioObserver)
    }

    /**
     * 解除观察者
     */
    fun unregister(audioObserver: AudioObserver) {
        observers.remove(audioObserver)
    }

    /**
     * 手动更新观察者
     */
    private fun notifyObserver(audioObserver: AudioObserver) {
        playList.currentAudio()?.let { audioObserver.onAudioBean(it) }
        audioObserver.onPlayMode(playList.getCurrentMode())
        audioObserver.onPlayStatus(playStatus)
        playList.currentAudio()?.duration?.let {
            audioObserver.onProgress(
                playerHelper.getProgress(),
                it
            )
        }
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
    private fun sendPlayStatusToObserver() {
        observers.forEach {
            it.onPlayStatus(playStatus)
        }
    }

    /**
     * 给观察者发送进度
     */
    private fun sendProgressToObserver(duration: Int) {
        observers.forEach {
            playList.currentAudio()?.duration?.let { it1 -> it.onProgress(duration, it1) }
        }
    }

    /**
     * 给观察者发送播放模式
     */
    private fun sendPlayModeToObserver(playMode: Int) {
        observers.forEach {
            it.onPlayMode(playMode)
        }
    }

    /**
     * 给观察者发送播放状态
     */
    private fun sendResetToObserver() {
        observers.forEach {
            it.onReset()
        }
    }

    override fun onBufferingUpdate(percent: Int) {

    }

    override fun onComplete() {
        playList.nextAudio()?.let { play(it) }
    }

}