package com.zs.zs_jetpack.ui.set

import android.os.Bundle
import androidx.lifecycle.Observer
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.common.clickNoRepeat
import com.zs.base_library.common.toast
import com.zs.base_library.utils.PrefUtils
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.constants.Constants
import com.zs.zs_jetpack.constants.UrlConstants
import com.zs.zs_jetpack.databinding.FragmentSetBinding
import com.zs.base_wa_lib.utils.CacheUtil
import com.zs.zs_jetpack.view.DialogUtils

/**
 * des 设置
 * @author zs
 * @date 2020-06-30
 */
class SetFragment : BaseVmFragment<FragmentSetBinding>() {

    private lateinit var setVM: SetVM

    override fun initViewModel() {
        setVM = getFragmentViewModel(SetVM::class.java)
    }

    override fun observe() {
        setVM.logoutLiveData.observe(this, Observer {
            toast("已退出登陆")
            nav().navigateUp()
        })
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = setVM
        setNightMode()
    }

    /**
     * 却换夜间/白天模式
     */
    private fun setNightMode() {
        val theme = PrefUtils.getBoolean(Constants.SP_THEME_KEY,false)
        binding.scDayNight.isChecked = theme
        //不能用切换监听,否则会递归
        binding.scDayNight.clickNoRepeat {
            it.isSelected = !theme
            PrefUtils.setBoolean(Constants.SP_THEME_KEY, it.isSelected)
            mActivity.recreate()
        }
    }

    override fun onClick() {
        binding.ivBack.clickNoRepeat {
            nav().navigateUp()
        }
        binding.tvClear.clickNoRepeat {

        }
        binding.tvVersion.clickNoRepeat {

        }
        binding.tvAuthor.clickNoRepeat {

        }
        binding.tvProject.clickNoRepeat {
            nav().navigate(R.id.action_set_fragment_to_web_fragment, Bundle().apply {
                putString(Constants.WEB_URL, UrlConstants.APP_GITHUB)
                putString(Constants.WEB_TITLE, Constants.APP_NAME)
            })
        }
        binding.tvCopyright.clickNoRepeat {

        }
        binding.tvLogout.clickNoRepeat {
            if (!CacheUtil.isLogin()){
                toast("请先登陆～")
                return@clickNoRepeat
            }
            DialogUtils.confirm(mActivity,"确定退出登录？"){
                setVM.logout()
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_set
}