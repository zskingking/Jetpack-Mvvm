package com.zs.zs_jetpack.common

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zs.base_library.common.clickNoRepeat
import com.zs.base_library.common.loadRadius
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.bean.ArticleListBean
import com.zs.zs_jetpack.constants.Constants

/**
 * 文章适配器
 * 关于该适配器用到了多布局,所以没有使用DataBinding,我觉得通过BaseQuickAdapter更加简便
 * 上述言论属一家之见，也可能是山猪吃不惯细糠～-～
 *
 * @author zs
 * @date 2020-07-07修改
 */


class aaArticleAdapter(
    list: MutableList<ArticleListBean>
) : BaseMultiItemQuickAdapter<ArticleListBean,
        BaseViewHolder>(list) {

    /**
     * 子view点击回调接口。单独写一个接口目的是可以使用防止快速点击
     */
    private var onItemClickListener: OnChildItemClickListener? = null

    init {
        addItemType(Constants.ITEM_ARTICLE, R.layout.item_home_article)
        addItemType(Constants.ITEM_ARTICLE_PIC, R.layout.item_project)
    }

    fun setOnChildItemClickListener(onItemClickListener: OnChildItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun convert(helper: BaseViewHolder, item: ArticleListBean) {
        when (helper.itemViewType) {
            //不带图片
            Constants.ITEM_ARTICLE -> {
                item.run {
                    helper.getView<View>(R.id.root).clickNoRepeat {
                        onItemClickListener?.onItemChildClick(
                            this@aaArticleAdapter, it, helper.adapterPosition - headerLayoutCount
                        )
                    }
                    helper.setText(R.id.tvTag, "置顶 ")
                    helper.setText(
                        R.id.tvAuthor,
                        author
                    )
                    helper.setText(R.id.tvDate, date)
                    helper.setText(R.id.tvTitle, title)
                    helper.setText(R.id.tvChapterName, articleTag)
                    helper.getView<ImageView>(R.id.ivCollect)
                        .apply {
                            clickNoRepeat {
                                onItemClickListener?.onItemChildClick(
                                    this@aaArticleAdapter,
                                    this,
                                    helper.adapterPosition - headerLayoutCount
                                )
                            }
                            if (item.collect) {
                                setImageResource(R.mipmap._collect)
                            } else {
                                setImageResource(R.mipmap.un_collect)
                            }
                        }
                }
            }

            //带图片
            Constants.ITEM_ARTICLE_PIC -> {
                item.apply {
                    //根布局
                    helper.getView<View>(R.id.root).clickNoRepeat {
                        onItemClickListener?.onItemChildClick(
                            this@aaArticleAdapter, it, helper.adapterPosition - headerLayoutCount
                        )
                    }
                    //图片
                    picUrl?.let {
                        helper.getView<ImageView>(R.id.ivTitle).loadRadius(mContext, it, 20)
                    }
                    //标题
                    helper.setText(R.id.tvTitle, title)
                    //描述信息
                    helper.setText(R.id.tvDes, desc)
                    //日期
                    helper.setText(R.id.tvNameData, "$date | $author")
                    //收藏
                    helper.getView<ImageView>(R.id.ivCollect).apply {
                        clickNoRepeat {
                            onItemClickListener?.onItemChildClick(
                                //必须减headCount，否则角标会错乱
                                this@aaArticleAdapter,
                                this,
                                helper.adapterPosition - headerLayoutCount
                            )
                        }
                        if (item.collect) {
                            setImageResource(R.mipmap._collect)
                        } else {
                            setImageResource(R.mipmap.un_collect)
                        }
                    }
                }
            }
        }
    }

    /**
     * 收藏，通过id做局部刷新
     */
    fun collectNotifyById(id: Int) {
        for (index in 0 until data.size) {
            if (id == data[index].id) {
                data[index].collect = true
                //必须加headCount，否则角标错乱
                notifyItemChanged(index + headerLayoutCount)
                return
            }
        }
    }

    /**
     * 取消收藏，通过id做局部刷新
     */
    fun unCollectNotifyById(id: Int) {
        for (index in 0 until data.size) {
            if (id == data[index].id) {
                data[index].collect = false
                //必须加headCount，否则角标错乱
                notifyItemChanged(index + headerLayoutCount)
                return
            }
        }
    }

    /**
     * 获取跳转至web界面的bundle
     */
    fun getBundle(position: Int): Bundle {
        return Bundle().apply {
            putString("loadUrl", data[position].link)
            putString("title", data[position].title)
            putString("author", data[position].author)
            putInt("id", data[position].id)
        }
    }
}