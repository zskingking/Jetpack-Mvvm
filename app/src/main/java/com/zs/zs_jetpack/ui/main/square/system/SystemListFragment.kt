package com.zs.zs_jetpack.ui.main.square.system

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.common.setNoRepeatClick
import com.zs.base_library.common.smartConfig
import com.zs.base_library.common.smartDismiss
import com.zs.base_library.utils.Param
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.common.ArticleAdapter
import com.zs.zs_jetpack.common.OnChildItemClickListener
import com.zs.zs_jetpack.utils.CacheUtil
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_system_list.*
import kotlinx.android.synthetic.main.fragment_system_list.smartRefresh

/**
 * @date 2020/7/10
 * @author zs
 */
class SystemListFragment : BaseVmFragment() ,OnChildItemClickListener{

    /**
     * 文章适配器
     */
    private lateinit var adapter:ArticleAdapter
    private lateinit var systemVM: SystemVM

    @Param
    private var systemId = 0

    @Param
    private var systemTitle = ""

    override fun initViewModel() {
        systemVM = getFragmentViewModel(SystemVM::class.java)
    }

    override fun observe() {
        systemVM.articleLiveData.observe(this, Observer {
            smartDismiss(smartRefresh)
            adapter.setNewData(it)
        })

        //收藏
        systemVM.collectLiveData.observe(this, Observer {
            adapter.collectNotifyById(it)
        })
        //取消收藏
        systemVM.unCollectLiveData.observe(this, Observer {
            adapter.unCollectNotifyById(it)
        })
        systemVM.errorLiveData.observe(this, Observer {
            smartDismiss(smartRefresh)
        })
    }

    override fun init(savedInstanceState: Bundle?) {
        initView()
        loadData()
    }

    override fun initView() {
        adapter = ArticleAdapter(mutableListOf()).apply {
            setOnChildItemClickListener(this@SystemListFragment)
            rvSystemList.adapter = this
        }
        //关闭更新动画
        (rvSystemList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        tvTitle.text = systemTitle
        //配置smartRefresh
        smartConfig(smartRefresh)
        smartRefresh.setOnRefreshListener {
            systemVM.getArticleList(true,systemId)
        }
        smartRefresh.setOnLoadMoreListener {
            systemVM.getArticleList(false,systemId)
        }
        setNoRepeatClick(ivBack){
            when(it.id){
                R.id.ivBack -> nav().navigateUp()
            }
        }
    }

    override fun loadData() {
        //自动刷新
        smartRefresh.autoRefresh()
    }

    override fun getLayoutId() = R.layout.fragment_system_list
    override fun getDataBindingConfig(): DataBindingConfig? {
        return null
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        when(view.id){
            //item
            R.id.root->{
                nav().navigate(R.id.action_system_list_fragment_to_web_fragment
                    ,this@SystemListFragment.adapter.getBundle(position))
            }
            //收藏
            R.id.ivCollect->{
                //已登陆
                if (CacheUtil.isLogin()){
                    this@SystemListFragment.adapter.data[position].apply {
                        //已收藏取消收藏
                        if (collect){
                            systemVM.unCollect(id)
                        }else{
                            systemVM.collect(id)
                        }
                    }
                }else{
                    //未登陆跳登陆页
                    nav().navigate(R.id.action_system_list_fragment_to_login_fragment)
                }
            }
        }
    }
}