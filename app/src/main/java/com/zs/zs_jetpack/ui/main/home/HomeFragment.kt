package com.zs.zs_jetpack.ui.main.home

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
import cn.bingoogolapple.bgabanner.BGABanner
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.base.LazyVmFragment
import com.zs.base_library.common.*
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.common.ArticleAdapter
import com.zs.zs_jetpack.utils.CacheUtil
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * des 首页
 * @author zs
 * @date 2020-05-14
 */
class HomeFragment : LazyVmFragment(), BGABanner.Adapter<ImageView?, String?>,
    BGABanner.Delegate<ImageView?, String?> {

    private var homeVm: HomeVM? = null
    private var bannerList: MutableList<BannerBean>? = null
    private val adapter by lazy { ArticleAdapter(mActivity) }

    override fun initViewModel() {
        homeVm = getActivityViewModel(HomeVM::class.java)
    }

    override fun observe() {
        //文章列表
        homeVm?.articleList?.observe(this, Observer {
            smartDismiss(smartRefresh)
            adapter.submitList(it)
        })
        //banner
        homeVm?.banner?.observe(this, Observer {
            bannerList = it
            initBanner()
        })
        //请求错误
        homeVm?.errorLiveData?.observe(this, Observer {
            smartDismiss(smartRefresh)
        })
    }

    override fun lazyInit() {
        initView()
        loadData()
    }

    override fun initView() {
        //关闭更新动画
        (rvHomeList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        smartRefresh.setOnRefreshListener {
            homeVm?.getBanner()
            homeVm?.getArticle()
        }
        //上拉加载
        smartRefresh.setOnLoadMoreListener {
            homeVm?.loadMoreArticle()
        }
        smartConfig(smartRefresh)
        adapter.apply {
            rvHomeList.adapter = this
            setOnItemClickListener { i, _ ->
                nav().navigate(
                    R.id.action_main_fragment_to_web_fragment,
                    this@HomeFragment.adapter.getBundle(i)
                )
            }
            setOnItemChildClickListener { i, view ->
                when (view.id) {
                    //收藏
                    R.id.ivCollect -> {
                        if (CacheUtil.isLogin()) {
                            this@HomeFragment.adapter.currentList[i].apply {
                                //已收藏取消收藏
                                if (collect) {
                                    homeVm?.unCollect(id)
                                } else {
                                    homeVm?.collect(id)
                                }
                            }
                        } else {
                            nav().navigate(R.id.action_main_fragment_to_login_fragment)
                        }
                    }
                }
            }
        }
        setNoRepeatClick(ivAdd) {
            when (it.id) {
                R.id.ivAdd -> nav().navigate(R.id.action_main_fragment_to_publish_fragment)
            }
        }
    }

    override fun loadData() {
        //自动刷新
        smartRefresh.autoRefresh()
    }

    override fun onClick() {
        setNoRepeatClick(clSearch) {
            when (it.id) {
                R.id.clSearch -> nav().navigate(R.id.action_main_fragment_to_search_fragment)
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_home

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_home, homeVm)
            .addBindingParam(BR.vm, homeVm)
    }

    /**
     * 填充banner
     */
    override fun fillBannerItem(
        banner: BGABanner?,
        itemView: ImageView?,
        model: String?,
        position: Int
    ) {
        itemView?.apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            loadUrl(mActivity, bannerList?.get(position)?.imagePath!!)
        }
    }

    /**
     * banner点击事件
     */
    override fun onBannerItemClick(
        banner: BGABanner?,
        itemView: ImageView?,
        model: String?,
        position: Int
    ) {
        nav().navigate(R.id.action_main_fragment_to_web_fragment, Bundle().apply {
            bannerList?.get(position)?.let {
                putString("loadUrl", it.url)
                putString("title", it.title)
                putInt("id", it.id)
            }
        })
    }

    /**
     * 初始化banner
     */
    private fun initBanner() {
        banner.apply {
            setAutoPlayAble(true)
            val views: MutableList<View> = ArrayList()
            bannerList?.forEach { _ ->
                views.add(ImageView(mActivity).apply {
                    setBackgroundResource(R.drawable.ripple_bg)
                })
            }
            setAdapter(this@HomeFragment)
            setDelegate(this@HomeFragment)
            setData(views)
        }
    }

}
