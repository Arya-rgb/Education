package com.thorin.eduaps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thorin.eduaps.data.EducationRepository
import com.thorin.eduaps.data.source.remote.response.ListPelajaranResponse

class PelajaranViewModel(private val educationRepository: EducationRepository): ViewModel() {

    fun getPelajaranData(): LiveData<List<ListPelajaranResponse>> = educationRepository.getDataSoal()

}