package com.zs.base_library.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * des mvvm 基础 fragment
 * @date 2020/5/9
 * @author zs
 */
abstract class BaseVmFragment : Fragment() {

    private lateinit var mActivity:AppCompatActivity
    private var mActivityProvider: ViewModelProvider? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
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
        initViewModel()
        init(savedInstanceState)
        //observe一定要在初始化之后，因为observe会收到黏性事件，随后对ui做处理
        observe()
    }

    /**
     * 短时长toast
     */
    protected fun showToastShort(msg:String){
        Toast.makeText(mActivity,msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 长时长toast
     */
    protected fun showToastLong(msg:String){
        Toast.makeText(mActivity,msg, Toast.LENGTH_LONG).show()
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
    protected open fun <T : ViewModel?> getActivityViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(mActivity)
        }
        return mActivityProvider!!.get(modelClass)
    }

    /**
     * 通过fragment获取viewModel，跟随fragment生命周期
     */
    protected open fun <T : ViewModel?> getFragmentViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(this)
        }
        return mActivityProvider!!.get(modelClass)
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