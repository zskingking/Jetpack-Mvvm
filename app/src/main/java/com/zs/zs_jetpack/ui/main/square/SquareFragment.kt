package com.zs.zs_jetpack.ui.main.square


import androidx.fragment.app.Fragment
import com.zs.base_library.base.LazyVmFragment
import com.zs.base_library.common.initFragment
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.common.TabNavigatorAdapter
import com.zs.zs_jetpack.databinding.FragmentSquareBinding
import com.zs.zs_jetpack.ui.main.square.system.SystemFragment
import com.zs.zs_jetpack.view.MagicIndicatorUtils
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter


/**
 * des 广场做成viewpager模式，以后有新功能可以在此处扩展。
 * @author zs
 * @date 2020-05-14
 */
class SquareFragment : LazyVmFragment<FragmentSquareBinding>() {

    override fun lazyInit() {
        initView()
    }

    override fun initView() {
        mutableListOf<String>().apply {
            add("体系")
            add("导航")
            initViewPager(this)
        }
    }

    private fun initViewPager(tabList: MutableList<String>) {
        binding.vpSquareFragment.initFragment(this, arrayListOf<Fragment>().apply {
            tabList.forEach { _ ->
                add(SystemFragment())
            }
        })
        //下划线绑定
        val commonNavigator = CommonNavigator(mActivity)
        commonNavigator.adapter = getCommonNavigatorAdapter(tabList)
        binding.tabLayout.navigator = commonNavigator
        MagicIndicatorUtils.bindForViewPager2(binding.vpSquareFragment, binding.tabLayout)
    }

    /**
     * 获取下划线根跟字适配器
     */
    private fun getCommonNavigatorAdapter(tabList: MutableList<String>): CommonNavigatorAdapter {
        return TabNavigatorAdapter(tabList) {
            binding.vpSquareFragment.currentItem = it
        }
    }


    override fun getLayoutId() = R.layout.fragment_square



}

