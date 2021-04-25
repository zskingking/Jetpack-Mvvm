package com.zs.zs_jetpack.ui.collect

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
import com.zs.zs_jetpack.common.ArticleAdapter
import kotlinx.android.synthetic.main.fragment_collect.*

/**
 * des 收藏
 * @date 2020/7/14
 * @author zs
 */
class CollectFragment : BaseLoadingFragment() {
    /**
     * 文章适配器
     */
    private lateinit var adapter: ArticleAdapter

    private lateinit var collectVM: CollectVM

    override fun initViewModel() {
        collectVM = getFragmentViewModel(CollectVM::class.java)
    }

    override fun observe() {
        collectVM.articleLiveData.observe(this, Observer {
            smartDismiss(smartRefresh)
            gloding?.dismiss()
            adapter.submitList(it)
        })

        collectVM.errorLiveData.observe(this, Observer {
            smartDismiss(smartRefresh)
            if (it.errorCode == -100) {
                //显示网络错误
                gloding?.showInternetError()
                gloding?.setReloadListener {
                    collectVM.getCollect()
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
        (rvCollect.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        adapter = ArticleAdapter(mActivity).apply {
            rvCollect.adapter = this
            setOnItemClickListener { i, _ ->
                nav().navigate(
                    R.id.action_collect_fragment_to_web_fragment,
                    this@CollectFragment.adapter.getBundle(i)
                )
            }
            setOnItemChildClickListener { i, view ->
                when (view.id) {
                    //收藏
                    R.id.ivCollect -> {
                        this@CollectFragment.adapter.currentList[i].apply {
                            collectVM.unCollect(id)
                        }
                    }
                }
            }
        }

        //刷新
        smartRefresh.setOnRefreshListener {
            collectVM.getCollect()
        }
        //加载更多
        smartRefresh.setOnLoadMoreListener {
            collectVM.loadMoreCollect()
        }

        ivBack.clickNoRepeat {
            nav().navigateUp()
        }
    }

    override fun loadData() {
        collectVM.getCollect()
        gloding?.loading()
    }

    override fun getLayoutId() = R.layout.fragment_collect

    override fun getDataBindingConfig(): DataBindingConfig? {
        return null
    }

}