package com.zs.zs_jetpack.ui.play.local

import android.util.Log
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.play.PlayList
import com.zs.zs_jetpack.play.PlayListType
import com.zs.zs_jetpack.play.PlayerManager
import com.zs.zs_jetpack.play.bean.AudioBean

/**
 * des 音频列表适配器
 * @author zs
 * @data 2020/6/27
 */
class LocalAudioAdapter : BaseQuickAdapter<AudioBean, BaseViewHolder>(R.layout.item_audio) {

    /**
     * 当前正在播放的角标
     */
    private var currentPosition = getCurrentPlayPosition()

    init {
        setNewData(PlayList.instance.localList)
        setOnItemClickListener { _, _, position ->
            PlayerManager.instance.play(data[position])
        }
    }

    private fun getCurrentPlayPosition(): Int {
        PlayerManager.instance.getPlayList().apply {
            for (position in 0 until size) {
                if (this[position].id == PlayerManager.instance.getCurrentAudioBean()?.id) {
                    return position
                }
            }
        }
        return 0
    }

    override fun convert(helper: BaseViewHolder, item: AudioBean) {
        val current = PlayerManager.instance.getCurrentAudioBean()
        //为null 默认状态
        if (current == null){
            helper.getView<View>(R.id.tvSongName).isSelected = false
            helper.getView<View>(R.id.tvSinger).isSelected = false
            helper.setGone(R.id.ivPlaying, false)
        }else {
            //当前 正在播放 && 是本地列表
            if (item.id == current.id && current.playListType == PlayListType.LOCAL_PLAY_LIST) {
                helper.getView<View>(R.id.tvSongName).isSelected = true
                helper.getView<View>(R.id.tvSinger).isSelected = true
                helper.setGone(R.id.ivPlaying, true)
            } else {
                helper.getView<View>(R.id.tvSongName).isSelected = false
                helper.getView<View>(R.id.tvSinger).isSelected = false
                helper.setGone(R.id.ivPlaying, false)
            }
        }
        helper.setText(R.id.tvSongName, item.name)
        helper.setText(R.id.tvSinger, "-${item.singer}")
    }

    /**
     * 更新当前播放的item,更换播放时由观察者触发
     */
    fun updateCurrentPlaying() {
        setNewData(PlayList.instance.localList)
//        //正在播放的角标
//        val playPosition = getCurrentPlayPosition()
//        //更新当前播放
//        notifyItemChanged(playPosition)
//        //第一次进入两个角标相等
//        if (playPosition != currentPosition) {
//            //此时currentPosition已经是上一次播放位置
//            notifyItemChanged(currentPosition)
//        }
//        //将currentPosition重新置为当前播放
//        currentPosition = playPosition
    }
}