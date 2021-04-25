package com.zs.zs_jetpack.ui.integral

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.common.clickNoRepeat
import com.zs.base_library.common.smartDismiss
import com.zs.base_wa_lib.base.BaseLoadingFragment
import com.zs.zs_jetpack.R
import com.zs.base_wa_lib.view.LoadingTip
import kotlinx.android.synthetic.main.fragment_integral.*

/**
 * des 积分
 * @date 2020/7/14
 * @author zs
 */
class IntegralFragment : BaseLoadingFragment(){
    /**
     * 文章适配器
     */
    private lateinit var adapter: IntegralAdapter


    private lateinit var integralVM: IntegralVM

    override fun initViewModel() {
        integralVM = getFragmentViewModel(IntegralVM::class.java)
    }

    override fun observe() {
        integralVM.integralLiveData.observe(this, Observer {
            smartDismiss(smartRefresh)
            gloding?.dismiss()
            adapter.submitList(it)
        })
        integralVM.footLiveDate.observe(this, Observer {

        })
        integralVM.errorLiveData.observe(this, Observer {
            smartDismiss(smartRefresh)
            if (it.errorCode == -100) {
                //显示网络错误
                gloding?.showInternetError()
                gloding?.setReloadListener {
                    integralVM.getIntegral()
                }
            }
        })
    }

    override fun init(savedInstanceState: Bundle?) {
        initView()
        loadData()
    }

    override fun initView() {
        //关闭更新动画
        (rvIntegral.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        adapter = IntegralAdapter().apply {
            rvIntegral.adapter = this
        }

        //刷新
        smartRefresh.setOnRefreshListener {
            integralVM.getIntegral()
        }
        //加载更多
        smartRefresh.setOnLoadMoreListener {
            integralVM.loadMore()
        }

        ivBack.clickNoRepeat {
            nav().navigateUp()
        }
    }

    override fun loadData() {
        integralVM.getIntegral()
        gloding?.loading()
    }

    override fun getLayoutId() = R.layout.fragment_integral

    override fun getDataBindingConfig(): DataBindingConfig? {
        return null
    }
}