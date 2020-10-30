package com.zs.zs_jetpack.ui.play.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zs.zs_jetpack.play.PlayListType
import com.zs.zs_jetpack.play.bean.AudioBean

/**
 * des mp3文件封装类,遵循mp3格式。播放历史
 * @author zs
 * @date 2020-06-24
 */
@Entity(tableName = "history_audio")
class HistoryAudioBean {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sortId")
    var sortId: Long = 0

    /**
     * 歌曲id
     */
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
        return "\nAudioBean(sortId=$sortId,name=$name, singer=$singer, size=$size, duration=$duration, path=$path, albumId=$albumId, id=$id)"
    }

    companion object {
        /**
         * room同一个bean好像不支持多张表(或者说支持是我不会用),只能单独创建个历史bean,收藏相同
         * 将HistoryAudioBean列表转换为AudioBean列表
         */
        fun historyList2AudioList(list: MutableList<HistoryAudioBean>): MutableList<AudioBean> {
            return list.map {
                history2Audio(it)
            }.toMutableList()
        }

        /**
         * 将AudioBean列表转换为HistoryAudioBean列表
         */
        fun audioList2HistoryList(list: MutableList<AudioBean>): MutableList<HistoryAudioBean> {
            return list.map {
                audio2History(it)
            }.toMutableList()
        }

        /**
         * 将AudioBean转换为HistoryAudioBean
         */
        fun history2Audio(bean: HistoryAudioBean): AudioBean {
            return AudioBean().apply {
                sortId = bean.sortId
                id = bean.id
                name = bean.name
                singer = bean.singer
                size = bean.size
                duration = bean.duration
                path = bean.path
                albumId = bean.albumId
                playListType = PlayListType.HISTORY_PLAY_LIST
            }
        }

        /**
         * 将AudioBean转换为HistoryAudioBean
         */
        fun audio2History(bean: AudioBean): HistoryAudioBean {
            return HistoryAudioBean().apply {
                sortId = bean.sortId
                id = bean.id
                name = bean.name
                singer = bean.singer
                size = bean.size
                duration = bean.duration
                path = bean.path
                albumId = bean.albumId
                playListType = PlayListType.HISTORY_PLAY_LIST
            }
        }
    }
}