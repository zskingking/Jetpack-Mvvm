package com.zs.zs_jetpack.ui.integral

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zs.zs_jetpack.R

/**
 * 积分增加记录
 * @author zs
 * @date 2020-03-17
 */
class IntegralAdapter() : BaseQuickAdapter<IntegralListBean, BaseViewHolder>(R.layout.item_integral) {

    override fun convert(helper: BaseViewHolder, item: IntegralListBean?) {

        item?.apply {
            val desc = desc
            val firstSpace = desc?.indexOf(" ")
            val secondSpace = firstSpace?.plus(1)?.let { desc.indexOf(" ", it) }
            val time = secondSpace?.let { desc.substring(0, it) }
            val title = secondSpace?.plus(1)?.let {
                desc.substring(it)
                    .replace(",", "")
                    .replace("：", "")
                    .replace(" ", "")
            }
            helper.setText(R.id.tvAddIntegralMode,title)
            helper.setText(R.id.tvDate,time)
            helper.setText(R.id.tvAddIntegral,"+$coinCount")
        }
    }

}