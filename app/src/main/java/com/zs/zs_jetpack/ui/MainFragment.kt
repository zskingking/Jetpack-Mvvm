package com.zs.zs_jetpack.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.common.doSelected
import com.zs.base_library.common.initFragment
import com.zs.zs_jetpack.PlayViewModel
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.constants.Constants
import com.zs.zs_jetpack.databinding.FragmentMainBinding
import com.zs.zs_jetpack.play.PlayerManager
import com.zs.zs_jetpack.ui.main.home.HomeFragment
import com.zs.zs_jetpack.ui.main.mine.MineFragment
import com.zs.zs_jetpack.ui.main.tab.TabFragment
import com.zs.zs_jetpack.ui.main.square.SquareFragment

/**
 * des 主页面
 * @author zs
 * @date 2020-05-14
 */
class MainFragment : BaseVmFragment<FragmentMainBinding>() {
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
        binding.vm = playViewModel
        //初始化viewpager2
        binding.vpHome.initFragment(childFragmentManager, fragmentList).run {
            //全部缓存,避免切换回重新加载
            offscreenPageLimit = fragmentList.size
        }

        binding.vpHome.doSelected {
            binding.btnNav.menu.getItem(it).isChecked = true
        }
        //初始化底部导航栏
        binding.btnNav.run {
            setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_home -> {
                        binding.vpHome.setCurrentItem(0, false)
                    }
                    R.id.menu_project -> binding.vpHome.setCurrentItem(1, false)
                    R.id.menu_square -> binding.vpHome.setCurrentItem(2, false)
                    R.id.menu_official_account -> binding.vpHome.setCurrentItem(3, false)
                    R.id.menu_mine -> binding.vpHome.setCurrentItem(4, false)
                }
                // 这里注意返回true,否则点击失效
                true
            }
        }
    }

    override fun onClick() {
        binding.floatLayout.playClick {
            PlayerManager.instance.controlPlay()
        }
        binding.floatLayout.rootClick {
            nav().navigate(R.id.action_main_fragment_to_play_fragment)
        }
    }

    override fun getLayoutId() = R.layout.fragment_main
}
