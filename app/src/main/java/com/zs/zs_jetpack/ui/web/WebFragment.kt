package com.zs.zs_jetpack.ui.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebResourceRequest
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.common.setElevation
import com.zs.base_library.common.setNoRepeatClick
import com.zs.base_library.utils.Param
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R
import kotlinx.android.synthetic.main.fragment_web.*


/**
 * des 展示h5
 * @author zs
 * @date 2020-07-06修改
 */
class WebFragment : BaseVmFragment() {

    /**
     * 通过注解接收参数
     * url
     */
    @Param
    private var loadUrl: String? = null
    /**
     * 文章标题
     */
    @Param
    private var title: String? = null

    /**
     * 文章id
     */
    @Param
    private var id: Int? = -1

    /**
     * 作者
     */
    @Param
    private var author: String? = null

    private var webVM: WebVM? = null

    override fun initViewModel() {
        webVM = getActivityViewModel(WebVM::class.java)
    }

    override fun init(savedInstanceState: Bundle?) {
        initView()
    }

    override fun initView() {
        setElevation(llTitle,6f)
        tvTitle.text = Html.fromHtml(title)
        setNoRepeatClick(ivBack) {
            when (it.id) {
                R.id.ivBack -> nav().navigateUp()

            }
        }
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        //自适应屏幕
        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webView.settings.loadWithOverviewMode = true

        //如果不设置WebViewClient，请求会跳转系统浏览器
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址）
                //均交给webView自己处理，这也是此方法的默认处理
                return false
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址）
                //均交给webView自己处理，这也是此方法的默认处理
                return false
            }
        }

        webView?.loadUrl(loadUrl)

        //设置最大进度
        webVM?.maxProgress?.set(100)
        //webView加载成功回调
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                Log.i("webView","newProgress--$newProgress")
                //进度小于100，显示进度条
                if (newProgress<100){
                    webVM?.isVisible?.set(true)
                }
                //等于100隐藏
                else if (newProgress==100){
                    webVM?.isVisible?.set(false)
                }
                //改变进度
                webVM?.progress?.set(newProgress)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //自定义返回
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (webView.canGoBack()) {
                        //返回上个页面
                        webView.goBack()
                    } else {
                        //退出H5界面
                        nav().navigateUp()
                    }

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }


    override fun getLayoutId() = R.layout.fragment_web

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_search, webVM)
            .addBindingParam(BR.vm, webVM)
    }


}
