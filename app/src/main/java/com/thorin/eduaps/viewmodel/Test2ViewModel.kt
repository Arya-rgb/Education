package com.thorin.eduaps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thorin.eduaps.data.EducationRepository
import com.thorin.eduaps.data.source.remote.response.TestQuestioner

class Test2ViewModel(private val educationRepository: EducationRepository): ViewModel() {

    fun getTest2Data(): LiveData<List<TestQuestioner>> = educationRepository.getTest2()

}