package com.zs.zs_jetpack.ui.play

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.zs.base_library.common.setNoRepeatClick
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.play.AudioObserver
import com.zs.zs_jetpack.play.PlayList
import com.zs.zs_jetpack.play.PlayerManager
import kotlinx.android.synthetic.main.fragment_play_list.*


/**
 * des 播放列表
 * @author zs
 * @data 2020/6/27
 */
class PlayListFragment : DialogFragment(), AudioObserver {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Dialog_FullScreen);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play_list, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PlayerManager.instance.register(this)
        click()
        tvListSize.text = String.format("(%s)",PlayerManager.instance.getPlayListSize())
        setPlayList()
    }

    private fun setPlayList(){
        rvPlayList.adapter = AudioAdapter()
    }

    private fun click() {
        setNoRepeatClick(root,llPlayMode){
            when(it.id){
                R.id.root -> dismiss()
                R.id.llPlayMode -> PlayerManager.instance.switchPlayMode()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        PlayerManager.instance.unregister(this)
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
}