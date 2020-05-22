package com.zs.zs_jetpack.ui.history

import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zs.zs_jetpack.R


/**
 * @date 2020/5/20
 * @author zs
 */
class OuterAdapter:BaseQuickAdapter<OuterBean,BaseViewHolder>(R.layout.item_outer) {

    /**
     * 创建一个缓存池，供所有的子recyclerview公用，避免重复创建view造成卡顿
     */
    private val viewPool by lazy { RecycledViewPool() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return super.onCreateViewHolder(parent, viewType)
    }


    override fun convert(helper: BaseViewHolder, item: OuterBean) {
//        Log.i("OuterAdapter", "${item.title}}")
//        Log.i("OuterAdapter", "${item.imgList}")

        helper.getView<TextView>(R.id.tvTitle).text = item.title
        helper.getView<RecyclerView>(R.id.rvInner).apply {
            setRecycledViewPool(viewPool)
            layoutManager = when (item.imgList?.size) {
                1 -> {
                    GridLayoutManager(context,1)
                }
                2 -> {
                    GridLayoutManager(context,2)
                }
                else -> {
                    GridLayoutManager(context,3)
                }
            }
            val innerAdapter = InnerAdapter()
            adapter = innerAdapter
            innerAdapter.setNewData(item.imgList)
        }
    }
}