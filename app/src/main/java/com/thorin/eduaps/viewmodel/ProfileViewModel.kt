package com.thorin.eduaps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thorin.eduaps.data.EducationRepository
import com.thorin.eduaps.data.source.remote.response.UserResponse

class ProfileViewModel(private val educationRepository: EducationRepository): ViewModel() {

    fun getUserProfile(): LiveData<UserResponse> = educationRepository.getDataUserProfile()

}