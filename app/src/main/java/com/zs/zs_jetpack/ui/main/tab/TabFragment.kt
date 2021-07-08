package com.zs.zs_jetpack.ui.main.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.zs.base_library.base.LazyVmFragment
import com.zs.base_library.common.initFragment
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.common.TabNavigatorAdapter
import com.zs.zs_jetpack.databinding.FragmentTabBinding
import com.zs.zs_jetpack.view.MagicIndicatorUtils
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter


/**
 * des 项目/公众号公用
 * @author zs
 * @date 2020-05-14
 */
class TabFragment : LazyVmFragment<FragmentTabBinding>() {

    /**
     * fragment 类型
     */
    private var type = 0

    private var tabVM: TabVM? = null
    override fun lazyInit() {
        arguments?.apply {
            type = getInt("type")
        }
        loadData()
    }

    override fun initViewModel() {
        tabVM = getFragmentViewModel(TabVM::class.java)
    }

    override fun observe() {
        tabVM?.tabLiveData?.observe(this, Observer {
            initViewPager(it)
        })
    }

    override fun loadData() {
        tabVM?.getTab(type)
    }

    private fun initViewPager(tabList: MutableList<TabBean>) {
        binding.vpArticleFragment.initFragment(childFragmentManager, arrayListOf<Fragment>().apply {
            tabList.forEach {
                add(ArticleListFragment().apply {
                    //想各个fragment传递信息
                    val bundle = Bundle()
                    bundle.putInt("type", type)
                    bundle.putInt("tabId", it.id)
                    bundle.putString("name", it.name)
                    arguments = bundle
                })
            }
        })
        //下划线绑定
        val commonNavigator = CommonNavigator(mActivity)
        commonNavigator.adapter = getCommonNavigatorAdapter(tabList)
        binding.tabLayout.navigator = commonNavigator
        MagicIndicatorUtils.bindForViewPager(binding.vpArticleFragment, binding.tabLayout)
    }

    /**
     * 获取下划线根跟字适配器
     */
    private fun getCommonNavigatorAdapter(tabList: MutableList<TabBean>): CommonNavigatorAdapter {
        return TabNavigatorAdapter(mutableListOf<String>().apply {
            //将tab转换为String
            tabList.forEach {
                it.name?.let { it1 -> add(it1) }
            }
        }) {
            binding.vpArticleFragment.currentItem = it
        }
    }

    override fun getLayoutId() = R.layout.fragment_tab
}

