package com.zs.zs_jetpack

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.zs.base_library.base.BaseVmActivity
import com.zs.base_library.common.stringForTime
import com.zs.base_library.utils.PrefUtils
import com.zs.base_library.utils.StatusUtils
import com.zs.zs_jetpack.common.BaseLoadingActivity
import com.zs.zs_jetpack.constants.Constants
import com.zs.zs_jetpack.play.PlayList
import com.zs.zs_jetpack.play.PlayerManager
import com.zs.zs_jetpack.ui.MainFragment

/**
 * des 主页面，作用有二
 *     1.用于承载Fragment
 *     2.作为音频播放观察者,接受到通知立即更新viewModel内状态
 *       间接通过DataBinding更新View
 *
 * @author zs
 * @date 2020-05-12
 */
class MainActivity : BaseLoadingActivity() {

    private var playVM: PlayViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        changeTheme()
        super.onCreate(savedInstanceState)
    }

    override fun initViewModel() {
        playVM = getActivityViewModel(PlayViewModel::class.java)
    }

    override fun init2(savedInstanceState: Bundle?) {
        PlayerManager.instance.playLiveData.audioLiveData.observe(this, Observer {
            playVM?.setAudioBean(it)
        })
        PlayerManager.instance.playLiveData.playStatusLiveData.observe(this, Observer {
            playVM?.playStatus?.set(it)
        })
        PlayerManager.instance.playLiveData.progressLiveData.observe(this, Observer {
            playVM?.setProgress(it)
        })
        PlayerManager.instance.playLiveData.playModeLiveData.observe(this, Observer {
            playVM?.setPlayMode(it)
        })
        PlayerManager.instance.playLiveData.resetLiveData.observe(this, Observer {
            playVM?.reset()
        })
    }

    override fun getLayoutId() = R.layout.activity_main

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
