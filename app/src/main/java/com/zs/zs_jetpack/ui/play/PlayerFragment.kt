package com.zs.zs_jetpack.ui.play

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
 * A simple [Fragment] subclass.
 */
class PlayerFragment : BaseVmFragment() ,AudioObserver{

    private var playVM:PlayVM? = null


    override fun init(savedInstanceState: Bundle?) {
        Log.i("PlayerFragment","register")
        PlayerManager.instance.register(this)
    }

    override fun initViewModel() {
        playVM = getActivityViewModel(PlayVM::class.java)
    }

    override fun getLayoutId(): Int? {
        return R.layout.fragment_player
    }

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_home, playVM)
            .addBindingParam(BR.vm, playVM)
    }

    override fun onClick() {
        setNoRepeatClick(tvPre,tvPlay,tvNext){
            when(it.id){
                R.id.tvPre->{
                    PlayerManager.instance.previous()
                }
                R.id.tvPlay->{
                    PlayerManager.instance.controlPlay()
                }
                R.id.tvNext->{
                    PlayerManager.instance.next()
                }
            }
        }
    }

    override fun onAudioBean(audioBean: AudioBean) {
        Log.i("PlayerFragment", audioBean.toString())
    }

    override fun onPlaying(playing: Boolean) {
        Log.i("PlayerFragment", "playing$playing")

    }

    override fun onProgress(progress: Int) {
    }

    override fun onPlayMode(playMode: Int) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("PlayerFragment","unregister")
        PlayerManager.instance.unregister(this)
    }

}
