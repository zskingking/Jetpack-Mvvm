package com.zs.zs_jetpack.play.bean

import com.zs.zs_jetpack.play.PlayListType

/**
 * des mp3文件封装类,遵循mp3格式
 * @author zs
 * @date 2020-06-24
 */
class AudioBean {
    /**
     * 歌曲名
     */
    var name: String? = null

    /**
     * 歌手
     */
    var singer: String? = null

    /**
     * 歌曲所占空间大小
     */
    var size: Long = 0

    /**
     * 歌曲时间长度
     */
    var duration = 0

    /**
     * 歌曲地址
     */
    var path: String? = null

    /**
     * 图片id
     */
    var albumId: Long = 0

    /**
     * 歌曲id
     */
    var id: Long = 0

    /**
     * 排序id
     */
    var sortId: Long = 0

    /**
     * 所属播放列表
     */
    var playListType = PlayListType.LOCAL_PLAY_LIST

    override fun toString(): String {
        return "\nAudioBean(sortId=$sortId,name=$name, singer=$singer, size=$size, duration=$duration, path=$path, albumId=$albumId, id=$id)"
    }

    fun copy(bean: AudioBean):AudioBean{
        return AudioBean().apply {
            sortId = bean.sortId
            id = bean.id
            name = bean.name
            singer = bean.singer
            size = bean.size
            duration = bean.duration
            path = bean.path
            albumId = bean.albumId
        }
    }

}