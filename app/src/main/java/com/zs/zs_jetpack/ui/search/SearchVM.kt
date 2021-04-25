package com.zs.zs_jetpack.ui.search

import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zs.base_library.base.BaseViewModel
import com.zs.base_library.common.toast
import com.zs.zs_jetpack.bean.ArticleBean
import com.zs.zs_jetpack.bean.ArticleListBean
import com.zs.zs_jetpack.ui.common.CollectRequest
import org.w3c.dom.Text

/**
 * @date 2020/7/8
 * @author zs
 */
class SearchVM : BaseViewModel() {

    private val repo by lazy { SearchRepo() }
    private val collectRequest by lazy { CollectRequest(_articleLiveData) }

    /**
     * 关键字，与搜索框保持一致
     */
    val keyWord = ObservableField<String>("")

    /**
     * 搜索到的文章
     */
    private val _articleLiveData = MutableLiveData<MutableList<ArticleListBean>>()
    val articleLiveData: LiveData<MutableList<ArticleListBean>> = _articleLiveData


    fun search() {
        if (TextUtils.isEmpty(keyWord.get())) {
            toast("请输入关键字")
            return
        }
        launch {
            _articleLiveData.value = repo.search(keyWord.get()!!)
        }
    }

    fun loadMore() {
        if (TextUtils.isEmpty(keyWord.get())) {
            toast("请输入关键字")
            return
        }
        launch {
            val list = _articleLiveData.value
            list?.addAll(repo.loadMore(keyWord.get()!!))
            _articleLiveData.value = list
            handleList(_articleLiveData)
        }
    }

    /**
     * 收藏
     */
    fun collect(id: Int) {
        launch {
            collectRequest.collect(id)
        }
    }

    /**
     * 取消收藏
     */
    fun unCollect(id: Int) {
        launch {
            collectRequest.unCollect(id)
        }
    }
}