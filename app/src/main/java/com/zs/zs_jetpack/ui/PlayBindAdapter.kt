package com.zs.zs_jetpack.ui

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.zs.base_library.common.albumById
import com.zs.base_library.common.loadBlurTrans
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.view.FloatPlayLayout

/**
 * des 播放dataBinding适配器
 * @date 2020/6/28
 * @author zs
 */
object PlayBindAdapter {


    /**
     * 加载图片,做高斯模糊处理
     */
    @BindingAdapter(value = ["imgPlayBlur"])
    @JvmStatic
    fun imgPlayBlur(view: ImageView, albumId: Long) {
        view.loadBlurTrans(view.context.applicationContext,albumById(albumId), 90)
    }

    /**
     * 处理暂停/播放
     */
    @BindingAdapter(value = ["imgPlay"])
    @JvmStatic
    fun imgPlay(view: ImageView, playing: Boolean) {
       if (playing){
           view.setImageResource(R.mipmap.play_resume)
       }else{
           view.setImageResource(R.mipmap.play_pause)
       }
    }

    /**
     * 悬浮-处理暂停/播放
     */
    @BindingAdapter(value = ["floatImgPlay"])
    @JvmStatic
    fun floatImgPlay(view: FloatPlayLayout, playing: Boolean?) {
        view.setImgPlaying(playing)
    }

    /**
     * 悬浮-歌名
     */
    @BindingAdapter(value = ["floatSongName"])
    @JvmStatic
    fun floatSongName(view: FloatPlayLayout, songName: String?) {
        view.setSongName(songName)
    }

    /**
     * 悬浮-图片
     */
    @BindingAdapter(value = ["floatImgAlbum"])
    @JvmStatic
    fun floatImgAlbum(view: FloatPlayLayout, albumId: Long?) {
        view.setAlbumPic(albumId)
    }

    /**
     * 悬浮-图片
     */
    @BindingAdapter(value = ["rotate"])
    @JvmStatic
    fun rotate(view : View, isPlying:Boolean) {

    }

}