package com.thorin.eduaps.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TestQuestioner(
    var Soal: String? = "",
    var kunci_jawaban: String? = "",
    var opsi_1: String? = "",
    var opsi_2: String? = "",
    var opsi_3: String? = "",
    var opsi_4: String? = "",
    var opsi_5: String? = "",
    var opsi_6: String? = ""
): Parcelable