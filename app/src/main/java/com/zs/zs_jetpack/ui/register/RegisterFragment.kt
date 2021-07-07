package com.zs.zs_jetpack.ui.register

import android.os.Bundle
import androidx.lifecycle.Observer
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.common.clickNoRepeat
import com.zs.base_library.common.toast
import com.zs.base_library.utils.KeyBoardUtil
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.databinding.FragmentRegisterBinding

/**
 * des 注册
 * @date 2020/7/9
 * @author zs
 */
class RegisterFragment : BaseVmFragment<FragmentRegisterBinding>() {
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
        binding.vm = registerVM
        initView()
    }

    override fun initView() {
        binding.ivBack.clickNoRepeat {
            nav().navigateUp()
        }
        binding.ivClear.clickNoRepeat {
            registerVM.username.set("")
        }
        binding.ivPasswordVisibility.clickNoRepeat {
            registerVM.passIsVisibility.set(!registerVM.passIsVisibility.get()!!)
        }
        binding.ivRePasswordVisibility.clickNoRepeat {
            registerVM.rePassIsVisibility.set(!registerVM.rePassIsVisibility.get()!!)
        }
        binding.rlRegister.clickNoRepeat {
            //关闭软键盘
            KeyBoardUtil.closeKeyboard(binding.etUsername,mActivity)
            KeyBoardUtil.closeKeyboard(binding.etPassword,mActivity)
            KeyBoardUtil.closeKeyboard(binding.etRePassword,mActivity)
            if (registerVM.username.get()!!.isEmpty()){
                toast("请填写用户名")
                return@clickNoRepeat
            }
            if (registerVM.password.get()!!.isEmpty()){
                toast("请填写密码")
                return@clickNoRepeat
            }
            if (registerVM.rePassword.get()!!.isEmpty()){
                toast("请填写确认密码")
                return@clickNoRepeat
            }
            registerVM.register()
        }
    }

}