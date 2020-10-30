package com.zs.zs_jetpack

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.zs.base_library.base.BaseVmActivity
import com.zs.base_library.common.stringForTime
import com.zs.base_library.utils.PrefUtils
import com.zs.base_library.utils.StatusUtils
import com.zs.zs_jetpack.constants.Constants
import com.zs.zs_jetpack.db.AppDataBase
import com.zs.zs_jetpack.play.AudioObserver
import com.zs.zs_jetpack.play.PlayList
import com.zs.zs_jetpack.play.PlayerManager
import com.zs.zs_jetpack.play.bean.AudioBean
import com.zs.zs_jetpack.ui.MainFragment
import com.zs.zs_jetpack.ui.play.collect.CollectAudioBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * des 主页面，作用有二
 *     1.用于承载Fragment
 *     2.作为音频播放观察者,接受到通知立即更新viewModel内状态
 *       间接通过DataBinding更新View
 *
 * @author zs
 * @date 2020-05-12
 */
class MainActivity : BaseVmActivity(), AudioObserver {

    private var playVM: PlayViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        changeTheme()
        super.onCreate(savedInstanceState)
    }

    override fun initViewModel() {
        playVM = getActivityViewModel(PlayViewModel::class.java)
    }

    override fun init(savedInstanceState: Bundle?) {
        PlayerManager.instance.register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        PlayerManager.instance.unregister(this)
    }

    override fun getLayoutId() = R.layout.activity_main

    /**
     * 歌曲信息
     */
    override fun onAudioBean(audioBean: AudioBean) {
        playVM?.songName?.set(audioBean.name)
        playVM?.singer?.set(audioBean.singer)
        playVM?.maxDuration?.set(stringForTime(audioBean.duration))
        playVM?.maxProgress?.set(audioBean.duration)
        playVM?.albumPic?.set(audioBean.albumId)
        lifecycleScope.launch {
            val bean = withContext(Dispatchers.IO) {
                AppDataBase.getInstance()
                    .collectDao()
                    .findAudioById(audioBean.id)
            }
            playVM?.collect?.set(bean != null)
        }
    }

    /**
     * 播放状态-暂停/播放
     */
    override fun onPlayStatus(playStatus: Int) {
        playVM?.playStatus?.set(playStatus)
    }

    /**
     * 当前播放进度
     */
    override fun onProgress(currentDuration: Int, totalDuration: Int) {
        playVM?.currentDuration?.set(stringForTime(currentDuration))
        playVM?.playProgress?.set(currentDuration)
    }

    /**
     * 播放模式
     */
    override fun onPlayMode(playMode: Int) {
        when (playMode) {
            PlayList.PlayMode.ORDER_PLAY_MODE -> playVM?.playModePic?.set(R.mipmap.play_order)
            PlayList.PlayMode.SINGLE_PLAY_MODE -> playVM?.playModePic?.set(R.mipmap.play_single)
            PlayList.PlayMode.RANDOM_PLAY_MODE -> playVM?.playModePic?.set(R.mipmap.play_random)
        }
    }

    /**
     * 重置
     */
    override fun onReset() {
        playVM?.reset()
    }

    /**
     * 动态切换主题
     */
    private fun changeTheme() {
        val theme = PrefUtils.getBoolean(Constants.SP_THEME_KEY, false)
        if (theme) {
            setTheme(R.style.AppTheme_Night)
        } else {
            setTheme(R.style.AppTheme)
        }
    }

    /**
     * 沉浸式状态,随主题改变
     */
    override fun setSystemInvadeBlack() {
        val theme = PrefUtils.getBoolean(Constants.SP_THEME_KEY, false)
        if (theme) {
            StatusUtils.setSystemStatus(this, true, false)
        } else {
            StatusUtils.setSystemStatus(this, true, true)
        }
    }

    override fun onBackPressed() {
        //获取hostFragment
        val mMainNavFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.host_fragment)
        //获取当前所在的fragment
        val fragment =
            mMainNavFragment?.childFragmentManager?.primaryNavigationFragment
        //如果当前处于根fragment即HostFragment
        if (fragment is MainFragment) {
            //Activity退出但不销毁
            moveTaskToBack(false)
        } else {
            super.onBackPressed()
        }
    }

}
