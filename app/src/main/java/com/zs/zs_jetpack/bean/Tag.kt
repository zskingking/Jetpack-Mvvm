package com.zs.zs_jetpack.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by xiaojianjun on 2019-11-07.
 */
@Parcelize
data class Tag(
    var id: Long,
    var articleId: Long,
    var name: String?,
    var url: String?
) : Parcelable