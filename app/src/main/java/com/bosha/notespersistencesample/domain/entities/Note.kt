package com.bosha.notespersistencesample.domain.entities

import android.os.Parcelable
import androidx.annotation.ColorRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val title: String,
    @ColorRes val colorId: Int,
    var createDate: Long,
    var id: String,
    val description: String,
    val priority: Int,
    val type: Int,
):Parcelable {
    enum class Priority{
        LOW ,
        MEDIUM,
        HIGH
    }
    enum class Type{
        DO,
        DOING,
        DONE
    }
}
