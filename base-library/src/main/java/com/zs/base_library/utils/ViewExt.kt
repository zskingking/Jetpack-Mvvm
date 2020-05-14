package com.zs.base_library.utils

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

/**
 * des 关于视图的工具类
 * @date 2020/5/14
 * @author zs
 */
fun ViewPager2.initFragment(
    fragment: Fragment,
    fragments: ArrayList<Fragment>
): ViewPager2 {
    //设置适配器
    adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
    return this
}