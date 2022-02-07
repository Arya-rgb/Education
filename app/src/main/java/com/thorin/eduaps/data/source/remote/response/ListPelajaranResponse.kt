package com.thorin.eduaps.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListPelajaranResponse(
    var id_soal: String? = "",
    var judul_pelajaran: String? = "",
    var jenis_file: String? = "",
    var link_file: String? = "",
    var deskripsi_pelajaran: String? = "",
    var jumlah_slide: String? = ""
): Parcelable