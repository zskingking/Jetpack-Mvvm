package com.zs.zs_jetpack.play

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import com.zs.zs_jetpack.db.AppDataBase
import com.zs.zs_jetpack.play.bean.AudioBean
import com.zs.zs_jetpack.ui.play.collect.CollectAudioBean
import com.zs.zs_jetpack.ui.play.history.HistoryAudioBean

/**
 * des 读取播放列表-扩展文件
 * @author zs
 * @data 2020/6/26
 */


/**
 * 通过ContentProvider读取本地音频文件
 */
fun readLocalPlayList(context: Context): MutableList<AudioBean> {
    val audioList = mutableListOf<AudioBean>()

    val cursor: Cursor? = context.contentResolver.query(
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        null,
        null,
        null,
        MediaStore.Audio.Media.DEFAULT_SORT_ORDER
    )
    if (cursor != null) {
        while (cursor.moveToNext()) {
            val audioBean = AudioBean()
            audioBean.name =
                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            audioBean.id =
                cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
            audioBean.singer =
                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            audioBean.path =
                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
            audioBean.duration =
                cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
            audioBean.size =
                cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE))
            audioBean.albumId =
                cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
            //设置播放列表
            audioBean.playListType = PlayListType.LOCAL_PLAY_LIST
            //筛选大于一分钟的音频
            if (audioBean.duration > 60000) {
                audioList.add(audioBean)
            }
        }
        cursor.close()
    }
    return audioList
}

/**
 * 获取历史列表
 */
fun readHistoryPlayList(): MutableList<AudioBean> {
    AppDataBase.getInstance().historyDao().getAllAudios()?.let {
        return HistoryAudioBean.historyList2AudioList(it)
    }?: return mutableListOf()
}

/**
 * 获取收藏列表
 */
fun readCollectPlayList(): MutableList<AudioBean> {
    AppDataBase.getInstance().collectDao().getAllAudios()?.let {
        return CollectAudioBean.collectList2AudioList(it)
    }?: return mutableListOf()
}
