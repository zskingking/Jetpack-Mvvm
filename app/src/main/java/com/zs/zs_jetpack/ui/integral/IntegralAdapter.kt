package com.zs.zs_jetpack.ui.integral

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.zs.base_library.common.BaseListAdapter
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.common.getDefaultDiff
import com.zs.zs_jetpack.databinding.ItemIntegralBinding

/**
 * des
 * @author zs
 * @date 2020/9/10
 */
class IntegralAdapter(context: Context)
    :BaseListAdapter<IntegralListBean,ItemIntegralBinding>(context, getDefaultDiff()){

    override fun onBindItem(item: IntegralListBean, binding: ItemIntegralBinding) {
        binding.dataBean = item
    }

    override fun getLayoutId() = R.layout.item_integral
}