package com.zs.zs_jetpack.ui.main.square


import androidx.fragment.app.Fragment
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.base.LazyVmFragment
import com.zs.base_library.common.initFragment
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.common.TabNavigatorAdapter
import com.zs.zs_jetpack.ui.main.square.system.SystemFragment
import com.zs.zs_jetpack.ui.main.square.system.SystemVM
import com.zs.zs_jetpack.ui.main.tab.ArticleListFragment
import com.zs.zs_jetpack.view.MagicIndicatorUtils
import kotlinx.android.synthetic.main.fragment_square.*
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter


/**
 * des 广场做成viewpager模式，以后有新功能可以在此处扩展。
 * @author zs
 * @date 2020-05-14
 */
class SquareFragment : LazyVmFragment() {

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
        vpSquareFragment.initFragment(this, arrayListOf<Fragment>().apply {
            tabList.forEach { _ ->
                add(SystemFragment())
            }
        })
        //下划线绑定
        val commonNavigator = CommonNavigator(mActivity)
        commonNavigator.adapter = getCommonNavigatorAdapter(tabList)
        tabLayout.navigator = commonNavigator
        MagicIndicatorUtils.bindForViewPager2(vpSquareFragment, tabLayout)
    }

    /**
     * 获取下划线根跟字适配器
     */
    private fun getCommonNavigatorAdapter(tabList: MutableList<String>): CommonNavigatorAdapter {
        return TabNavigatorAdapter(tabList) {
            vpSquareFragment.currentItem = it
        }
    }


    override fun getLayoutId() = R.layout.fragment_square

    override fun getDataBindingConfig(): DataBindingConfig? {
        return null
    }


}

