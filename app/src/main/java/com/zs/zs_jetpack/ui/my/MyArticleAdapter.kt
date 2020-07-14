package com.zs.zs_jetpack.ui.my

import android.text.Html
import android.text.TextUtils
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zs.base_library.common.clickNoRepeat
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.bean.ArticleBean
import com.zs.zs_jetpack.common.OnChildItemClickListener
import com.zs.zs_jetpack.constants.Constants

/**
 * 我的文章适配器
 * @author zs
 * @date 2020-03-17
 */
class MyArticleAdapter :
    BaseQuickAdapter<ArticleBean.DatasBean, BaseViewHolder>(R.layout.item_my_article) {
    /**
     * 子view点击回调接口。单独写一个接口目的是可以使用防止快速点击
     */
    private var onItemClickListener: OnChildItemClickListener? = null


    fun setOnChildItemClickListener(onItemClickListener: OnChildItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun convert(helper: BaseViewHolder, item: ArticleBean.DatasBean?) {
        item?.run {
            helper.setText(R.id.tvAuthor, if (!TextUtils.isEmpty(author)) author else shareUser)
            helper.setText(R.id.tvDate, niceDate)
            helper.setText(R.id.tvTitle, Html.fromHtml(title))
            helper.setText(R.id.tvChapterName, superChapterName)
            helper.getView<View>(R.id.rlContent).clickNoRepeat {
                onItemClickListener?.onItemChildClick(this@MyArticleAdapter,it,helper.adapterPosition)
            }
            helper.getView<View>(R.id.tvDelete).clickNoRepeat {
                onItemClickListener?.onItemChildClick(this@MyArticleAdapter,it,helper.adapterPosition)
            }
        }
    }

    /**
     * 单个删除
     */
    fun deleteById(id: Int) {
        for (index in 0 until data.size){
            if (data[index].id == id){
                data.removeAt(index)
                notifyItemRemoved(index)
                return
            }
        }
    }
}