package com.zs.zs_jetpack.ui.collect

import android.text.Html
import com.zs.base_library.base.BaseRepository
import com.zs.base_wa_lib.article.ArticleListBean
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager

/**
 * des 收藏的文章
 * @date 2020/7/8
 * @author zs
 */
class CollectListRepo : BaseRepository() {

    private var page = 0

    /**
     * 请求第一页
     */
    suspend fun getCollectArticle() = withIO {
        page = 0
        RetrofitManager.getApiService(ApiService::class.java)
            .getCollectData(page)
            .data()
            .let {
                transByCollect(it.datas?: mutableListOf())
            }
    }

    /**
     * 请求下一页
     */
    suspend fun loadMoreCollectArticle() = withIO {
        page++
        RetrofitManager.getApiService(ApiService::class.java)
            .getCollectData(page)
            .data()
            .let {
                transByCollect(it.datas?: mutableListOf())
            }
    }

    /**
     * 取消收藏
     */
    suspend fun unCollect(id:Int) = withIO {
        RetrofitManager.getApiService(ApiService::class.java)
            .unCollect(id)
            //如果data可能为空,可通过此方式通过反射生成对象,避免空判断
            .data(Any::class.java)
    }

    private  fun transByCollect(list: MutableList<CollectBean.DatasBean>): MutableList<ArticleListBean> {
        return list.map {
            ArticleListBean().apply {
                id = it.originId
                author = it.author
                collect = true
                desc = it.desc
                picUrl = it.envelopePic
                link = it.link
                date = it.niceDate
                title = Html.fromHtml(it.title).toString()
                articleTag = it.chapterName
                topTitle = ""
            }
        }.toMutableList()
    }
}