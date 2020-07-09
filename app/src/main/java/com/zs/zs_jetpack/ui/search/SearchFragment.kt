package com.zs.zs_jetpack.ui.search

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.common.dip2px
import com.zs.base_library.common.setElevation
import com.zs.base_library.utils.ScreenUtils
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.concurrent.TimeUnit

class SearchFragment : BaseVmFragment() {

    private var searchVM: SearchVM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //自定义返回
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    startSearchAnim(false)
                    val disposable = Single.timer(250,TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( {
                            nav().navigateUp()
                        }, {})
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun initViewModel() {
        searchVM = getFragmentViewModel(SearchVM::class.java)
    }

    override fun observe() {

    }

    override fun init(savedInstanceState: Bundle?) {
        initView()
        loadData()
    }

    override fun initView() {
        setElevation(clTitle,6f)
        startSearchAnim(true)
    }

    override fun loadData() {

    }

    override fun getLayoutId(): Int? {
        return R.layout.fragment_search
    }

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_search, searchVM)
            .addBindingParam(BR.vm, searchVM)
    }

    /**
     * 开启搜索动画
     * @param isIn 是否为进
     */
    private fun startSearchAnim(isIn:Boolean) {
        //搜索按钮初始宽度为：屏幕宽度-140dp
        val searchWidth = ScreenUtils.getScreenWidth(mActivity) - dip2px(mActivity,140f)
        //搜索按钮目标宽度为：屏幕宽度-32dp
        val targetWidth = ScreenUtils.getScreenWidth(mActivity) - dip2px(mActivity,32f)

        //进入
        val anim = if (isIn){
            ValueAnimator.ofInt(searchWidth,targetWidth)
        }
        //退出
        else{
            ValueAnimator.ofInt(targetWidth,searchWidth)
        }
        anim.duration = 249
        anim.addUpdateListener {
            val value = it.animatedValue as Int
            //平滑的，动态的设置宽度
            val params = etSearch.layoutParams as ViewGroup.MarginLayoutParams
            params.width = value
            etSearch.layoutParams = params
        }
        anim.start()
    }

}