package com.zs.base_library.common

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions

/**
 * des 图片加载扩展方法
 * @author zs
 * @data 2020/6/26
 */


/**
 * 通过url加载
 */
fun ImageView.loadUrl(context: Context,url:String){
    Glide.with(context)
        .load(url)
        .transition(withCrossFade(200))
        .into(this)
}

/**
 * 通过uri加载
 */
fun ImageView.loadUri(context: Context,uri:Uri){
    Glide.with(context)
        .load(uri)
        .transition(withCrossFade(200))
        .into(this)
}

/**
 * 高斯模糊加渐入渐出
 */
fun ImageView.loadBlurTrans(context: Context,uri:Uri,radius:Int){
    Glide.with(context)
        .load(uri)
        .thumbnail(0.1f).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .apply(RequestOptions.bitmapTransform(CenterBlurTransformation(radius, 8,context)))
        .transition(withCrossFade(400))
        .into(this)
}


/**
 * 圆形图片
 */
fun ImageView.loadCircle(context: Context,uri:Uri){
    Glide.with(context)
        .load(uri)
        .apply(RequestOptions.bitmapTransform(CircleCrop()))
        .into(this)
}

