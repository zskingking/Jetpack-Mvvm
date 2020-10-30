package com.zs.zs_jetpack.ui.play.history

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
class HistoryAudioAdapter : BaseQuickAdapter<AudioBean, BaseViewHolder>(R.layout.item_audio) {

    init {
        setData(PlayList.instance.historyList)
        setOnItemClickListener { _, _, position ->
            PlayerManager.instance.play(data[position])
        }
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
            if (item.id == current.id && current.playListType == PlayListType.HISTORY_PLAY_LIST) {
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

    private fun setData(data: List<AudioBean>){
        setNewData(mutableListOf<AudioBean>().apply {
            addAll(data)
            reverse()
        })
    }

    /**
     * 更新当前播放的item,更换播放时由观察者触发
     */
    fun updateCurrentPlaying() {
        setData(PlayList.instance.historyList)
    }
}