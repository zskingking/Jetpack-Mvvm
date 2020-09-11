package com.zs.zs_jetpack.common

import androidx.recyclerview.widget.DiffUtil
import com.zs.zs_jetpack.bean.ArticleBean
import com.zs.zs_jetpack.bean.ArticleListBean

/**
 * des diff扩展函数
 * @author zs
 * @date 2020/9/10
 */


/**
 * 文章diff
 */
fun getArticleDiff(): DiffUtil.ItemCallback<ArticleListBean> {
    return object : DiffUtil.ItemCallback<ArticleListBean>() {
        override fun areItemsTheSame(
            oldItem: ArticleListBean,
            newItem: ArticleListBean
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ArticleListBean,
            newItem: ArticleListBean
        ): Boolean {
            //只有点赞和时间可能存在改变
            return oldItem.collect == newItem.collect
                    && oldItem.date == newItem.date
        }
    }
}

/**
 * 默认diff，item数据不可能改变
 */
fun<T> getDefaultDiff(): DiffUtil.ItemCallback<T> {
    return object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(
            oldItem: T,
            newItem: T
        ): Boolean {
            //判断是否为同一个对象，不是直接触发刷新
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem:T,
            newItem: T
        ): Boolean {
            //同一个对象的数据不可能改变
            return true
        }

    }
}