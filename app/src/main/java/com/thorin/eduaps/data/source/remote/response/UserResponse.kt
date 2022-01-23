package com.thorin.eduaps.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    var photoUrl: String,
    var nameUser: String,
    var emailUser: String,
): Parcelable
