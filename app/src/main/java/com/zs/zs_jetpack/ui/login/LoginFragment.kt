package com.zs.zs_jetpack.ui.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.common.clickNoRepeat
import com.zs.base_library.common.toast
import com.zs.base_library.utils.KeyBoardUtil
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.databinding.FragmentLoginBinding

/**
 * des 登陆
 * @date 2020/7/9
 * @author zs
 */
class LoginFragment : BaseVmFragment<FragmentLoginBinding>() {

    private lateinit var loginVM: LoginVM

    override fun initViewModel() {
        loginVM = getFragmentViewModel(LoginVM::class.java)
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = loginVM
        initView()
    }

    override fun observe() {
        loginVM.loginLiveData.observe(this, Observer {
            toast("登陆成功")
            nav().navigateUp()
        })

        loginVM.errorLiveData.observe(this, Observer {
            setViewStatus(true)
        })
    }

    override fun initView() {
        binding.tvRegister.clickNoRepeat {
            nav().navigate(R.id.action_main_fragment_to_register_fragment)
        }
        binding.ivClear.clickNoRepeat {
            loginVM.username.set("")
        }
        binding.ivPasswordVisibility.clickNoRepeat {
            //true false 切换
            loginVM.passIsVisibility.set(!loginVM.passIsVisibility.get()!!)
        }
        binding.llLogin.clickNoRepeat {
            //关闭软键盘
            KeyBoardUtil.closeKeyboard(binding.etUsername,mActivity)
            KeyBoardUtil.closeKeyboard(binding.etPassword,mActivity)
            if (loginVM.username.get()!!.isEmpty()){
                toast("请填写用户名")
                return@clickNoRepeat
            }
            if (loginVM.password.get()!!.isEmpty()){
                toast("请填写密码")
                return@clickNoRepeat
            }
            login()
        }
        binding.tvSkip.clickNoRepeat {
            nav().navigateUp()
        }
    }

    private fun login() {
        setViewStatus(false)
        loginVM.login()
    }

    /**
     * 登录时给具备点击事件的View上锁，登陆失败时解锁
     * 并且施加动画
     */
    private fun setViewStatus(lockStatus:Boolean){
        binding.llLogin.isEnabled = lockStatus
        binding.tvRegister.isEnabled = lockStatus
        binding.tvSkip.isEnabled = lockStatus
        binding.etUsername.isEnabled = lockStatus
        binding.etPassword.isEnabled = lockStatus
        if (lockStatus) {
            binding.tvLoginTxt.visibility = View.VISIBLE
            binding.indicatorView.visibility = View.GONE
            binding.indicatorView.hide()
        }else {
            binding.tvLoginTxt.visibility = View.GONE
            binding.indicatorView.visibility = View.VISIBLE
            binding.indicatorView.show()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }
}