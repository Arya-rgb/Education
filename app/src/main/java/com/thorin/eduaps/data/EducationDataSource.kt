package com.thorin.eduaps.data

import androidx.lifecycle.LiveData
import com.thorin.eduaps.data.source.remote.response.TestQuestioner
import com.thorin.eduaps.data.source.remote.response.UserResponse

interface EducationDataSource {

    fun getDataUserProfile(): LiveData<UserResponse>

    fun getTest2(): LiveData<List<TestQuestioner>>



}