package com.thorin.eduaps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thorin.eduaps.data.EducationRepository
import com.thorin.eduaps.data.source.remote.response.ProgressResponse
import com.thorin.eduaps.data.source.remote.response.UserResponse

class ProgressViewModel(private val educationRepository: EducationRepository): ViewModel() {

    fun getProgress(): LiveData<ProgressResponse> = educationRepository.getProgress()

}