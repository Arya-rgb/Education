package com.thorin.eduaps.data

import androidx.lifecycle.LiveData
import com.thorin.eduaps.data.source.remote.response.*

interface EducationDataSource {

    fun getDataUserProfile(): LiveData<UserResponse>

    fun getTest2(): LiveData<List<TestQuestioner>>

    fun getChatData(label: String): LiveData<List<ChatResponse>>

    fun getProgress(): LiveData<ProgressResponse>

    fun getDataSoal(): LiveData<List<ListPelajaranResponse>>

    fun getUserData(): LiveData<ProfileResponse>

}