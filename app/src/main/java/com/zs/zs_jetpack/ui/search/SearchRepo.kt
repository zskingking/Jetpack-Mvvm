package com.zs.zs_jetpack.ui.search

import com.zs.base_library.base.BaseRepository
import com.zs.base_wa_lib.article.ArticleListBean
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager

/**
 * @author zs
 * @data 2020/7/11
 */
class SearchRepo: BaseRepository() {

    /**
     * 页码
     */
    private var page = 0

    /**
     * 搜索
     */
    suspend fun search(keyWord: String) = withIO {
        page = 0
        RetrofitManager.getApiService(ApiService::class.java)
            .search(page,keyWord)
            .data()
            .let {
                ArticleListBean.trans(it.datas?: mutableListOf())
            }
    }

    suspend fun loadMore(keyWord: String) = withIO {
        page++
        RetrofitManager.getApiService(ApiService::class.java)
            .search(page,keyWord)
            .data()
            .let {
                ArticleListBean.trans(it.datas?: mutableListOf())
            }
    }

}