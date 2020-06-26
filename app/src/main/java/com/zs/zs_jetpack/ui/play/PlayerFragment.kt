package com.zs.zs_jetpack.ui.play

import android.os.Bundle
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.common.setNoRepeatClick
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.play.AudioObserver
import com.zs.zs_jetpack.play.PlayerManager
import com.zs.zs_jetpack.play.bean.AudioBean
import kotlinx.android.synthetic.main.fragment_player.*

/**
 * des 播放
 * @author zs
 * @date 2020-06-25
 */
class PlayerFragment : BaseVmFragment() ,AudioObserver{

    private var playVM:PlayVM? = null

    override fun init(savedInstanceState: Bundle?) {
        PlayerManager.instance.register(this)
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
        setNoRepeatClick(ivPrevious,ivPlay,ivNext){
            when(it.id){
                R.id.ivPrevious->{
                    PlayerManager.instance.previous()
                }
                R.id.ivPlay->{
                    PlayerManager.instance.controlPlay()
                }
                R.id.ivNext->{
                    PlayerManager.instance.next()
                }
            }
        }
    }

    /**
     * 观察播放信息
     */
    override fun onAudioBean(audioBean: AudioBean) {

    }

    /**
     * 观察播放状态
     */
    override fun onPlaying(playing: Boolean) {
        if (playing){
            ivPlay.setImageResource(R.mipmap.play_resume)
        }else{
            ivPlay.setImageResource(R.mipmap.play_pause)
        }
    }

    /**
     * 观察播放进度
     */
    override fun onProgress(currentDuration: Int) {

    }

    /**
     * 观察播放模式
     */
    override fun onPlayMode(playMode: Int) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        PlayerManager.instance.unregister(this)
    }

}
