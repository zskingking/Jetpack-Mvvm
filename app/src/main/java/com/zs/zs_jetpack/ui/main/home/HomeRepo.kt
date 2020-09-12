package com.zs.zs_jetpack.ui.main.home

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseRepository
import com.zs.base_library.http.ApiException
import com.zs.zs_jetpack.bean.ArticleListBean
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 * des 首页
 * @date 2020/7/6
 * @author zs
 */
class HomeRepo(coroutineScope: CoroutineScope, errorLiveData: MutableLiveData<ApiException>) :
    BaseRepository(coroutineScope, errorLiveData) {

    private var page = 0

    /**
     * 获取首页文章列表,第一页
     */
    suspend fun getArticle(): Flow<MutableList<ArticleListBean>> {
        return flow<MutableList<ArticleListBean>> {
            emit(
                //请求置顶
                RetrofitManager.getApiService(ApiService::class.java)
                    .getTopList()
                    .data()
                    .let {
                        //对模型转换
                        ArticleListBean.trans(it)
                    }
            )
        }.zip(
            flow<MutableList<ArticleListBean>> {
                emit(
                    //请求第一页
                    RetrofitManager.getApiService(ApiService::class.java)
                        .getHomeList(page)
                        .data()
                        .datas?.let {
                            ArticleListBean.trans(it)
                        }?: mutableListOf<ArticleListBean>()
                )
            }
        ) { a, b ->
            a.apply {
                //合并请求结果
                addAll(b)
            }
        }.flowOn(Dispatchers.IO)
    }

    /**
     * 分页获取首页文章列表
     */
    suspend fun loadMoreArticle(): Flow<MutableList<ArticleListBean>> {
        page++
        return flow {

            RetrofitManager.getApiService(ApiService::class.java)
                .getHomeList(page)
                .data()
                .datas?.let {
                    ArticleListBean.trans(it)
                }
                ?.let { res ->
                    emit(res)
                }

        }.flowOn(Dispatchers.IO)
    }


    /**
     * 获取banner
     */
    suspend fun getBanner(): Flow<MutableList<BannerBean>> {
        return flow {
            emit(
                RetrofitManager.getApiService(ApiService::class.java)
                    .getBanner()
                    .data()
            )
        }.flowOn(Dispatchers.IO)
    }

    /**
     * 收藏
     */
    fun collect(id: Int,list:MutableList<ArticleListBean>)
        : Flow<MutableList<ArticleListBean>> {
            return flow {
                RetrofitManager.getApiService(ApiService::class.java)
                    .collect(id)
                    .data(Any::class.java)
                emit(
                    list.map {
                        //将收藏的对象做替换，并改变收藏状态
                        if (it.id == id){
                            ArticleListBean.copy(it).apply {
                                collect = true
                            }
                        }else{
                            it
                        }
                    }.toMutableList()
                )
            }.flowOn(Dispatchers.IO)
    }

    /**
     * 取消收藏
     */
    fun unCollect(id: Int,list:MutableList<ArticleListBean>)
            : Flow<MutableList<ArticleListBean>> {
        return flow {
            RetrofitManager.getApiService(ApiService::class.java)
                .unCollect(id)
                .data(Any::class.java)
            emit(
                list.map {
                    //将收藏的对象做替换，并改变收藏状态
                    if (it.id == id){
                        ArticleListBean.copy(it).apply {
                            collect = false
                        }
                    }else{
                        it
                    }
                }.toMutableList()
            )
        }.flowOn(Dispatchers.IO)
    }
}
