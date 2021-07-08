package com.zs.base_library.common

import android.content.ContentUris
import android.net.Uri

/**
 * des 播放相关扩展方法
 * @author zs
 * @data 2020/6/26
 */

/**
 * 通过id获取专辑图片uri
 */
fun albumById(albumId:Long):Uri{
    return ContentUris.withAppendedId(
        Uri.parse("content://media/external/audio/albumart"),
        albumId
    )
}