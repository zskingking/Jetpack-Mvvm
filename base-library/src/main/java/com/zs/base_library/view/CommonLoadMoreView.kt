package com.zs.base_library.view

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.zs.base_library.R

/**
 * Created by xiaojianjun on 2019-11-06.
 */
class CommonLoadMoreView : LoadMoreView() {
    override fun getLayoutId() = R.layout.view_load_more_common

    override fun getLoadingViewId() = R.id.load_more_loading_view

    override fun getLoadFailViewId() = R.id.load_more_load_fail_view

    override fun getLoadEndViewId() = R.id.load_more_load_end_view
}