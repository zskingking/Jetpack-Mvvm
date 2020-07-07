package com.zs.zs_jetpack.ui.main.tab

import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.base.LazyVmFragment
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R

/**
 * des 文章列表fragment
 * @date 2020/7/7
 * @author zs
 */
class ArticleListFragment : LazyVmFragment() {

    private var tabVM: TabVM? = null
    override fun initViewModel() {
        tabVM = getFragmentViewModel(TabVM::class.java)
    }

    override fun lazyInit() {

    }

    override fun getLayoutId(): Int? {
        return R.layout.fragment_article
    }

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_article, tabVM)
            .addBindingParam(BR.vm, tabVM)
    }
}