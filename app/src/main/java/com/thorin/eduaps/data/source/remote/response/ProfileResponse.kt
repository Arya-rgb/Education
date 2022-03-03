package com.thorin.eduaps.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileResponse(
    var nama_responden: String? = "",
    var alamat_responden: String? = "",
): Parcelable