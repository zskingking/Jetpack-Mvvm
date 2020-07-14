package com.zs.zs_jetpack.ui.collect

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.common.clickNoRepeat
import com.zs.base_library.common.smartDismiss
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.common.OnChildItemClickListener
import com.zs.zs_jetpack.view.LoadingTip
import kotlinx.android.synthetic.main.fragment_collect.*

/**
 * des 收藏
 * @date 2020/7/14
 * @author zs
 */
class CollectFragment : BaseVmFragment(), OnChildItemClickListener {
    /**
     * 文章适配器
     */
    private lateinit var adapter: CollectAdapter

    /**
     * 空白页，网络出错等默认显示
     */
    private val loadingTip by lazy { LoadingTip(mActivity) }

    private lateinit var collectVM: CollectVM

    override fun initViewModel() {
        collectVM = getFragmentViewModel(CollectVM::class.java)
    }

    override fun observe() {
        collectVM.articleLiveData.observe(this, Observer {
            smartDismiss(smartRefresh)
            adapter.setNewData(it)
        })
        //取消收藏
        collectVM.unCollectLiveData.observe(this, Observer {
            adapter.deleteById(it)
        })
        collectVM.emptyLiveDate.observe(this, Observer {
            loadingTip.showEmpty()
        })
        collectVM.errorLiveData.observe(this, Observer {
            smartDismiss(smartRefresh)
            if (it.errorCode == -100) {
                //显示网络错误
                loadingTip.showInternetError()
                loadingTip.setReloadListener {
                    collectVM.getCollect(true)
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
        adapter = CollectAdapter().apply {
            emptyView = loadingTip
            setOnChildItemClickListener(this@CollectFragment)
            rvCollect.adapter = this
        }

        //刷新
        smartRefresh.setOnRefreshListener {
            collectVM.getCollect(true)
        }
        //加载更多
        smartRefresh.setOnLoadMoreListener {
            collectVM.getCollect(false)
        }

        ivBack.clickNoRepeat {
            nav().navigateUp()
        }
    }

    override fun loadData() {
        smartRefresh.autoRefresh()
    }

    override fun getLayoutId() = R.layout.fragment_collect

    override fun getDataBindingConfig(): DataBindingConfig? {
        return null
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        when (view.id) {
            //item
            R.id.root -> {
                nav().navigate(
                    R.id.action_collect_fragment_to_web_fragment
                    , this@CollectFragment.adapter.getBundle(position)
                )
            }
            //收藏
            R.id.ivCollect -> {
                this@CollectFragment.adapter.data[position].apply {
                    collectVM.unCollect(originId)
                }
            }
        }
    }
}