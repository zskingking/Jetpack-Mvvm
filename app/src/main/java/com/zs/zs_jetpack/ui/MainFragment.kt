package com.zs.zs_jetpack.ui

import android.os.Bundle
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.common.initFragment
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.PlayViewModel
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.constants.Constants
import com.zs.zs_jetpack.play.PlayerManager
import com.zs.zs_jetpack.ui.main.home.HomeFragment
import com.zs.zs_jetpack.ui.main.mine.MineFragment
import com.zs.zs_jetpack.ui.main.tab.TabFragment
import com.zs.zs_jetpack.ui.main.square.SquareFragment
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * des 主页面
 * @author zs
 * @date 2020-05-14
 */
class MainFragment : BaseVmFragment() {
    private val fragmentList = arrayListOf<Fragment>()

    /**
     * 首页
     */
    private val homeFragment by lazy { HomeFragment() }

    /**
     * 项目
     */
    private val projectFragment by lazy {
        TabFragment().apply {
            arguments = Bundle().apply {
                putInt("type",Constants.PROJECT_TYPE)
            }
        }
    }

    /**
     * 广场
     */
    private val squareFragment by lazy { SquareFragment() }

    /**
     * 公众号
     */
    private val publicNumberFragment by lazy {
        TabFragment().apply {
            arguments = Bundle().apply {
                putInt("type",Constants.ACCOUNT_TYPE)
            }
        }
    }

    /**
     * 我的
     */
    private val mineFragment by lazy { MineFragment() }
    private var playViewModel: PlayViewModel? = null

    init {
        fragmentList.apply {
            add(homeFragment)
            add(projectFragment)
            add(squareFragment)
            add(publicNumberFragment)
            add(mineFragment)
        }
    }

    override fun initViewModel() {
        playViewModel = getActivityViewModel(PlayViewModel::class.java)
    }

    override fun init(savedInstanceState: Bundle?) {
        //初始化viewpager2
        vpHome.initFragment(this, fragmentList).run {
            //全部缓存,避免切换回重新加载
            offscreenPageLimit = fragmentList.size
        }
        //取消viewPager2滑动
        vpHome.isUserInputEnabled = false
        vpHome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                btnNav.menu.getItem(position).isChecked = true
            }
        })
        //初始化底部导航栏
        btnNav.run {
            setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_home -> vpHome.setCurrentItem(0, false)
                    R.id.menu_project -> vpHome.setCurrentItem(1, false)
                    R.id.menu_square -> vpHome.setCurrentItem(2, false)
                    R.id.menu_official_account -> vpHome.setCurrentItem(3, false)
                    R.id.menu_mine -> vpHome.setCurrentItem(4, false)
                }
                // 这里注意返回true,否则点击失效
                true
            }
        }
        val rv = vpHome[0] as RecyclerView
        rv.isNestedScrollingEnabled = false
    }

    override fun onClick() {
        floatLayout.playClick {
            PlayerManager.instance.controlPlay()
        }
        floatLayout.rootClick {
            nav().navigate(R.id.action_main_fragment_to_play_fragment)
        }
    }

    override fun getLayoutId() = R.layout.fragment_main


    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_main, playViewModel)
            .addBindingParam(BR.vm, playViewModel)
    }
}
