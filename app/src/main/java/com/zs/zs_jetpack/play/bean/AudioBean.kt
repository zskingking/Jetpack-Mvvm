package com.zs.zs_jetpack.play.bean

/**
 * des mp3文件封装类,遵循mp3格式,从ContentProvider获取
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

    override fun toString(): String {
        return "AudioBean(name=$name, singer=$singer, size=$size, duration=$duration, path=$path, albumId=$albumId, id=$id)"
    }

}