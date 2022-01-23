package com.thorin.eduaps.di

import com.thorin.eduaps.data.EducationRepository
import com.thorin.eduaps.data.source.remote.RemoteDataSource

object Injection {

    fun provideRepository(): EducationRepository {
        val remoteDataSource = RemoteDataSource()

        return EducationRepository.getInstance(remoteDataSource)
    }

}