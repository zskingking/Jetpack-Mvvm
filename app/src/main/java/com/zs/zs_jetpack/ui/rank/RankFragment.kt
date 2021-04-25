package com.zs.zs_jetpack.ui.rank

import android.os.Bundle
import androidx.lifecycle.Observer
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.common.setNoRepeatClick
import com.zs.base_library.common.smartDismiss
import com.zs.base_library.common.toast
import com.zs.base_library.utils.Param
import com.zs.base_wa_lib.base.BaseLoadingFragment
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.constants.Constants
import com.zs.zs_jetpack.constants.UrlConstants
import com.zs.zs_jetpack.utils.CacheUtil
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.fragment_rank.*
import kotlinx.android.synthetic.main.fragment_rank.tvIntegral
import kotlinx.android.synthetic.main.fragment_rank.tvRanking

/**
 * des 排名
 * @date 2020/7/13
 * @author zs
 */
class RankFragment : BaseLoadingFragment() {

    /**
     * 我的积分
     */
    @Param
    private var myIntegral: Int? = null

    /**
     * 我的排名
     */
    @Param
    private var myRank: Int? = null

    /**
     * 我的名称
     */
    @Param
    private var myName: String? = null

    private val adapter by lazy { RankAdapter() }

    private lateinit var rankVM: RankVM

    override fun initViewModel() {
        rankVM = getFragmentViewModel(RankVM::class.java)
    }

    override fun observe() {
        rankVM.rankLiveData.observe(this, Observer {
            smartRefresh.smartDismiss()
            adapter.setNewData(it)
            gloding?.dismiss()
        })
        rankVM.errorLiveData.observe(this, Observer {
            smartRefresh.smartDismiss()
        })
    }

    override fun init(savedInstanceState: Bundle?) {
        initView()
        loadData()
    }

    override fun initView() {
        tvIntegral.text = "$myIntegral"
        tvRanking.text = "$myRank"

        rvRank.adapter = adapter

        smartRefresh.setOnRefreshListener {
            rankVM.getRank(true)
        }
        smartRefresh.setOnLoadMoreListener {
            rankVM.getRank(false)
        }
    }

    override fun loadData() {
        //自动刷新
        smartRefresh.autoRefresh()
        gloding?.loading()
    }

    override fun onClick() {
        setNoRepeatClick(ivBack, ivDetail) {
            when (it.id) {
                R.id.ivBack -> nav().navigateUp()
                //查看积分详情
                R.id.ivDetail -> {
                    nav().navigate(R.id.action_rank_fragment_to_web_fragment, Bundle().apply {
                        putString(Constants.WEB_URL, UrlConstants.INTEGRAL_RULE)
                        putString(Constants.WEB_TITLE, getString(R.string.integral_rule))
                    })
                }
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_rank

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_rank, rankVM)
            .addBindingParam(BR.vm, rankVM)
    }
}