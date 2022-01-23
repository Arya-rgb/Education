package com.thorin.eduaps.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thorin.eduaps.data.EducationRepository
import com.thorin.eduaps.di.Injection
import com.thorin.eduaps.viewmodel.HomeViewModel
import com.thorin.eduaps.viewmodel.ProfileViewModel

class ViewModelFactory private constructor(private val mEducationRepository: EducationRepository): ViewModelProvider.NewInstanceFactory() {

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository()).apply{
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(mEducationRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(mEducationRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class : "+modelClass.name)
        }
    }
}