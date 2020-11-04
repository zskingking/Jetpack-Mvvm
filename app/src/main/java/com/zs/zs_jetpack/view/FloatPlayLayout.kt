package com.zs.zs_jetpack.view

import android.animation.ValueAnimator
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.zs.base_library.common.*
import com.zs.base_library.utils.ScreenUtils
import com.zs.zs_jetpack.R
import kotlinx.android.synthetic.main.play_float_layout.view.*


/**
 * des 自定义首页悬浮
 * @date 2020/6/28
 * @author zs
 */
class FloatPlayLayout : LinearLayout {

    /**
     * 是否展开
     */
    private var isOpen = false

    /**
     * content应该展示的宽度
     */
    private val contentWidth = dip2px(context, 180f)

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                moveY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                return true
            }
        }
        return false
    }

    private var moveY = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                var offsetY = translationY + (event.y - moveY)
                //因为translationY不管处于ViewGroup什么位置，初始值都为0，所以要买个top
                //最小值
                if (offsetY <  - top + dip2px(context,100f)) {
                    offsetY =  - top.toFloat() + dip2px(context,100f)
                }
                //最大值
                if (offsetY > ScreenUtils.getScreenHeight(context) - top - dip2px(context,150f)) {
                    offsetY = ScreenUtils.getScreenHeight(context) - top.toFloat() - dip2px(context,150f)
                }
                translationY = offsetY
                return true
            }
        }
        return super.onInterceptTouchEvent(event)
    }

    private fun initView(context: Context) {
        View.inflate(context, R.layout.play_float_layout, this)
        //居中显示
        gravity = Gravity.CENTER
        //设置阴影
        click()
    }

    /**
     * 事件
     */
    private fun click() {
        //音乐图片
        ivMusicPic.clickNoRepeat {
            //收缩状态进行展开动画
            if (!isOpen) {
                startAnim()
                ivMusicPic.isEnabled = false
                isOpen = true
            }
        }

        //x 号
        ivShrink.clickNoRepeat {
            //展开状态收缩
            if (isOpen) {
                startAnim()
                ivMusicPic.isEnabled = true
                isOpen = false
            }
        }
    }

    /**
     * 开启动画
     */
    private fun startAnim() {
        val animator = if (isOpen) {
            ValueAnimator.ofInt(contentWidth, 0)
        } else {
            ValueAnimator.ofInt(0, contentWidth)
        }
        //播放时长,尽量与防点击抖动间隔一致
        animator.duration = 249
        animator.addUpdateListener {
            val value = it.animatedValue as Int
            //平滑的，动态的设置宽度
            val params = llContent.layoutParams as MarginLayoutParams
            params.width = value
            llContent.layoutParams = params
        }
        animator.start()
    }

    /**
     * 播放点击事件
     */
    fun playClick(onClick: (View) -> Unit) {
        ivPlaying.clickNoRepeat {
            onClick.invoke(it)
        }
    }

    /**
     * 悬浮窗点击事件
     */
    fun rootClick(onClick: (View) -> Unit) {
        root.clickNoRepeat {
            onClick.invoke(it)
        }
    }

    /**
     * 设置播放状态
     */
    fun setImgPlaying(isPlying: Boolean?) {
        isPlying?.apply {
            ivPlaying.isSelected = this
        }
    }

    /**
     * 设置歌名
     */
    fun setSongName(songName: String?) {
        if (TextUtils.isEmpty(songName)) {
            tvSongName.text = "暂无播放"
        } else {
            tvSongName.text = songName
        }
        songName?.apply {
        }
    }

    /**
     * 设置专辑图片
     */
    fun setAlbumPic(albumId: Long?) {
        //收到重置
        if (albumId == -1L) {
            ivMusicPic.setImageResource(R.drawable.svg_music_not)
            return
        }
        albumId?.apply {
            ivMusicPic.loadCircle(context.applicationContext, albumById(this))
        }
    }
}