package com.thorin.eduaps.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProgressResponse(
    var Status_Belajar: String? = "",
    var Status_Post_Test: String? = "",
    var Status_Pre_Test: String? = "",
): Parcelable