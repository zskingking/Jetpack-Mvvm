package com.zs.zs_jetpack.ui.play

import android.net.Uri
import android.os.Bundle
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.common.albumById
import com.zs.base_library.common.loadBlurTrans
import com.zs.base_library.common.setNoRepeatClick
import com.zs.base_library.common.stringForTime
import com.zs.base_library.utils.StatusUtils
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.play.AudioObserver
import com.zs.zs_jetpack.play.PlayList
import com.zs.zs_jetpack.play.PlayerManager
import com.zs.zs_jetpack.play.bean.AudioBean
import kotlinx.android.synthetic.main.fragment_player.*


/**
 * des 播放
 * @author zs
 * @date 2020-06-25
 */
open class PlayerFragment : BaseVmFragment(), AudioObserver {

    private var playVM: PlayVM? = null
    private val playListFragment = PlayListFragment()
    override fun init(savedInstanceState: Bundle?) {
        PlayerManager.instance.register(this)
        setMarginTop()
        initSeek()
    }

    /**
     * 设置顶部信息marginTop,适配状态栏高度
     */
    private fun setMarginTop() {
        val params = ivBack.layoutParams as ConstraintLayout.LayoutParams
        params.topMargin = StatusUtils.getStatusBarHeight(mContext)
        ivBack.layoutParams = params
    }

    /**
     * 设置背景高斯模糊
     */
    private fun setBgBlur(uri: Uri) {
        ivBackground.loadBlurTrans(mContext, uri, 90)
    }

    /**
     * 初始化seekBar
     */
    private fun initSeek() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                tvStartTime.text = stringForTime(seekBar.progress)
            }

            /**
             * 拖动放开
             */
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                PlayerManager.instance.seekTo(seekBar.progress)
            }
        })
    }

    override fun initViewModel() {
        playVM = getActivityViewModel(PlayVM::class.java)
    }

    override fun getLayoutId(): Int? {
        return R.layout.fragment_player
    }

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_player, playVM)
            .addBindingParam(BR.vm, playVM)
    }

    override fun onClick() {
        setNoRepeatClick(ivBack,ivMode, ivPrevious, ivPlay, ivNext, ivList) {
            when (it.id) {
                R.id.ivBack -> {
                    findNavController().popBackStack()
                }
                R.id.ivMode -> {
                    PlayerManager.instance.switchPlayMode()
                }
                R.id.ivPrevious -> {
                    PlayerManager.instance.previous()
                }
                R.id.ivPlay -> {
                    PlayerManager.instance.controlPlay()
                }
                R.id.ivNext -> {
                    PlayerManager.instance.next()
                }
                R.id.ivList -> {
                    playListFragment.show(mActivity.supportFragmentManager,"")
                }
            }
        }
    }

    /**
     * 观察播放信息
     */
    override fun onAudioBean(audioBean: AudioBean) {
        setBgBlur(albumById(audioBean.albumId))
        tvSinger.text = audioBean.singer
        tvSongName.text = audioBean.name
        tvEndTime.text = stringForTime(audioBean.duration)
        seekBar.max = audioBean.duration
    }

    /**
     * 观察播放状态
     */
    override fun onPlaying(playing: Boolean) {
        if (playing) {
            ivPlay.setImageResource(R.mipmap.play_resume)
        } else {
            ivPlay.setImageResource(R.mipmap.play_pause)
        }
    }

    /**
     * 观察播放进度
     * @param currentDuration 当前播放到的事件
     * @param totalDuration   总时长
     */
    override fun onProgress(currentDuration: Int, totalDuration: Int) {
        seekBar.progress = currentDuration
        tvStartTime.text = stringForTime(currentDuration)
    }

    /**
     * 观察播放模式
     */
    override fun onPlayMode(playMode: Int) {
        when (playMode) {
            PlayList.PlayMode.ORDER_PLAY_MODE -> {
                ivMode.setImageResource(R.mipmap.play_order)
            }
            PlayList.PlayMode.SINGLE_PLAY_MODE -> {
                ivMode.setImageResource(R.mipmap.play_single)
            }
            PlayList.PlayMode.RANDOM_PLAY_MODE -> {
                ivMode.setImageResource(R.mipmap.play_random)
            }
        }
    }

    /**
     * 设置状态栏背景颜色
     */
    override fun setSystemInvadeBlack() {
        StatusUtils.setSystemStatus(mActivity, true, false)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        PlayerManager.instance.unregister(this)
    }

}
