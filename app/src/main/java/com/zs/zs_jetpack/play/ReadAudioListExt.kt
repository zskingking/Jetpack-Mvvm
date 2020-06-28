package com.zs.zs_jetpack.play

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import com.zs.zs_jetpack.play.bean.AudioBean
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * des 读取播放列表-扩展文件
 * @author zs
 * @data 2020/6/26
 */


/**
 * 通过ContentProvider读取本地音频文件
 */
fun readPlayList(context:Context,audioList:MutableList<AudioBean>){
    GlobalScope.launch {
        val cursor: Cursor? = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            , null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER
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
                //筛选大于一分钟的音频
                if (audioBean.duration>60000){
                    audioList.add(audioBean)
                }
            }
            cursor.close()
            Log.i("PlayList","$audioList")
        }
    }
}