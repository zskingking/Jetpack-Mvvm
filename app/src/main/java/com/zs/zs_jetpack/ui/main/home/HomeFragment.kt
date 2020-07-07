package com.zs.zs_jetpack.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import cn.bingoogolapple.bgabanner.BGABanner
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.base.LazyVmFragment
import com.zs.base_library.common.loadUrl
import com.zs.base_library.common.setElevation
import com.zs.base_library.view.CommonLoadMoreView
import com.zs.wanandroid.entity.BannerEntity
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.common.ArticleAdapter
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
    private var adapter: ArticleAdapter? = null
    private val head  by lazy {
        LayoutInflater.from(mActivity).inflate(R.layout.banner_head,null) as BGABanner
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
            smartRefresh.isRefreshing = false
            if (adapter == null) {
                adapter = ArticleAdapter(it).apply {
                    setLoadMoreView(CommonLoadMoreView())
                    bindToRecyclerView(rvHomeList)
                    setOnLoadMoreListener({
                        page++
                        homeVm?.getArticleList(false)
                    },rvHomeList)
                    //将banner添加至recyclerView
                    addHeaderView(head)
                }
            } else {
                adapter?.setNewData(it)
            }
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
        setElevation(rlTitle, 10f)
        smartRefresh.setOnRefreshListener {
            page = 0
            homeVm?.getArticleList(true)
        }
    }

    override fun loadData() {
        homeVm?.getArticleList(true)
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
        banner.setAutoPlayAble(true)
        val views: MutableList<View> = ArrayList()
        bannerList?.forEach { _ ->
            views.add(ImageView(mActivity).apply {
                setBackgroundResource(R.drawable.ripple_bg)
            })
        }
        banner.setAdapter(this)
        banner.setDelegate(this)
        banner.setData(views)
    }
}
