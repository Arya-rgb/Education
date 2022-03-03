package com.thorin.eduaps.data.source.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.thorin.eduaps.data.source.remote.response.*

class RemoteDataSource private constructor() {


    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource().apply { instance = this }
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

    fun getTest2(callback: LoadPretest2Callback): List<TestQuestioner> {

        val list = ArrayList<TestQuestioner>()
        val reff: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("bagian_dua_kuisioner")
        reff.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val dataChat = dataSnapshot.getValue(TestQuestioner::class.java)
                        list.add(dataChat!!)
                        callback.onPretest2Received(list)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "error: " + error.message)
            }

        })
        return list

    }

    interface LoadPretest2Callback {
        fun onPretest2Received(preTestQuestioner: List<TestQuestioner>)
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

    fun getUserName(callback: DataUserNameCallback) {
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val list = ArrayList<ProfileResponse>()
        val reff: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("data_demografi")
                .child(mAuth.currentUser?.uid.toString())
        reff.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val alamatResponden = snapshot.child("alamat_responden").value.toString()
                    val namaResponden = snapshot.child("nama_responden").value.toString()

                    val retriveDataProgress =
                        ProfileResponse(alamatResponden, namaResponden)
                    callback.onDataUserReceived(retriveDataProgress)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "error: " + error.message)
            }

        })

    }

    interface DataUserNameCallback {
        fun onDataUserReceived(profileResponse: ProfileResponse)
    }


    fun getProgressUser(callback: DataProgressCallback) {
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val list = ArrayList<ChatResponse>()
        val reff: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("progress")
                .child(mAuth.currentUser?.uid.toString())
        reff.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val statusBelajar = snapshot.child("Status_Belajar").value.toString()
                    val statusPostTest = snapshot.child("Status_Post_Test").value.toString()
                    val statusPreTest = snapshot.child("Status_Pre_Test").value.toString()

                    val retriveDataProgress =
                        ProgressResponse(statusBelajar, statusPostTest, statusPreTest)
                    callback.onDataProgressReceived(retriveDataProgress)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "error: " + error.message)
            }

        })

    }

    interface DataProgressCallback {

        fun onDataProgressReceived(progressResponse: ProgressResponse)

    }

    fun getDatasoal(callback: LoadSoalCallBack): List<ListPelajaranResponse> {
        val list = ArrayList<ListPelajaranResponse>()
        val reff: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("list_pelajaran")
        reff.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val dataChat = dataSnapshot.getValue(ListPelajaranResponse::class.java)
                        list.add(dataChat!!)
                        callback.onDatasoalCallBack(list)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "error: " + error.message)
            }

        })
        return list
    }


    interface LoadSoalCallBack {
        fun onDatasoalCallBack(listPelajaranResponse: List<ListPelajaranResponse>)
    }

}




