package com.zs.zs_jetpack.common

import androidx.recyclerview.widget.DiffUtil
import com.zs.zs_jetpack.bean.ArticleBean

/**
 * des 文章diff，用于判断数据是否改变，避免没改变的视图刷新
 * @date 2020/7/8
 * @author zs
 */
class ArticleDiff : DiffUtil.ItemCallback<ArticleBean.DatasBean>() {

    /**
     * 判断是否是同一个item
     */
    override fun areItemsTheSame(
        oldItem: ArticleBean.DatasBean,
        newItem: ArticleBean.DatasBean
    ): Boolean {
        return oldItem.id == newItem.id
    }

    /**
     * 判断数据是否改变
     */
    override fun areContentsTheSame(
        oldItem: ArticleBean.DatasBean,
        newItem: ArticleBean.DatasBean
    ): Boolean {
        return oldItem.author == newItem.author
                && oldItem.collect == newItem.collect
                && oldItem.desc == newItem.desc
                && oldItem.envelopePic == newItem.envelopePic
                && oldItem.id == newItem.id
                && oldItem.niceDate == newItem.niceDate
                && oldItem.shareDate == newItem.shareDate
                && oldItem.shareUser == newItem.shareUser
                && oldItem.superChapterName == newItem.superChapterName
                && oldItem.title == newItem.title
                && oldItem.type == newItem.type
    }
}