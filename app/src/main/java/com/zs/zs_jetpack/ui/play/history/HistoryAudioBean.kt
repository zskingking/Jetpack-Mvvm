package com.zs.zs_jetpack.ui.play.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zs.zs_jetpack.play.PlayListType

/**
 * des mp3文件封装类,遵循mp3格式。播放历史
 * @author zs
 * @date 2020-06-24
 */
@Entity(tableName = "history_audio")
class HistoryAudioBean {

    /**
     * 歌曲id
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    /**
     * 歌曲名
     */
    @ColumnInfo(name = "name")
    var name: String? = null

    /**
     * 歌手
     */
    @ColumnInfo(name = "singer")
    var singer: String? = null

    /**
     * 歌曲所占空间大小
     */
    @ColumnInfo(name = "size")
    var size: Long = 0

    /**
     * 歌曲时间长度
     */
    @ColumnInfo(name = "duration")
    var duration = 0

    /**
     * 歌曲地址
     */
    @ColumnInfo(name = "path")
    var path: String? = null

    /**
     * 图片id
     */
    @ColumnInfo(name = "albumId")
    var albumId: Long = 0

    /**
     * 所属播放列表
     */
    @ColumnInfo(name = "playListType")
    var playListType = PlayListType.LOCAL_PLAY_LIST

    override fun toString(): String {
        return "\nAudioBean(name=$name, singer=$singer, size=$size, duration=$duration, path=$path, albumId=$albumId, id=$id)"
    }
}