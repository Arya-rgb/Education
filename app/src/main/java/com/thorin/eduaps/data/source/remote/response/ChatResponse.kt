package com.thorin.eduaps.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatResponse(
    var isi_pesan: String? = "",
    var profile_photo: String? = "",
    var uid: String? = "",
    var username: String? = ""
): Parcelable