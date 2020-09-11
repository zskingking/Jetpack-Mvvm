package com.zs.zs_jetpack.ui.search

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseRepository
import com.zs.base_library.common.isListEmpty
import com.zs.base_library.common.toast
import com.zs.base_library.http.ApiException
import com.zs.zs_jetpack.ui.main.home.BannerBean
import com.zs.zs_jetpack.bean.ArticleListBean
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager
import kotlinx.coroutines.CoroutineScope

/**
 * @author zs
 * @data 2020/7/11
 */
class SearchRepo(coroutineScope: CoroutineScope, errorLiveData: MutableLiveData<ApiException>) :
    BaseRepository(coroutineScope, errorLiveData) {

    private var page = 0

    /**
     * 搜索
     */
    fun search(
        isRefresh: Boolean, keyWord: String
        , articleLiveData: MutableLiveData<MutableList<ArticleListBean>>
        , emptyLiveData: MutableLiveData<Any>
    ) {
        launch(
            block = {
                if (isRefresh) {
                    page = 0
                } else {
                    page++
                }
                RetrofitManager.getApiService(ApiService::class.java)
                    .search(page, keyWord)
                    .data()
            },
            success = {
                //处理刷新/分页数据
                articleLiveData.value.apply {
                    //第一次加载 或 刷新 给 articleLiveData 赋予一个空集合
                    val currentList = if (isRefresh || this == null) {
                        mutableListOf()
                    } else {
                        this
                    }
                    it.datas?.let { it1 -> currentList.addAll(ArticleListBean.trans(it1)) }
                    articleLiveData.postValue(currentList)
                }
                if (isListEmpty(it.datas)) {
                    //第一页并且数据为空
                    if (page == 0) {
                        emptyLiveData.postValue(Any())
                    } else {
                        toast("没有数据啦～")
                    }
                }
            }
        )
    }


    /**
     * 获取banner
     */
    private fun getBanner(banner: MutableLiveData<MutableList<BannerBean>>) {
        launch(
            block = {
                RetrofitManager.getApiService(ApiService::class.java)
                    .getBanner()
                    .data()
            },
            success = {
                banner.postValue(it)
            }
        )
    }

    /**
     * 收藏
     */
    fun collect(articleId:Int,articleList : MutableLiveData<MutableList<ArticleListBean>>){
        launch(
            block = {
                RetrofitManager.getApiService(ApiService::class.java)
                    .collect(articleId)
                    .data(Any::class.java)
            },
            success = {
                //此处直接更改list中模型,ui层会做diff运算做比较
                articleList.value = articleList.value?.map { bean->
                    if (bean.id == articleId){
                        //拷贝一个新对象，将点赞状态置换。kotlin没找到复制对象的函数,有知道的麻烦告知一下～～～
                        ArticleListBean().apply {
                            id = bean.id
                            author = bean.author
                            collect = true
                            desc = bean.desc
                            picUrl = bean.picUrl
                            link = bean.link
                            date = bean.date
                            title = bean.title
                            articleTag = bean.articleTag
                            topTitle = bean.topTitle
                        }
                    }else{
                        bean
                    }
                }?.toMutableList()
            }
        )
    }

    /**
     * 收藏
     */
    fun unCollect(articleId:Int,articleList : MutableLiveData<MutableList<ArticleListBean>>){
        launch(
            block = {
                RetrofitManager.getApiService(ApiService::class.java)
                    .unCollect(articleId)
                    //如果data可能为空,可通过此方式通过反射生成对象,避免空判断
                    .data(Any::class.java)
            },
            success = {
                //此处直接更改list中模型,ui层会做diff运算做比较
                articleList.value = articleList.value?.map { bean->
                    if (bean.id == articleId){
                        //拷贝一个新对象，将点赞状态置换。kotlin没找到复制对象的函数,有知道的麻烦告知一下～～～
                        ArticleListBean().apply {
                            id = bean.id
                            author = bean.author
                            collect = false
                            desc = bean.desc
                            picUrl = bean.picUrl
                            link = bean.link
                            date = bean.date
                            title = bean.title
                            articleTag = bean.articleTag
                            topTitle = bean.topTitle
                        }
                    }else{
                        bean
                    }
                }?.toMutableList()
            }
        )
    }
}