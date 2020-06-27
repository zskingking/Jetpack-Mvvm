package com.zs.zs_jetpack.ui.play

import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.play.PlayerManager
import com.zs.zs_jetpack.play.bean.AudioBean
import java.text.FieldPosition

/**
 * des 音频列表适配器
 * @author zs
 * @data 2020/6/27
 */
class AudioAdapter : BaseQuickAdapter<AudioBean, BaseViewHolder>(R.layout.item_audio) {

    /**
     * 当前正在播放的角标
     */
    private var currentPosition = getCurrentPlayPosition()

    init {
        setNewInstance(PlayerManager.instance.getPlayList())
        setOnItemClickListener { _, _, position ->
            PlayerManager.instance.play(data[position])
            updateCurrentPlaying(position)
        }
    }

    private fun getCurrentPlayPosition():Int{
        PlayerManager.instance.getPlayList().apply {
            for (position in 0 until size){
                if (this[position].id == PlayerManager.instance.getCurrentAudioBean()?.id){
                    return position
                }
            }
        }
        return 0
    }

    override fun convert(holder: BaseViewHolder, item: AudioBean) {
        //当前正在播放

        if (item.id == PlayerManager.instance.getCurrentAudioBean()?.id) {
            holder.setTextColor(R.id.tvSongName, ContextCompat.getColor(context,R.color.theme))
            holder.setTextColor(R.id.tvSinger, ContextCompat.getColor(context,R.color.theme))
            holder.setGone(R.id.ivPlaying, false)
        } else {
            holder.setGone(R.id.ivPlaying, true)
            holder.setTextColor(R.id.tvSongName, ContextCompat.getColor(context,R.color.text_1))
            holder.setTextColor(R.id.tvSinger, ContextCompat.getColor(context,R.color.text_3))
        }
        holder.setText(R.id.tvSongName, item.name)
        holder.setText(R.id.tvSinger, "-${item.singer}")
    }

    /**
     * 更新当前播放的item
     */
    private fun updateCurrentPlaying(position: Int){
        //更新当前播放
        notifyItemChanged(position)
        //此时currentPosition已经是上一次播放位置
        currentPosition?.let { notifyItemChanged(it) }
        //将currentPosition重新置为当前播放
        currentPosition = position
    }
}