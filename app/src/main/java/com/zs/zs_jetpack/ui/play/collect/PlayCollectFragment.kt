package com.zs.zs_jetpack.ui.play.collect

import android.os.Bundle
import androidx.lifecycle.Observer
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.common.clickNoRepeat
import com.zs.zs_jetpack.PlayViewModel
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.databinding.FragmentPlayListCollectBinding
import com.zs.zs_jetpack.play.PlayerManager

/**
 * des 音频收藏
 * @author zs
 * @date 2020/10/29
 */
class PlayCollectFragment : BaseVmFragment<FragmentPlayListCollectBinding>() {

    private val adapter by lazy { CollectAudioAdapter() }
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

    override fun getLayoutId() = R.layout.fragment_play_list_collect

    private fun setPlayList() {
        binding.rvCollectPlayList.adapter = adapter
    }

    private fun click() {
        binding.llPlayMode.clickNoRepeat {
            PlayerManager.instance.switchPlayMode()
        }
    }

}