package com.zs.zs_jetpack.ui.play.collect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.common.clickNoRepeat
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.PlayViewModel
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.play.AudioObserver
import com.zs.zs_jetpack.play.PlayList
import com.zs.zs_jetpack.play.PlayerManager
import com.zs.zs_jetpack.play.bean.AudioBean
import com.zs.zs_jetpack.ui.play.history.HistoryAudioAdapter
import kotlinx.android.synthetic.main.fragment_play_list_collect.*

/**
 * des
 * @author zs
 * @date 2020/10/29
 */
class PlayCollectFragment : BaseVmFragment() {

    private val adapter by lazy { CollectAudioAdapter() }
    private var playVM: PlayViewModel? = null

    override fun initViewModel() {
        playVM = getActivityViewModel(PlayViewModel::class.java)
    }

    override fun init(savedInstanceState: Bundle?) {
        click()
        tvListSize.text = String.format("(%s)", PlayerManager.instance.getPlayListSize())
        setPlayList()
    }

    override fun observe() {
        PlayerManager.instance.playLiveData.audioLiveData.observe(this, Observer {
            adapter.updateCurrentPlaying()
        })
        PlayerManager.instance.playLiveData.playModeLiveData.observe(this, Observer {
            playVM?.setPlayMode(it)
        })
    }

    override fun getLayoutId() = R.layout.fragment_play_list_collect
    override fun getDataBindingConfig(): DataBindingConfig =
        DataBindingConfig(R.layout.fragment_player, playVM)
        .addBindingParam(BR.vm, playVM)

    private fun setPlayList() {
        rvCollectPlayList.adapter = adapter
    }

    private fun click() {
        llPlayMode.clickNoRepeat {
            PlayerManager.instance.switchPlayMode()
        }
    }

}