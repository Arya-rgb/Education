package com.thorin.eduaps.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thorin.eduaps.data.source.remote.RemoteDataSource
import com.thorin.eduaps.data.source.remote.response.UserResponse

class EducationRepository private constructor(private val remoteDataSource: RemoteDataSource): EducationDataSource {

    companion object{
        @Volatile
        private var instance: EducationRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): EducationRepository =
            instance?: synchronized(this) {
                instance?:EducationRepository(remoteDataSource).apply { instance = this }
            }
    }

    override fun getDataUserProfile(): LiveData<UserResponse> {
        val userProfileResult = MutableLiveData<UserResponse>()

        remoteDataSource.getDataUserProfile(object : RemoteDataSource.LoadUserCallback{
            override fun onDataUserReceived(userResponse: UserResponse) {
                userProfileResult.postValue(userResponse)
            }
        })

        return userProfileResult

    }

}