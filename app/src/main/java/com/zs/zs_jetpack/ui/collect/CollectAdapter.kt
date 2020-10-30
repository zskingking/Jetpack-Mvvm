package com.zs.zs_jetpack.ui.collect

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zs.base_library.common.clickNoRepeat
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.common.OnChildItemClickListener


/**
 * des 收藏适配器
 *
 * @author zs
 * @date 2020-03-13
 */
class CollectAdapter:BaseQuickAdapter<CollectBean.DatasBean,BaseViewHolder>(R.layout.item_home_article){

    private var collectClickListener:OnChildItemClickListener? = null

    fun setOnChildItemClickListener(collectClickListener:OnChildItemClickListener){
        this.collectClickListener = collectClickListener
    }
    override fun convert(helper: BaseViewHolder, item: CollectBean.DatasBean?) {
        item?.run {
            helper.setText(R.id.tvTag,"")
            helper.setText(R.id.tvAuthor,author)
            helper.setText(R.id.tvDate,niceDate)
            helper.setText(R.id.tvTitle,title)
            helper.setText(R.id.tvChapterName,chapterName)
            helper.getView<ImageView>(R.id.ivCollect).apply {
                setImageResource(R.mipmap._collect)
                clickNoRepeat {
                    collectClickListener?.onItemChildClick(this@CollectAdapter,it,helper.adapterPosition)
                }
            }
            helper.getView<View>(R.id.root).apply {
                clickNoRepeat {
                    collectClickListener?.onItemChildClick(this@CollectAdapter,it,helper.adapterPosition)
                }
            }
        }
    }

    /**
     * 单个删除
     */
    fun deleteById(id: Int) {
        for (index in 0 until data.size){
            if (data[index].originId == id){
                data.removeAt(index)
                notifyItemRemoved(index)
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
            putInt("id", data[position].originId)
        }
    }
}