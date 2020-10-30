package com.zs.zs_jetpack.ui.play.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zs.base_library.common.clickNoRepeat
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.play.AudioObserver
import com.zs.zs_jetpack.play.PlayList
import com.zs.zs_jetpack.play.PlayerManager
import com.zs.zs_jetpack.play.bean.AudioBean
import kotlinx.android.synthetic.main.fragment_play_list_local.*

/**
 * des
 * @author zs
 * @date 2020/10/30
 */
class PlayLocalFragment : Fragment(), AudioObserver {

    private val adapter by lazy { LocalAudioAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play_list_local, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PlayerManager.instance.register(this)
        click()
        tvListSize.text = String.format("(%s)", PlayerManager.instance.getPlayListSize())
        setPlayList()
    }

    private fun setPlayList() {
        rvLocalPlayList.adapter = adapter
    }

    private fun click() {
        llPlayMode.clickNoRepeat {
            PlayerManager.instance.switchPlayMode()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        PlayerManager.instance.unregister(this)
    }

    override fun onAudioBean(audioBean: AudioBean) {
        adapter.updateCurrentPlaying()
    }

    override fun onPlayMode(playMode: Int) {
        when (playMode) {
            PlayList.PlayMode.ORDER_PLAY_MODE -> {
                ivPlayMode.setImageResource(R.mipmap.play_order_gray)
                tvPlayMode.text = "列表循环"
            }
            PlayList.PlayMode.SINGLE_PLAY_MODE -> {
                ivPlayMode.setImageResource(R.mipmap.play_single_gray)
                tvPlayMode.text = "单曲循环"
            }
            PlayList.PlayMode.RANDOM_PLAY_MODE -> {
                ivPlayMode.setImageResource(R.mipmap.play_random_gray)
                tvPlayMode.text = "随机播放"
            }
        }
    }

    override fun onReset() {
    }

    override fun onResume() {
        super.onResume()
        //rvLocalPlayList.isNestedScrollingEnabled = false
    }
}