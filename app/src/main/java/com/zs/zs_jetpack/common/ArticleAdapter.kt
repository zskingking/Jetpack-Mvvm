package com.zs.zs_jetpack.common

import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zs.base_library.common.clickNoRepeat
import com.zs.base_library.common.loadRadius
import com.zs.base_library.utils.ColorUtils
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.bean.ArticleEntity
import com.zs.zs_jetpack.constants.Constants
import java.text.FieldPosition

/**
 * 文章适配器
 * @author zs
 * @date 2020-07-07修改
 */
class ArticleAdapter(list:MutableList<ArticleEntity.DatasBean>)
    : BaseMultiItemQuickAdapter<ArticleEntity.DatasBean,
        BaseViewHolder>(list) {

    /**
     * 子view点击回调接口。单独写一个接口目的是可以使用防止快速点击
     */
    private var onItemClickListener: OnChildItemClickListener? = null

    init {
        addItemType(Constants.ITEM_ARTICLE, R.layout.item_home_article)
        addItemType(Constants.ITEM_ARTICLE_PIC,R.layout.item_project)
    }

    fun setOnChildItemClickListener(onItemClickListener: OnChildItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun convert(helper: BaseViewHolder, item: ArticleEntity.DatasBean) {
        when(helper.itemViewType){
            //不带图片
            Constants.ITEM_ARTICLE ->{
                item.run {
                    helper.getView<View>(R.id.root).clickNoRepeat {
                        onItemClickListener?.onItemChildClick(
                            this@ArticleAdapter,it,helper.bindingAdapterPosition)
                    }
                    if (type==1){
                        helper.setText(R.id.tvTag,"置顶 ")
                        helper.setTextColor(R.id.tvTag, ColorUtils.parseColor(R.color.red))
                    }else{
                        helper.setText(R.id.tvTag,"")
                    }
                    helper.setText(R.id.tvAuthor,if (!TextUtils.isEmpty(author))author else shareUser)
                    helper.setText(R.id.tvDate,niceDate)
                    helper.setText(R.id.tvTitle, Html.fromHtml(title))
                    helper.setText(R.id.tvChapterName,superChapterName)
                    helper.getView<ImageView>(R.id.ivCollect)
                        .apply {
                            clickNoRepeat {
                                onItemClickListener?.onItemChildClick(
                                    this@ArticleAdapter,this,helper.bindingAdapterPosition)
                            }
                            if (item.collect) {
                                setImageResource(R.mipmap.article_collect)
                            }else{
                                setImageResource(R.mipmap.article_un_collect)
                            }
                        }
                }
            }

            //带图片
            Constants.ITEM_ARTICLE_PIC->{
                item.apply {
                    helper.getView<View>(R.id.root).clickNoRepeat {
                        onItemClickListener?.onItemChildClick(
                            this@ArticleAdapter,it,helper.bindingAdapterPosition)
                    }
                    envelopePic?.let {
                        helper.getView<ImageView>(R.id.ivTitle).loadRadius(mContext, it,20)
                    }
                    helper.setText(R.id.tvTitle,title)
                    helper.setText(R.id.tvDes,desc)
                    helper.setText(R.id.tvNameData,"$niceDate | $author")
                    helper.getView<ImageView>(R.id.ivCollect).apply {
                        clickNoRepeat {
                            onItemClickListener?.onItemChildClick(
                                this@ArticleAdapter,this,helper.bindingAdapterPosition)
                        }
                        if (item.collect) {
                            setImageResource(R.mipmap.article_collect)
                        }else{
                            setImageResource(R.mipmap.article_un_collect)
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取跳转至web界面的bundle
     */
    fun getBundle(position: Int):Bundle{
        return Bundle().apply {
            putString("loadUrl",data[position].link)
            putString("title",data[position].title)
            putString("author",data[position].author)
            putInt("id",data[position].id)
        }
    }
}