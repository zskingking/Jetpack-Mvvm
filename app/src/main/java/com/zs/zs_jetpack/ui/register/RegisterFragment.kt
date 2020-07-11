package com.zs.zs_jetpack.ui.register

import android.os.Bundle
import androidx.lifecycle.Observer
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.common.setNoRepeatClick
import com.zs.base_library.common.toast
import com.zs.base_library.utils.KeyBoardUtil
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * des 注册
 * @date 2020/7/9
 * @author zs
 */
class RegisterFragment : BaseVmFragment() {
    override fun getLayoutId() = R.layout.fragment_register

    private lateinit var registerVM: RegisterVM

    override fun initViewModel() {
        registerVM = getFragmentViewModel(RegisterVM::class.java)
    }

    override fun observe() {
        registerVM.registerLiveData.observe(this, Observer {
            toast("注册成功")
            nav().navigateUp()
        })
    }

    override fun init(savedInstanceState: Bundle?) {
        initView()
    }

    override fun initView() {
        setNoRepeatClick(
            ivBack,
            ivClear,
            ivPasswordVisibility,
            ivRePasswordVisibility,
            rlRegister
        ) {
            when (it.id) {
                //回退
                R.id.ivBack -> nav().navigateUp()
                //重置用户名
                R.id.ivClear -> registerVM.username.set("")
                //密码是否明文
                R.id.ivPasswordVisibility -> registerVM.passIsVisibility.set(!registerVM.passIsVisibility.get()!!)
                //确认密码是否明文
                R.id.ivRePasswordVisibility -> registerVM.rePassIsVisibility.set(!registerVM.rePassIsVisibility.get()!!)
                //注册
                R.id.rlRegister -> {
                    //关闭软键盘
                    KeyBoardUtil.closeKeyboard(etUsername,mActivity)
                    KeyBoardUtil.closeKeyboard(etPassword,mActivity)
                    KeyBoardUtil.closeKeyboard(etRePassword,mActivity)
                    if (registerVM.username.get()!!.isEmpty()){
                        toast("请填写用户名")
                        return@setNoRepeatClick
                    }
                    if (registerVM.password.get()!!.isEmpty()){
                        toast("请填写密码")
                        return@setNoRepeatClick
                    }
                    if (registerVM.rePassword.get()!!.isEmpty()){
                        toast("请填写确认密码")
                        return@setNoRepeatClick
                    }
                    registerVM.register()
                }
            }
        }
    }

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_register, registerVM)
            .addBindingParam(BR.vm, registerVM)
    }
}