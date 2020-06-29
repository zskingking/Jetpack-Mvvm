package com.zs.base_library.common

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

/**
 * des 自定义DataBinding适配器
 * @author zs
 * @date 2020/6/28
 */
object CustomBindAdapter {



    /**
     * 加载资源图片
     */
    @BindingAdapter(value = ["imgSrc"])
    @JvmStatic
    fun imgSrc(view: ImageView, id: Int) {
        view.setImageResource(id)
    }

    /**
     * 加载图片
     */
    @BindingAdapter(value = ["imageUrl"])
    @JvmStatic
    fun imageUrl(view: ImageView, url: String) {
        view.loadUrl(view.context.applicationContext,url)
    }

    /**
     * 加载图片
     */
    @BindingAdapter(value = ["imgUriCircle"])
    @JvmStatic
    fun imgUriCircle(view: ImageView, albumId: Long) {
        view.loadCircle(view.context.applicationContext, albumById(albumId))
    }


    @BindingAdapter(value = ["visible"])
    @JvmStatic
    fun visible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

}