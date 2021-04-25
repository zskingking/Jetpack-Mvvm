package com.zs.base_library.common

import android.content.ClipboardManager
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.os.Parcelable
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * des 视图扩展方法
 * @date 2020/5/14
 * @author zs
 */

/**
 * viewPager2适配fragment
 */
fun ViewPager2.initFragment(
    fragment: Fragment,
    fragments: MutableList<Fragment>
): ViewPager2 {
    //设置适配器
    adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
    return this
}

/**
 * ViewPager于fragment绑定
 * 通过BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT支持懒加载
 */
fun ViewPager.initFragment(
    manager: FragmentManager,
    fragments: MutableList<Fragment>
): ViewPager {
    //设置适配器
    adapter = object : FragmentStatePagerAdapter(
        manager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
        override fun getCount() = fragments.size

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun saveState(): Parcelable? {
            return null
        }
    }
    return this
}

/**
 * ViewPager选中
 */
fun ViewPager.doSelected(selected: (Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            selected.invoke(position)
        }

    })
}


/**
 * 防止重复点击,可同时注册多个view
 */
fun setNoRepeatClick(vararg views: View, interval: Long = 400, onClick: (View) -> Unit) {
    views.forEach {
        it.clickNoRepeat(interval = interval) { view ->
            onClick.invoke(view)
        }
    }
}

/**
 * 防止重复点击
 * @param interval 重复间隔
 * @param onClick  事件响应
 */
var lastTime = 0L
fun View.clickNoRepeat(interval: Long = 400, onClick: (View) -> Unit) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastTime != 0L && (currentTime - lastTime < interval)) {
            return@setOnClickListener
        }
        lastTime = currentTime
        onClick(it)
    }
}

/**
 * 复制剪切板
 */
fun copy(context: Context, msg: String) {
    val clip = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clip.text = msg
    toast("已复制")
}

/**
 * 隐藏刷新加载ui
 */
fun SmartRefreshLayout.smartDismiss() {
    finishRefresh(0)
    finishLoadMore(0)
}

/**
 * 配置SmartRefreshLayout
 */
fun SmartRefreshLayout.smartConfig() {
    //加载
    setEnableLoadMore(true)
    //刷新
    setEnableRefresh(true)
    //不满一页关闭加载
    //setEnableLoadMoreWhenContentNotFull(false)
    //滚动回弹
    setEnableOverScrollDrag(true)
}

/**
 * 获取当前主图颜色属性
 */
fun Context.getThemeColor(attr: Int): Int {
    val array: TypedArray = theme.obtainStyledAttributes(
        intArrayOf(
            attr
        )
    )
    val color = array.getColor(0, -0x50506)
    array.recycle()
    return color
}


/**
 * editText搜索按钮
 * @param onClick 搜索点击事件
 */
fun EditText.keyBoardSearch(onClick: () -> Unit) {
    //添加搜索按钮
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            onClick()
        } else {
            toast("请输入关键字")
            return@setOnEditorActionListener false
        }
        return@setOnEditorActionListener true
    }
}





