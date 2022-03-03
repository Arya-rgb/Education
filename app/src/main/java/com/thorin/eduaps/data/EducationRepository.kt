package com.thorin.eduaps.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thorin.eduaps.data.source.remote.RemoteDataSource
import com.thorin.eduaps.data.source.remote.response.*

class EducationRepository private constructor(private val remoteDataSource: RemoteDataSource) :
    EducationDataSource {

    companion object {
        @Volatile
        private var instance: EducationRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): EducationRepository =
            instance ?: synchronized(this) {
                EducationRepository(remoteDataSource).apply { instance = this }
            }
    }

    override fun getDataUserProfile(): LiveData<UserResponse> {
        val userProfileResult = MutableLiveData<UserResponse>()

        remoteDataSource.getDataUserProfile(object : RemoteDataSource.LoadUserCallback {
            override fun onDataUserReceived(userResponse: UserResponse) {
                userProfileResult.postValue(userResponse)
            }
        })

        return userProfileResult

    }

    override fun getTest2(): LiveData<List<TestQuestioner>> {

        val dataTest2Result = MutableLiveData<List<TestQuestioner>>()
        remoteDataSource.getTest2(object : RemoteDataSource.LoadPretest2Callback {
            override fun onPretest2Received(preTestQuestioner: List<TestQuestioner>) {
                val dataTest2List = ArrayList<TestQuestioner>()
                for (response in preTestQuestioner) {
                    val dataTest2ListAdd = TestQuestioner(
                        response.Soal,
                        response.kunci_jawaban,
                        response.opsi_1,
                        response.opsi_2,
                        response.opsi_3,
                        response.opsi_4,
                        response.opsi_5,
                        response.opsi_6
                    )
                    dataTest2List.add(dataTest2ListAdd)
                }
                dataTest2Result.postValue(dataTest2List)
            }

        })

        return dataTest2Result

    }

    override fun getChatData(label: String): LiveData<List<ChatResponse>> {

        val dataChatResult = MutableLiveData<List<ChatResponse>>()
        remoteDataSource.getChatData(label, object : RemoteDataSource.LoadChatDataCallBack {
            override fun onDataChatReceived(chatResponse: List<ChatResponse>) {
                val dataChatList = ArrayList<ChatResponse>()
                for (response in chatResponse) {
                    val dataChatListAdd = ChatResponse(
                        response.isi_pesan,
                        response.profile_photo,
                        response.uid,
                        response.username
                    )
                    dataChatList.add(dataChatListAdd)
                }
                dataChatResult.postValue(dataChatList)
            }

        })

        return dataChatResult

    }

    override fun getProgress(): LiveData<ProgressResponse> {

        val progressResult = MutableLiveData<ProgressResponse>()

        remoteDataSource.getProgressUser(object : RemoteDataSource.DataProgressCallback {

            override fun onDataProgressReceived(progressResponse: ProgressResponse) {
                progressResult.postValue(progressResponse)
            }
        })

        return progressResult

    }

    override fun getDataSoal(): LiveData<List<ListPelajaranResponse>> {

        val dataSoalResult = MutableLiveData<List<ListPelajaranResponse>>()
        remoteDataSource.getDatasoal(object : RemoteDataSource.LoadSoalCallBack {
            override fun onDatasoalCallBack(listPelajaranResponse: List<ListPelajaranResponse>) {
                val dataSoalList = ArrayList<ListPelajaranResponse>()
                for (response in listPelajaranResponse) {
                    val dataChatListAdd = ListPelajaranResponse(
                        response.deskripsi_pelajaran,
                        response.id_soal,
                        response.jenis_file,
                        response.judul_pelajaran,
                        response.jumlah_slide,
                        response.link_file,
                        response.z_linkgambar
                    )
                    dataSoalList.add(dataChatListAdd)

                }

                dataSoalResult.postValue(dataSoalList)

            }

        })

        return dataSoalResult

    }

    override fun getUserData(): LiveData<ProfileResponse> {
        val dataUserHome = MutableLiveData<ProfileResponse>()

        remoteDataSource.getUserName(object : RemoteDataSource.DataUserNameCallback {

            override fun onDataUserReceived(profileResponse: ProfileResponse) {
                dataUserHome.postValue(profileResponse)
            }
        })

        return dataUserHome
    }
}