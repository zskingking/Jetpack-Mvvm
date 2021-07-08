package com.zs.base_library.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.zs.base_library.BaseApp
import com.zs.base_library.R
import com.zs.base_library.common.clickNoRepeat
import java.lang.reflect.Field


/**
 * des 带差异化的模板适配器,可以有效避免全部刷新,
 *     通过对数据比对也可动态实现增加、删除，无需再手动控制
 * author zs
 * date 2021/3/4
 */
abstract class BaseDiffAdapter<T, BD : ViewDataBinding>(private val diffCallback: DiffUtil.ItemCallback<T> = DefaultDiff()) :
    ListAdapter<T, BaseDiffAdapter.BaseDiffViewHolder>(diffCallback) {


    var recyclerView: RecyclerView? = null

    /**
     * 默认类型
     */
    private val TYPE_NORMAL = 1000

    /**
     * head
     */
    private val TYPE_HEADER = 1001

    /**
     * foot
     */
    private val TYPE_FOOTER = 1002

    /**
     * TODO recyclerView head
     * TODO 可以在rv上方添加一些视图，避免列表嵌套
     *
     * TODO head目前存在角标问题，先禁止使用
     */
    private var headView: LinearLayout? = null

    /**
     * recyclerView foot
     * 可以添加 `无更多数据` 之类的附加信息
     */
    private var footerView: View? = null

    /**
     * item点击事件
     * @param Int 角标
     * @param View 点击的View
     */
    protected var _onItemClickListener: ((Int, View) -> Unit)? = null

    /**
     * item中子View点击事件，需要子类做具体触发
     * @param Int 角标
     * @param View 点击的View
     */
    protected var _onItemChildClickListener: ((Int, View) -> Unit)? = null

    /**
     * 注册item点击事件
     */
    fun setOnItemClickListener(onItemClickListener: ((Int, View) -> Unit)? = null) {
        this._onItemClickListener = onItemClickListener
    }

    /**
     * 注册item子View点击事件
     */
    fun setOnItemChildClickListener(onItemChildClickListener: ((Int, View) -> Unit)? = null) {
        this._onItemChildClickListener = onItemChildClickListener
    }

    init {
        hookDiffer()
    }

    /**
     * 通过反射替换mDiffer，并在内部加入一个钩子(ListUpdateCallbackImp)
     */
    private fun hookDiffer(){
        //获取父类mDiffer
        val field: Field = ListAdapter::class.java.getDeclaredField("mDiffer")
        //给权限
        field.isAccessible = true
        val differ: AsyncListDiffer<T>  = AsyncListDiffer(
            //钩子
            ListUpdateCallbackImp(this@BaseDiffAdapter),
            AsyncDifferConfig.Builder(diffCallback).build()
        )
        differ.addListListener { previousList, currentList ->
            onCurrentListChanged(previousList, currentList);
        }
        //替换
        field.set(this@BaseDiffAdapter, differ)
    }


    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): BaseDiffViewHolder {

        return if (viewType == TYPE_FOOTER && footerView != null) {
            BaseDiffViewHolder(
                footerView!!
            )
        } else {
            val itemDataBinding = DataBindingUtil.inflate<BD>(
                LayoutInflater.from(parent.context),
                itemLayoutId,
                parent,
                false
            )
            BaseDiffViewHolder(
                itemDataBinding.root
            )
        }
    }

    override fun onBindViewHolder(holder: BaseDiffViewHolder, position: Int) {
        //如果是foot,直接return
        if (isFooterView(position)) return
        //onItemClickListener不为null即注册了点击事件，此时才会注册itemView点击事件
        _onItemClickListener?.let { click ->
            //itemView点击事件
            holder.itemView.clickNoRepeat {
                click.invoke(holder.bindingAdapterPosition, it)
            }
        }
        //获取ViewDataBinding
        val binding = DataBindingUtil.getBinding<BD>(holder.itemView)
        binding?.let { bindData(holder, position, getItem(position), binding) }
        binding?.executePendingBindings()
    }

    override fun getItemViewType(position: Int): Int {
        return if (isFooterView(position)) {
            TYPE_FOOTER
        } else {
            TYPE_NORMAL
        }
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
        handleEmpty(list,true)
    }

    /**
     * @param commitCallback diff算法在工作线程执行，完成后会执行该Runnable
     */
    override fun submitList(list: List<T>?, commitCallback: Runnable?) {
        super.submitList(
            if (list == null) mutableListOf() else
                mutableListOf<T>().apply {
                    addAll(
                        list
                    )
                }, commitCallback
        )
        handleEmpty(list,true)
    }

    /**
     * 处理空白页
     */
    private fun handleEmpty(list: List<T>?,isHandleEmpty: Boolean){
        //处理空白页
        if (isHandleEmpty) {
            //无数据
            if (list.isNullOrEmpty()) {
                //显示空白页
                showEmpty()
            } else {
                //移除空白页
                removeFooterView()
            }
        }
    }

    /**
     * 显示空白页
     */
    private fun showEmpty() {
        //空白页已经显示的情况下无需重复显示
        if (footerView != null) return
        addFooterView(
            LayoutInflater.from(BaseApp.getContext())
                .inflate(R.layout.srl_classics_footer, null)
        )
    }

    /**
     * 添加footer
     */
    fun addFooterView(footerView: View) {
        //先移除再添加
        removeFooterView()
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        footerView.layoutParams = params
        this.footerView = footerView
        ifGridLayoutManager()
        notifyItemInserted(itemCount - 1)
    }

    /**
     * 移除footer
     */
    fun removeFooterView() {
        if (footerView == null) return
        //从列表中移除
        notifyItemRemoved(itemCount - 1)
        //置为null
        footerView = null
    }

    override fun getItemCount(): Int {
        var count = currentList.size
        //存在footerView ItemCount+1
        if (footerView != null) {
            count++
        }
        return count
    }

    /**
     * 当前item是不是foot
     */
    private fun isFooterView(position: Int): Boolean {
        return footerView != null && position == itemCount - 1
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        runCatching {
            if (this.recyclerView == null && this.recyclerView !== recyclerView) {
                this.recyclerView = recyclerView
            }
            ifGridLayoutManager()
            //关闭动画
            this.recyclerView?.itemAnimator = null
        }
    }

    /**
     * 避免插入到GridLayoutManager一个小格中
     */
    private fun ifGridLayoutManager() {
        val layoutManager: RecyclerView.LayoutManager? = recyclerView?.layoutManager
        if (layoutManager is GridLayoutManager) {
            layoutManager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (isFooterView(position)) layoutManager.spanCount else 1
                }
            }
        }
    }

    /**
     * 绑定holder的数据
     *
     * @param holder   item holder
     * @param position 索引
     * @param itemData 实体数据
     * @param binding item对应的 data binding
     */
    protected abstract fun bindData(
        holder: BaseDiffViewHolder,
        position: Int,
        itemData: T,
        binding: BD
    )

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * 获取item布局的id
     *
     * @return item layout 的 id
     */
    protected abstract val itemLayoutId: Int

    /**
     * RecyclerView.ViewHolder是一个抽象类，创建一个BaseViewHolder方便使用
     */
    class BaseDiffViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView)

}
