package com.zs.base_library.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * des 基于ListAdapter封装的DataBinding适配器
 *     泛型 T:模型类类型
 *     泛型 B:ItemView对应的ViewDataBinding
 * @author zs
 * @date 2020/9/10
 */
abstract class BaseListAdapter<T, B : ViewDataBinding>(
    private val context: Context, diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseListAdapter.BaseViewHolder>(diffCallback) {


    /**
     * item点击事件
     * @param Int 角标
     * @param View 点击的View
     */
    private var onItemClickListener: ((Int, View) -> Unit)? = null


    /**
     * item中子View点击事件，需要子类做具体触发
     * @param Int 角标
     * @param View 点击的View
     */
    private var onItemChildClickListener: ((Int, View) -> Unit)? = null

    /**
     * 注册item点击事件
     */
    fun setOnItemClickListener(onItemClickListener: ((Int, View) -> Unit)? = null) {
        this.onItemClickListener = onItemClickListener
    }

    /**
     * 注册item子View点击事件
     */
    fun setOnItemChildClickListener(onItemChildClickListener: ((Int, View) -> Unit)? = null) {
        this.onItemChildClickListener = onItemChildClickListener
    }

    /**
     * 创建viewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding: B = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            this.getLayoutId(),
            parent,
            false
        )
        return BaseViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        //onItemClickListener不为null即注册了点击事件，此时才会注册itemView点击事件
        onItemClickListener?.let { click ->
            //itemView点击事件
            holder.itemView.clickNoRepeat {
                click.invoke(holder.bindingAdapterPosition, it)
            }
        }
        //获取ViewDataBinding
        val binding = DataBindingUtil.getBinding<B>(holder.itemView)
        binding?.let { onBindItem(getItem(position), it) }
        binding?.executePendingBindings()
    }

    /**
     * 重新加载数据时必须换一个list集合，否则diff不生效
     */
    override fun submitList(list: List<T>?) {
        super.submitList(if (list == null) mutableListOf() else
            mutableListOf<T>().apply {
                addAll(
                    list
                )
            })
    }

    /**
     * 供子类绑定DataBinding
     */
    abstract fun onBindItem(item: T, binding: B)

    /**
     * item布局id
     */
    abstract fun getLayoutId(): Int


    /**
     * RecyclerView.ViewHolder是一个抽象类，创建一个BaseViewHolder方便使用
     */
    class BaseViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView)

}