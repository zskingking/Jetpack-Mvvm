package com.zs.zs_jetpack.ui.main.tab

import android.view.View
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.base.LazyVmFragment
import com.zs.base_library.common.smartConfig
import com.zs.base_library.common.smartDismiss
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.common.ArticleAdapter
import com.zs.zs_jetpack.common.OnChildItemClickListener
import kotlinx.android.synthetic.main.fragment_article.*

/**
 * des 文章列表fragment
 * @date 2020/7/7
 * @author zs
 */
class ArticleListFragment : LazyVmFragment() , OnChildItemClickListener {

    private var articleVM: ArticleVM? = null

    /**
     * fragment类型，项目或公号
     */
    private var type = 0

    /**
     * tab的id
     */
    private var tabId = 0

    /**
     * 文章适配器
     */
    private val adapter by lazy { ArticleAdapter(mutableListOf()) }

    override fun initViewModel() {
        articleVM = getFragmentViewModel(ArticleVM::class.java)
    }

    override fun observe() {
        articleVM?.articleLiveData?.observe(this, Observer {
            smartDismiss(smartRefresh)
            adapter.setNewData(it)
        })
        articleVM?.errorLiveData?.observe(this, Observer {

        })
    }

    override fun lazyInit() {
        type = arguments?.getInt("type") ?: 0
        tabId = arguments?.getInt("tabId") ?: 0
        initView()
        loadData()
    }

    override fun initView() {
        //下拉刷新
        smartRefresh.setOnRefreshListener {
            articleVM?.getArticle(type, tabId, true)
        }
        //上拉加载
        smartRefresh.setOnLoadMoreListener {
            articleVM?.getArticle(type,tabId,false)
        }
        smartConfig(smartRefresh)
        adapter.apply {
            setOnChildItemClickListener(this@ArticleListFragment)
            rvArticleList.adapter = this
            //setDiffCallback(ArticleDiff())
        }
    }

    override fun loadData() {
        articleVM?.getArticle(type, tabId, true)
    }

    override fun getLayoutId(): Int? {
        return R.layout.fragment_article
    }

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_article, articleVM)
            .addBindingParam(BR.vm, articleVM)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        when(view.id){
            //item
            R.id.root->{
                nav().navigate(R.id.action_main_fragment_to_web_fragment
                    ,this@ArticleListFragment.adapter.getBundle(position))
            }
            //收藏
            R.id.ivCollect->{

            }
        }
    }
}