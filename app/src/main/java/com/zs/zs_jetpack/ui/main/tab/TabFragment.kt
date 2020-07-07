package com.zs.zs_jetpack.ui.main.tab

import android.content.Context
import android.util.Log
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.base.LazyVmFragment
import com.zs.base_library.common.initFragment
import com.zs.base_library.common.setElevation
import com.zs.base_library.utils.ColorUtils
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.view.MagicIndicatorUtils
import kotlinx.android.synthetic.main.fragment_tab.*
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

/**
 * des 项目/公众号公用
 * @author zs
 * @date 2020-05-14
 */
class TabFragment : LazyVmFragment() {

    /**
     * fragment 类型
     */
    private var type = 0

    private var tabVM: TabVM? = null
    override fun lazyInit() {
        arguments?.apply {
            type = getInt("type")
        }
        initView()
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

    override fun initView() {
        setElevation(flTop, 6f)
    }

    override fun loadData() {
        tabVM?.getTab(type)
    }

    private fun initViewPager(tabList: MutableList<TabBean>) {
        vpArticleFragment.initFragment(this, arrayListOf<Fragment>().apply {
            tabList.forEach { _ ->
                add(ArticleListFragment())
            }
        })
        TabLayoutMediator(tabLayout, vpArticleFragment, false,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                Log.i("TabLayoutMediator","${tabList[position].name}")
                tab.text = tabList[position].name
            }).attach()
//        val commonNavigator = CommonNavigator(mActivity)
//        commonNavigator.adapter = getCommonNavigatorAdapter(tabList)
//        tabLayout.navigator = commonNavigator
//        MagicIndicatorUtils.bindForViewPager2(vpArticleFragment,tabLayout)
    }

    /**
     * 适配下划线根文字
     */
    private fun getCommonNavigatorAdapter(tabList: MutableList<TabBean>): CommonNavigatorAdapter {
        return object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return tabList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePager = ColorTransitionPagerTitleView(context)
                simplePager.textSize = 15f
                simplePager.text = tabList[index].name
                simplePager.setPadding(30, 0, 30, 0)
                simplePager.normalColor = ColorUtils.parseColor(R.color.neutral)
                simplePager.selectedColor = ColorUtils.parseColor(R.color.theme)
                simplePager.setOnClickListener {
                    vpArticleFragment.currentItem = index
                }
                return simplePager
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.lineHeight = UIUtil.dip2px(context, 3.0).toFloat()
                indicator.lineWidth = UIUtil.dip2px(context, 28.0).toFloat()
                indicator.roundRadius = UIUtil.dip2px(context, 1.5).toFloat()
                indicator.startInterpolator = AccelerateInterpolator()
                indicator.endInterpolator = DecelerateInterpolator(2.0f)
                indicator.setColors(ColorUtils.parseColor(R.color.theme))
                return indicator
            }
        }
    }

    override fun getLayoutId(): Int? {
        return R.layout.fragment_tab
    }

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_tab, tabVM)
            .addBindingParam(BR.vm, tabVM)
    }


}

