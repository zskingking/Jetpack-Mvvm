package com.zs.zs_jetpack.ui.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.common.setNoRepeatClick
import com.zs.base_library.common.toast
import com.zs.base_library.utils.KeyBoardUtil
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * des 登陆
 * @date 2020/7/9
 * @author zs
 */
class LoginFragment : BaseVmFragment() {

    private lateinit var loginVM: LoginVM

    override fun initViewModel() {
        loginVM = getFragmentViewModel(LoginVM::class.java)
    }

    override fun init(savedInstanceState: Bundle?) {
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
        setNoRepeatClick(tvRegister, ivClear, ivPasswordVisibility, llLogin, tvSkip) {
            when (it.id) {
                //注册
                R.id.tvRegister -> nav().navigate(R.id.action_main_fragment_to_register_fragment)
                //清除账号
                R.id.ivClear -> {
                    loginVM.username.set("")
                }
                //密码是否可见
                R.id.ivPasswordVisibility -> {
                    //true false 切换
                    loginVM.passIsVisibility.set(!loginVM.passIsVisibility.get()!!)
                }
                //登陆
                R.id.llLogin -> {
                    //关闭软键盘
                    KeyBoardUtil.closeKeyboard(etUsername,mActivity)
                    KeyBoardUtil.closeKeyboard(etPassword,mActivity)
                    if (loginVM.username.get()!!.isEmpty()){
                        toast("请填写用户名")
                        return@setNoRepeatClick
                    }
                    if (loginVM.password.get()!!.isEmpty()){
                        toast("请填写密码")
                        return@setNoRepeatClick
                    }
                    login()
                }
                //跳过登陆
                R.id.tvSkip -> {
                    nav().navigateUp()
                }
            }
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
        llLogin.isEnabled = lockStatus
        tvRegister.isEnabled = lockStatus
        tvSkip.isEnabled = lockStatus
        etUsername.isEnabled = lockStatus
        etPassword.isEnabled = lockStatus
        if (lockStatus) {
            tvLoginTxt.visibility = View.VISIBLE
            indicatorView.visibility = View.GONE
            indicatorView.hide()
        }else {
            tvLoginTxt.visibility = View.GONE
            indicatorView.visibility = View.VISIBLE
            indicatorView.show()
        }
    }

    override fun getLayoutId(): Int? {
        return R.layout.fragment_login
    }

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_login, loginVM)
            .addBindingParam(BR.vm, loginVM)
    }
}