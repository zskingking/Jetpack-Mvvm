package com.zs.zs_jetpack.ui.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebResourceRequest
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.common.clickNoRepeat
import com.zs.base_library.utils.Param
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.databinding.FragmentWebBinding


/**
 * des 展示h5
 * @author zs
 * @date 2020-07-06修改
 */
class WebFragment : BaseVmFragment<FragmentWebBinding>() {

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
        binding.vm = webVM
        initView()
    }

    override fun initView() {
        binding.tvTitle.text = Html.fromHtml(title)
        binding.ivBack.clickNoRepeat {
            nav().navigateUp()
        }
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        val webSettings: WebSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
        //自适应屏幕
        binding.webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        binding.webView.settings.loadWithOverviewMode = true

        //如果不设置WebViewClient，请求会跳转系统浏览器
        binding.webView.webViewClient = object : WebViewClient() {

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

        binding.webView.loadUrl(loadUrl)

        //设置最大进度
        webVM?.maxProgress?.set(100)
        //webView加载成功回调
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
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
                    if (binding.webView.canGoBack()) {
                        //返回上个页面
                        binding.webView.goBack()
                    } else {
                        //退出H5界面
                        nav().navigateUp()
                    }

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }


    override fun getLayoutId() = R.layout.fragment_web
}
