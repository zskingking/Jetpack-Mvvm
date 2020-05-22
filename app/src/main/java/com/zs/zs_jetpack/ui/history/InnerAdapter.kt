package com.zs.zs_jetpack.ui.history

import android.graphics.Bitmap
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zs.base_library.utils.ScreenUtils
import com.zs.zs_jetpack.R
import java.security.MessageDigest

/**
 * @date 2020/5/20
 * @author zs
 */
class InnerAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_inner) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.getView<ImageView>(R.id.imageView).apply {
            val ivParams = layoutParams
            //动态设置ImageView宽高
            when (data.size) {
                1 -> {
                    ivParams.width = ScreenUtils.getScreenWidth(context) - 200
                    ivParams.height = 1
                    layoutParams = ivParams
                }
                2 -> {
                    ivParams.height = ScreenUtils.getScreenWidth(context) / 2 - 40
                    ivParams.width = ScreenUtils.getScreenWidth(context) / 2 - 40
                    layoutParams = ivParams
                }
                else -> {
                    ivParams.height = ScreenUtils.getScreenWidth(context) / 3 - 40
                    ivParams.width = ScreenUtils.getScreenWidth(context) / 3 - 40
                    layoutParams = ivParams
                }
            }

            //获取图片真正的宽高
            Glide.with(context)
                .asBitmap()
                .load(item)
                .transform(object :BitmapTransformation(){
                    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
                    }
                    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int
                    ): Bitmap {
                        return cropBitmap(toTransform,outWidth,outHeight)
                    }
                })
                .into(object :BitmapImageViewTarget(this){
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        super.onResourceReady(resource, transition)
                        if (data.size==1){
                            val bWidth = resource.width
                            val bHeight = resource.height
                            val width = ScreenUtils.getScreenWidth(context) - 200
                            val height = if (bHeight.toFloat()/bWidth.toFloat()>1.4f){
                                (width*1.4f).toInt()
                            }else{
                                (width*bHeight.toFloat()/bWidth.toFloat()).toInt()
                            }
                            ivParams.width = width
                            ivParams.height = height
                            layoutParams = ivParams
                        }
                    }
                })
        }
    }

    /**
     * 裁剪图片
     */
    private fun cropBitmap(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        if (data.size == 1) {
            // 高/宽大于1.4，将bitmap多余部分裁剪
            if (height.toFloat() / width.toFloat() > 1.4f) {
                val h = width.toFloat() * 1.4f
                return Bitmap.createBitmap(bitmap, 0, 0, width, h.toInt())
            }
        } else {
            //裁剪成方图
            return if (width > height) {
                Bitmap.createBitmap(bitmap, 0, 0, width, width)
            } else {
                Bitmap.createBitmap(bitmap, 0, 0, height, height)
            }
        }
        return bitmap
    }
}