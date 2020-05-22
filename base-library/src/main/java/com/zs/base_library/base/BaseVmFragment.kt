package com.zs.base_library.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment

/**
 * des mvvm 基础 fragment
 * @date 2020/5/9
 * @author zs
 */
abstract class BaseVmFragment : Fragment() {

    private lateinit var mActivity:AppCompatActivity
    private var privider: ViewModelProvider? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getLayoutId()?.let {
            return inflater.inflate(it, null)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
        initViewModel()
        //observe一定要在初始化最后，因为observe会收到黏性事件，随后对ui做处理
        observe()
        onClick()
    }

    /**
     * 初始化viewModel
     * 之所以没有设计为抽象，是因为部分简单activity可能不需要viewModel
     * observe同理
     */
    open fun initViewModel(){

    }

    /**
     * 注册观察者
     */
    open fun observe(){

    }

    /**
     * 通过activity获取viewModel，跟随activity生命周期
     */
    protected fun <T : ViewModel?> getActivityViewModel(modelClass: Class<T>): T {
        if (privider == null) {
            privider = ViewModelProvider(mActivity)
        }
        return privider!!.get(modelClass)
    }

    /**
     * 通过fragment获取viewModel，跟随fragment生命周期
     */
    protected open fun <T : ViewModel?> getFragmentViewModel(modelClass: Class<T>): T {
        if (privider == null) {
            privider = ViewModelProvider(this)
        }
        return privider!!.get(modelClass)
    }

    /**
     * fragment跳转
     */
    protected fun nav(actionId:Int){
        NavHostFragment.findNavController(this).navigate(actionId)
    }

    /**
     * 点击事件
     */
    open fun onClick(){

    }

    /**
     * activity入口
     */
    abstract fun init(savedInstanceState: Bundle?)

    /**
     * 获取layout布局
     */
    abstract fun getLayoutId():Int?

}