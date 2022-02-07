package com.thorin.eduaps.data.source.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.thorin.eduaps.data.source.remote.response.ChatResponse
import com.thorin.eduaps.data.source.remote.response.ListPelajaranResponse
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

    interface LoadPretest2Callback {
        fun onPretest2Received(preTestQuestioner: List<TestQuestioner>)
    }

    fun getDataPelajaran(callback: LoadDataPelajaranCallback) {
        callback.onDataPelajaranReceived(jsonHelper.loadListPelajaran())
    }

    interface LoadDataPelajaranCallback {
        fun onDataPelajaranReceived(listPelajaranResponse: List<ListPelajaranResponse>)
    }


    fun getChatData(label: String, callback: LoadChatDataCallBack): List<ChatResponse> {
        val list = ArrayList<ChatResponse>()
        val reff: DatabaseReference =
            FirebaseDatabase.getInstance().getReference(label)
        reff.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val dataChat = dataSnapshot.getValue(ChatResponse::class.java)
                        list.add(dataChat!!)
                        callback.onDataChatReceived(list)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "error: " + error.message)
            }

        })
        return list
    }

    interface LoadChatDataCallBack {
        fun onDataChatReceived(chatResponse: List<ChatResponse>)
    }

}

