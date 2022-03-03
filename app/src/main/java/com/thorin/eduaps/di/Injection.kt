package com.thorin.eduaps.di

import android.content.Context
import com.thorin.eduaps.data.EducationRepository
import com.thorin.eduaps.data.source.remote.RemoteDataSource

object Injection {

    fun provideRepository(context: Context): EducationRepository {

        val remoteDataSource = RemoteDataSource.getInstance()

        return EducationRepository.getInstance(remoteDataSource)
    }

}