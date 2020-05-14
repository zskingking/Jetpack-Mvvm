package com.zs.zs_jetpack

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.zs.base_library.base.BaseVmActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * dee 主页面，空壳
 * @author zs
 * @date 2020-05-12
 */
class MainActivity : BaseVmActivity() {

    override fun init(savedInstanceState: Bundle?) {
    }

    override fun getLayoutId(): Int? {
        return R.layout.activity_main
    }
}
