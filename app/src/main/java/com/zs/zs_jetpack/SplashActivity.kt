package com.zs.zs_jetpack

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import android.Manifest
import android.content.Intent
import android.os.Bundle
import com.zs.base_library.base.BaseVmActivity
import com.zs.base_library.utils.PrefUtils
import com.zs.zs_jetpack.constants.Constants
import com.zs.zs_jetpack.play.PlayerManager
import com.zs.zs_jetpack.view.DialogUtils
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks


/**
 * 开屏页
 * 权限：https://www.cnblogs.com/blosaa/p/9584348.html
 *
 * @author zs
 * @date 2020-03-07
 */
class SplashActivity : BaseVmActivity(), PermissionCallbacks {

    private var disposable:Disposable? = null
    private val tips = "玩安卓现在要向您申请存储权限，用于存储历史记录以及保存小姐姐图片，您也可以在设置中手动开启或者取消。"
    private val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun init(savedInstanceState: Bundle?) {
        requestPermission()
    }

    /**
     * 申请权限
     */
    private fun requestPermission(){
        //已申请
        if (EasyPermissions.hasPermissions(this, *perms)) {
            startIntent()
        }else{
            //为申请，显示申请提示语
            DialogUtils.tips(this,tips){
                    RequestLocationAndCallPermission()
            }
        }
    }

    /**
     * 开始倒计时跳转
     */
    private fun startIntent(){
        //初始化播放器
        PlayerManager.instance.init(this)
        disposable = Observable.timer(2000,TimeUnit.MILLISECONDS)
            .subscribe {
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }


    override fun getLayoutId() = R.layout.activity_splash

    @AfterPermissionGranted(WRITE_EXTERNAL_STORAGE)
    private fun RequestLocationAndCallPermission() {
        val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        //数组中权限都已申请
        if (EasyPermissions.hasPermissions(this, *perms)) {
            startIntent()
        } else {
            EasyPermissions.requestPermissions(this, "请求写入权限", WRITE_EXTERNAL_STORAGE, *perms)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * 权限申请失败
     */
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }

    /**
     * 权限申请成功
     */
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        startIntent()
    }


    companion object {
        private const val WRITE_EXTERNAL_STORAGE = 100
    }
}
