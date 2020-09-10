package com.zs.zs_jetpack.ui.integral

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.common.clickNoRepeat
import com.zs.base_library.common.smartDismiss
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.view.LoadingTip
import kotlinx.android.synthetic.main.fragment_integral.*

/**
 * des 积分
 * @date 2020/7/14
 * @author zs
 */
class IntegralFragment : BaseVmFragment(){
    /**
     * 文章适配器
     */
    private lateinit var adapter: IntegralAdapter

    /**
     * 空白页，网络出错等默认显示
     */
    private val loadingTip by lazy { LoadingTip(mActivity) }

    private lateinit var integralVM: IntegralVM

    override fun initViewModel() {
        integralVM = getFragmentViewModel(IntegralVM::class.java)
    }

    override fun observe() {
        integralVM.integralLiveData.observe(this, Observer {
            smartDismiss(smartRefresh)
            adapter.submitList(it)
        })

        integralVM.emptyLiveDate.observe(this, Observer {
            loadingTip.showEmpty()
        })
        integralVM.errorLiveData.observe(this, Observer {
            smartDismiss(smartRefresh)
            if (it.errorCode == -100) {
                //显示网络错误
                loadingTip.showInternetError()
                loadingTip.setReloadListener {
                    integralVM.getIntegral(true)
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
        adapter = IntegralAdapter(mActivity).apply {
            rvIntegral.adapter = this
        }

        //刷新
        smartRefresh.setOnRefreshListener {
            integralVM.getIntegral(true)
        }
        //加载更多
        smartRefresh.setOnLoadMoreListener {
            integralVM.getIntegral(false)
        }

        ivBack.clickNoRepeat {
            nav().navigateUp()
        }
    }

    override fun loadData() {
        smartRefresh.autoRefresh()
    }

    override fun getLayoutId() = R.layout.fragment_integral

    override fun getDataBindingConfig(): DataBindingConfig? {
        return null
    }
}