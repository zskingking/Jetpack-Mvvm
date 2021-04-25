package com.zs.zs_jetpack.ui.main.square.system

import com.zs.base_library.base.BaseRepository
import com.zs.zs_jetpack.bean.ArticleListBean
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager

/**
 * @date 2020/7/10
 * @author zs
 */
class SystemRepo : BaseRepository() {

    /**
     * 获取体系列表
     */
    suspend fun getSystemList() = withIO {
        RetrofitManager.getApiService(ApiService::class.java)
            .getSystemList()
            .data()
    }

    /**
     * 页码
     */
    private var page = 0

    /**
     * 获取体系对应的文章
     */
    suspend fun getArticleList(id:Int) = withIO {
        page = 0
        RetrofitManager.getApiService(ApiService::class.java)
            .getSystemArticle(page,id)
            .data()
            .let {
                ArticleListBean.trans(it.datas?: mutableListOf())
            }
    }

    /**
     * 分页加载体系对应的文章
     */
    suspend fun loadMoreArticle(id:Int) = withIO {
        page++
        RetrofitManager.getApiService(ApiService::class.java)
            .getSystemArticle(page,id)
            .data()
            .let {
                ArticleListBean.trans(it.datas?: mutableListOf())
            }
    }
}