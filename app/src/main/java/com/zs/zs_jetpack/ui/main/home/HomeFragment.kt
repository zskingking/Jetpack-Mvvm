package com.zs.zs_jetpack.ui.main.home


import android.util.Log
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.base.LazyVmFragment
import com.zs.base_library.common.clickNoRepeat
import com.zs.base_library.common.setNoRepeatClick
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.play.PlayerManager
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * des 首页
 * @author zs
 * @date 2020-05-14
 */
class HomeFragment : LazyVmFragment() {

    private var homeVm:HomeVM? = null
    override fun initViewModel() {
        homeVm = getFragmentViewModel(HomeVM::class.java)
    }

    override fun lazyInit() {
        tvTitle.clickNoRepeat {
            homeVm?.setTitle()
            PlayerManager.instance.start()
        }
    }

    override fun getLayoutId(): Int? {
        return R.layout.fragment_home
    }

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_home, homeVm)
            .addBindingParam(BR.vm, homeVm)
    }

}
