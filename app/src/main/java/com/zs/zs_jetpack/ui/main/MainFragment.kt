package com.zs.zs_jetpack.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.utils.initFragment
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.ui.main.home.HomeFragment
import com.zs.zs_jetpack.ui.main.mine.MineFragment
import com.zs.zs_jetpack.ui.main.project.ProjectFragment
import com.zs.zs_jetpack.ui.main.public.PublicNumberFragment
import com.zs.zs_jetpack.ui.main.square.SquareFragment
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * des 主页面
 * @author zs
 * @date 2020-05-14
 */
class MainFragment : BaseVmFragment() {

    private val fragmentList = arrayListOf<Fragment>()

    private val homeFragment by lazy { HomeFragment() }
    private val projectFragment by lazy { ProjectFragment() }
    private val squareFragment by lazy { SquareFragment() }
    private val publicNumberFragment by lazy { PublicNumberFragment() }
    private val mineFragment by lazy { MineFragment() }

    init {
        fragmentList.apply {
            add(homeFragment)
            add(projectFragment)
            add(squareFragment)
            add(publicNumberFragment)
            add(mineFragment)
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        //初始化viewpager2
        vpHome.initFragment(this,fragmentList).run {
            offscreenPageLimit = fragmentList.size
        }
        vpHome.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                btnNav.menu.getItem(position).isChecked = true
            }
        })
        //初始化底部导航栏
        btnNav.run {
            setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_home -> vpHome.setCurrentItem(0,false)
                    R.id.menu_project -> vpHome.setCurrentItem(1,false)
                    R.id.menu_square -> vpHome.setCurrentItem(2,false)
                    R.id.menu_official_account -> vpHome.setCurrentItem(3,false)
                    R.id.menu_mine -> vpHome.setCurrentItem(4,false)
                }
                // 这里注意返回true,否则点击失效
                true
            }
        }
    }

    override fun getLayoutId(): Int? {
        return R.layout.fragment_main
    }
}
