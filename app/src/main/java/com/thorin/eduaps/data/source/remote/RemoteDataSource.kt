package com.thorin.eduaps.data.source.remote

import com.google.firebase.auth.FirebaseAuth
import com.thorin.eduaps.data.source.remote.response.TestQuestioner
import com.thorin.eduaps.data.source.remote.response.UserResponse
import com.thorin.eduaps.utils.JsonHelper

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {


    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource(helper).apply { instance = this }
            }
    }

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
    
    fun getTest2(callback: LoadPretest2Callback) {

        callback.onPretest2Received(jsonHelper.loadTest2())

    }

    interface LoadPretest2Callback{
        fun onPretest2Received(preTestQuestioner: List<TestQuestioner>)
    }



//    fun getDataTestProgress() {
//
//        val reff: DatabaseReference = FirebaseDatabase.getInstance().reference.child("bagian_dua_kuisioner")
//            reff.addValueEventListener( object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists()) {
//                        for (dataSnapshot in snapshot.children) {
//                            val soalData = dataSnapshot.getValue(Test2Questioner::class.java)
//                            list.add(soalData!!)
//                        }
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Log.d("error","error : "+error.message)
//                }
//            })
//    }
}