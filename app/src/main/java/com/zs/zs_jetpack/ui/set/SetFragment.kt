package com.zs.zs_jetpack.ui.set

import android.os.Bundle
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.common.setNoRepeatClick
import com.zs.base_library.utils.PrefUtils
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.constants.Constants
import kotlinx.android.synthetic.main.fragment_set.*

/**
 * des 设置
 * @author zs
 * @date 2020-06-30
 */
class SetFragment : BaseVmFragment() {

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun onClick() {
        setNoRepeatClick(ivBack,tvClear,tvVersion,tvAuthor,tvProject,tvCopyright,tvLogout){
            when(it.id){
                R.id.ivBack-> nav().navigateUp()
                R.id.tvClear->{
                    PrefUtils.setInt(Constants.SP_THEME_KEY, Constants.THEME_TYPE)
                    mActivity.recreate()
                }
                R.id.tvVersion->{
                    PrefUtils.setInt(Constants.SP_THEME_KEY,Constants.THEME_NIGHT_TYPE)
                    mActivity.recreate()
                }
                R.id.tvAuthor->{}
                R.id.tvProject->{}
                R.id.tvCopyright->{}
                R.id.tvLogout->{}
            }
        }
    }

    override fun getLayoutId(): Int? {
        return R.layout.fragment_set
    }

    override fun getDataBindingConfig(): DataBindingConfig? {
        return null
    }

}