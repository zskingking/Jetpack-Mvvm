package com.zs.zs_jetpack.play

import com.zs.base_library.play.IPlayer
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
class PlayerManager private constructor() {

    /**
     * 单例创建PlayerManager
     */
    companion object {
        val instance: PlayerManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            PlayerManager()
        }
    }

    private val playerHelper: IPlayer = MediaPlayerHelper()

    /**
     * 当前播放的对象
     */
    private var currentAudioBean: AudioBean? = null

    /**
     * 播放一个新的音频
     */
    fun play(audioBean: AudioBean) {
        audioBean.path?.let { playerHelper.play(it) }
    }

    /**
     * 从暂停切换为播放
     */
    fun resume() {
        playerHelper.resume()
    }

    /**
     * 从播放切换为暂停
     */
    fun pause() {
        playerHelper.pause()
    }

    /**
     * 跳转至指定播放位置
     */
    fun seekTo(duration: Int) {
        playerHelper.seekTo(duration)
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
    }

}