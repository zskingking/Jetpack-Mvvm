package com.zs.zs_jetpack.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import cn.bingoogolapple.bgabanner.BGABanner
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.base.LazyVmFragment
import com.zs.base_library.common.loadUrl
import com.zs.base_library.common.setElevation
import com.zs.base_library.common.smartDismiss
import com.zs.wanandroid.entity.BannerEntity
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.bean.ArticleEntity
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
        LayoutInflater.from(mActivity).inflate(R.layout.banner_head,null)
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
                    loadMoreModule?.isEnableLoadMore = false
                    //上拉加载
                    loadMoreModule?.setOnLoadMoreListener {
                        page++
                        homeVm?.getArticleList(false)
                    }
                    //将banner添加至recyclerView
                    addHeaderView(head)
                    rvHomeList.adapter = this
                    setDiffCallback(getDiff())
                }
            } else {
                adapter?.setDiffNewData(it)
            }
            if (adapter?.loadMoreModule?.isLoading!!){
                adapter?.loadMoreModule?.loadMoreComplete()
            }
        })
        homeVm?.banner?.observe(this, Observer {
            bannerList = it
            initBanner()
        })
        homeVm?.errorLiveData?.observe(this, Observer {

        })
    }

    /**
     * 用于比对数据做局部刷新
     */
    private fun getDiff():DiffUtil.ItemCallback<ArticleEntity.DatasBean>{
        return object : DiffUtil.ItemCallback<ArticleEntity.DatasBean>(){

            /**
             * 判断是否是同一个item
             */
            override fun areItemsTheSame(
                oldItem: ArticleEntity.DatasBean,
                newItem: ArticleEntity.DatasBean
            ): Boolean {
                return oldItem.id == newItem.id
            }

            /**
             * 判断数据是否改变
             */
            override fun areContentsTheSame(
                oldItem: ArticleEntity.DatasBean,
                newItem: ArticleEntity.DatasBean
            ): Boolean {
                return oldItem.author == newItem.author
                        && oldItem.collect == newItem.collect
                        && oldItem.desc == newItem.desc
                        && oldItem.envelopePic == newItem.envelopePic
                        && oldItem.id == newItem.id
                        && oldItem.niceDate == newItem.niceDate
                        && oldItem.shareDate == newItem.shareDate
                        && oldItem.shareUser == newItem.shareUser
                        && oldItem.superChapterName == newItem.superChapterName
                        && oldItem.title == newItem.title
                        && oldItem.type == newItem.type
            }
        }
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
