package com.zs.zs_jetpack.ui.play.collect

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zs.zs_jetpack.play.PlayListType
import com.zs.zs_jetpack.play.bean.AudioBean
import com.zs.zs_jetpack.ui.play.history.HistoryAudioBean

/**
 * des mp3文件封装类,遵循mp3格式。播放历史
 * @author zs
 * @date 2020-06-24
 */
@Entity(tableName = "collect_audio")
class CollectAudioBean {

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
        return "\nAudioBean(name=$name, singer=$singer, size=$size, duration=$duration, path=$path, albumId=$albumId, id=$id)"
    }

    companion object {
        /**
         * room同一个bean好像不支持多张表(或者说支持是我不会用),只能单独创建个历史bean,收藏相同
         * 将HistoryAudioBean列表转换为AudioBean列表
         */
        fun collectList2AudioList(list: MutableList<CollectAudioBean>): MutableList<AudioBean> {
            return list.map {
                collect2Audio(it)
            }.toMutableList()
        }

        /**
         * 将AudioBean列表转换为HistoryAudioBean列表
         */
        fun audioList2CollectList(list: MutableList<AudioBean>): MutableList<CollectAudioBean> {
            return list.map {
                audio2Collect(it)
            }.toMutableList()
        }

        /**
         * 将AudioBean转换为HistoryAudioBean
         */
        fun collect2Audio(bean: CollectAudioBean): AudioBean {
            return AudioBean().apply {
                sortId = bean.sortId
                id = bean.id
                name = bean.name
                singer = bean.singer
                size = bean.size
                duration = bean.duration
                path = bean.path
                albumId = bean.albumId
                playListType = PlayListType.COLLECT_PLAY_LIST
            }
        }

        /**
         * 将AudioBean转换为HistoryAudioBean
         */
        fun audio2Collect(bean: AudioBean): CollectAudioBean {
            return CollectAudioBean().apply {
                sortId = bean.sortId
                id = bean.id
                name = bean.name
                singer = bean.singer
                size = bean.size
                duration = bean.duration
                path = bean.path
                albumId = bean.albumId
                playListType = PlayListType.COLLECT_PLAY_LIST
            }
        }
    }
}