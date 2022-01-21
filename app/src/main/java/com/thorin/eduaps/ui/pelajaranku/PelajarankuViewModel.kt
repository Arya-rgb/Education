package com.thorin.eduaps.ui.pelajaranku

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PelajarankuViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Pelajaran Fragment"
    }
    val text: LiveData<String> = _text
}