package com.thorin.eduaps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thorin.eduaps.data.EducationRepository
import com.thorin.eduaps.data.source.remote.response.ChatResponse
import com.thorin.eduaps.data.source.remote.response.ListPelajaranResponse

class ChatViewModel(private val educationRepository: EducationRepository): ViewModel() {

    fun getChatData(label : String): LiveData<List<ChatResponse>> = educationRepository.getChatData(label)

}