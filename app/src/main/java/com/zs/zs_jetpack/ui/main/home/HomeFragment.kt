package com.zs.zs_jetpack.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import cn.bingoogolapple.bgabanner.BGABanner
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.base.LazyVmFragment
import com.zs.base_library.common.*
import com.zs.wanandroid.entity.BannerEntity
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.common.ArticleAdapter
import com.zs.zs_jetpack.common.ArticleDiff
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * des 首页
 * @author zs
 * @date 2020-05-14
 */
class HomeFragment : LazyVmFragment(), BGABanner.Adapter<ImageView?, String?>
    , BGABanner.Delegate<ImageView?, String?> {

    private var homeVm: HomeVM? = null
    private var bannerList: MutableList<BannerEntity>? = null
    private val adapter by lazy { ArticleAdapter(mutableListOf()) }
    private val head by lazy {
        LayoutInflater.from(mActivity).inflate(R.layout.banner_head, null)
    }
    private val banner by lazy {
        head.findViewById(R.id.banner) as BGABanner
    }

    /**
     * 页码
     */
    private var page = 0
    override fun initViewModel() {
        homeVm = getActivityViewModel(HomeVM::class.java)
    }

    override fun observe() {
        homeVm?.articleList?.observe(this, Observer {
            smartDismiss(smartRefresh)
            adapter.setNewData(it)
        })
        homeVm?.banner?.observe(this, Observer {
            bannerList = it
            initBanner()
        })
        homeVm?.errorLiveData?.observe(this, Observer {

        })
    }


    override fun lazyInit() {
        initView()
        loadData()
    }

    override fun initView() {
        //设置阴影
        setElevation(clTitle, 10f)
        smartRefresh.setOnRefreshListener {
            page = 0
            homeVm?.getArticleList(true)
        }
        //上拉加载
        smartRefresh.setOnLoadMoreListener {
            page++
            homeVm?.getArticleList(false)
        }
        smartConfig(smartRefresh)
        adapter.apply {
            //将banner添加至recyclerView
            addHeaderView(head)
            rvHomeList.adapter = this
            //setDiffCallback(ArticleDiff())
        }
        setNoRepeatClick(ivAdd){
            when(it.id){
                R.id.ivAdd ->{}
            }
        }
    }

    override fun loadData() {
        homeVm?.getArticleList(true)
    }

    override fun onClick() {
        setNoRepeatClick(tvSearch) {
            when (it.id) {
                R.id.tvSearch -> nav().navigate(R.id.action_main_fragment_to_search_fragment)
            }
        }
    }

    override fun getLayoutId(): Int? {
        return R.layout.fragment_home
    }

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
