package com.zs.zs_jetpack.ui.search

import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zs.base_library.base.BaseViewModel
import com.zs.base_library.common.toast
import com.zs.zs_jetpack.bean.ArticleBean
import com.zs.zs_jetpack.bean.ArticleListBean
import org.w3c.dom.Text

/**
 * @date 2020/7/8
 * @author zs
 */
class SearchVM : BaseViewModel() {

    private val repo by lazy { SearchRepo(viewModelScope, errorLiveData) }

    /**
     * 关键字，与搜索框保持一致
     */
    val keyWord = ObservableField<String>().apply {
        set("")
    }

    /**
     * 搜索到的文章
     */
    val articleLiveData = MutableLiveData<MutableList<ArticleListBean>>()

    /**
     * 是否为刷新或者首次加载
     */
    fun search(isRefresh: Boolean) {
        if (TextUtils.isEmpty(keyWord.get())) {
            toast("请输入关键字")
            return
        }
        repo.search(
            isRefresh, keyWord.get()!!
            , articleLiveData
            , emptyLiveDate
        )
    }

    /**
     * 收藏
     */
    fun collect(id:Int){
        repo.collect(id,articleLiveData)
    }

    /**
     * 取消收藏
     */
    fun unCollect(id:Int){
        repo.unCollect(id,articleLiveData)
    }
}