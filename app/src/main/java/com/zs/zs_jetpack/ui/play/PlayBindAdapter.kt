package com.zs.zs_jetpack.ui.play

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.zs.base_library.common.albumById
import com.zs.base_library.common.loadBlurTrans
import com.zs.zs_jetpack.R

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


}