package com.zs.zs_jetpack.ui.main.square.system

import androidx.core.view.children
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.donkingliang.labels.LabelsView
import com.zs.zs_jetpack.R

/**
 * 体系 适配器
 * @author zs
 * @date 2020-03-16
 *
 * @param tagClick tag的点击事件 两个参数分别是外层和内层角标
 */
class SystemAdapter(private val tagClick: (Int, Int) -> Unit
) : BaseQuickAdapter<SystemBean, BaseViewHolder>(R.layout.item_system) {


    override fun convert(helper: BaseViewHolder, item: SystemBean) {
        item.let {
            helper.setText(R.id.tvTitle, item.name)
            helper.getView<LabelsView>(R.id.labelsView).apply {
                setLabels(item.children) { _, _, data ->
                    data.name
                }
                //不知为何，在xml中设置主题背景无效
                for (child in children){
                    child.setBackgroundResource(R.drawable.ripple_tag_bg)
                }
                //标签的点击监听
                setOnLabelClickListener { _, _, position ->
                    tagClick(helper.adapterPosition, position)
                }
            }
        }
    }

}