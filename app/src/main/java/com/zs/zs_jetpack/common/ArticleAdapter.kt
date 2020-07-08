package com.zs.zs_jetpack.common

import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zs.base_library.common.loadRadius
import com.zs.base_library.utils.ColorUtils
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.bean.ArticleEntity
import com.zs.zs_jetpack.constants.Constants

/**
 * 文章适配器
 * @author zs
 * @date 2020-07-07修改
 */
class ArticleAdapter(list:MutableList<ArticleEntity.DatasBean>)
    : BaseMultiItemQuickAdapter<ArticleEntity.DatasBean,
        BaseViewHolder>(list) {

    init {
        addItemType(Constants.ITEM_ARTICLE, R.layout.item_home_article)
        addItemType(Constants.ITEM_ARTICLE_PIC,R.layout.item_project)
    }

    override fun convert(helper: BaseViewHolder, item: ArticleEntity.DatasBean) {
        when(helper.itemViewType){
            //不带图片
            Constants.ITEM_ARTICLE ->{
                item.run {
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
                    envelopePic?.let {
                        helper.getView<ImageView>(R.id.ivTitle).loadRadius(mContext, it,20)
                    }
                    helper.setText(R.id.tvTitle,title)
                    helper.setText(R.id.tvDes,desc)
                    helper.setText(R.id.tvNameData,"$niceDate | $author")
                    helper.getView<ImageView>(R.id.ivCollect).apply {
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
}