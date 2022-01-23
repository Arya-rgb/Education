package com.thorin.eduaps.data.source.remote

import com.google.firebase.auth.FirebaseAuth
import com.thorin.eduaps.data.source.remote.response.UserResponse

class RemoteDataSource {

    fun getDataUserProfile(callback: LoadUserCallback) {

        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

        val dataUser = mAuth.currentUser
        val photoUrl = dataUser?.photoUrl.toString()
        val namaUser = dataUser?.displayName.toString()
        val emailUser = dataUser?.email.toString()

        val retrieveDataUser = UserResponse(photoUrl, namaUser, emailUser)
        callback.onDataUserReceived(retrieveDataUser)

    }

    interface LoadUserCallback {

        fun onDataUserReceived(userResponse: UserResponse)

    }

}