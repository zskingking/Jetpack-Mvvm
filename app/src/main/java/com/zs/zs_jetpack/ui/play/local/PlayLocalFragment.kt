package com.zs.zs_jetpack.ui.play.local

import android.os.Bundle
import androidx.lifecycle.Observer
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.common.clickNoRepeat
import com.zs.zs_jetpack.PlayViewModel
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.databinding.FragmentPlayListLocalBinding
import com.zs.zs_jetpack.play.PlayerManager

/**
 * des
 * @author zs
 * @date 2020/10/30
 */
class PlayLocalFragment: BaseVmFragment<FragmentPlayListLocalBinding>() {

    private val adapter by lazy { LocalAudioAdapter() }
    private var playVM: PlayViewModel? = null

    override fun initViewModel() {
        playVM = getActivityViewModel(PlayViewModel::class.java)
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = playVM
        click()
        binding.tvListSize.text = String.format("(%s)", PlayerManager.instance.getPlayListSize())
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

    override fun getLayoutId() = R.layout.fragment_play_list_local

    private fun setPlayList() {
        binding.rvLocalPlayList.adapter = adapter
    }

    private fun click() {
        binding.llPlayMode.clickNoRepeat {
            PlayerManager.instance.switchPlayMode()
        }
    }
}