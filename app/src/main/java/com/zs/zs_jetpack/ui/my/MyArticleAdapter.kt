package com.zs.zs_jetpack.ui.my

import android.text.Html
import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.bean.ArticleBean

/**
 * 我的文章适配器
 * @author zs
 * @date 2020-03-17
 */
class MyArticleAdapter :
    BaseQuickAdapter<ArticleBean.DatasBean, BaseViewHolder>(R.layout.item_my_article) {

    override fun convert(helper: BaseViewHolder, item: ArticleBean.DatasBean?) {
        item?.run {
            helper.setText(R.id.tvAuthor, if (!TextUtils.isEmpty(author)) author else shareUser)
            helper.setText(R.id.tvDate, niceDate)
            helper.setText(R.id.tvTitle, Html.fromHtml(title))
            helper.setText(R.id.tvChapterName, superChapterName)
            helper.addOnClickListener(R.id.rlContent)
            helper.addOnClickListener(R.id.tvDelete)
        }
    }

    /**
     * 单个删除
     */
    fun delete(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }
}