package com.zs.zs_jetpack.common

import android.content.Context
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.zs.base_library.common.getThemeColor
import com.zs.zs_jetpack.R
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

/**
 * des Magic 文字下划线适配器
 * @date 2020/7/10
 * @author zs
 */
class TabNavigatorAdapter(
    private val tabList: MutableList<String>
    , private val onItemClick: (position: Int) -> Unit
) : CommonNavigatorAdapter() {

    override fun getCount(): Int {
        return tabList.size
    }

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        val simplePager = ColorTransitionPagerTitleView(context)
        simplePager.textSize = 15f
        simplePager.text = tabList[index]
        simplePager.setPadding(30, 0, 30, 0)
        simplePager.normalColor = getThemeColor(context, R.attr.theme_color_3)
        simplePager.selectedColor = getThemeColor(context, R.attr.theme_color_1)
        simplePager.setOnClickListener {
            onItemClick.invoke(index)
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
        indicator.setColors(getThemeColor(context, R.attr.theme_color_1))
        return indicator
    }
}