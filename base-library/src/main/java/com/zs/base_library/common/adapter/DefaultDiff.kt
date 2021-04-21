package com.zs.base_library.common.adapter

import androidx.recyclerview.widget.DiffUtil

/**
 * des 默认diff
 * author zs
 * date 2021/3/5
 */
open class DefaultDiff<T> : DiffUtil.ItemCallback<T>() {

    /**
     * 是否为同一个对象
     * @return true 是同一个item  false 非同一个item
     */
    override fun areItemsTheSame(
        oldItem: T,
        newItem: T
    ): Boolean {
        //默认写法，全部刷新
        return false
    }

    /**
     * 同一个对象的数据是否发生改变
     * 可根据对业务的分析，仅比对可能发生改变的字段
     * @return true item未改变  false item已改变
     *         默认返回true，即item数据不会发生改变。
     *         如果业务中数据可能会改变，对此方法进行重写(比对可能发生改变的字段)即可
     */
    override fun areContentsTheSame(
        oldItem: T,
        newItem: T
    ): Boolean {
        return false
    }

}
