package com.thorin.eduaps.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thorin.eduaps.data.source.remote.RemoteDataSource
import com.thorin.eduaps.data.source.remote.response.ChatResponse
import com.thorin.eduaps.data.source.remote.response.ListPelajaranResponse
import com.thorin.eduaps.data.source.remote.response.TestQuestioner
import com.thorin.eduaps.data.source.remote.response.UserResponse

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
                        response.opsi_1,
                        response.opsi_2,
                        response.opsi_3,
                        response.opsi_4,
                        response.kunci_jawaban
                    )
                    dataTest2List.add(dataTest2ListAdd)
                }
                dataTest2Result.postValue(dataTest2List)
            }

        })

        return dataTest2Result

    }

    override fun getDataPelajaran(): LiveData<List<ListPelajaranResponse>> {

        val dataPelajaranResult = MutableLiveData<List<ListPelajaranResponse>>()
        remoteDataSource.getDataPelajaran(object : RemoteDataSource.LoadDataPelajaranCallback {
            override fun onDataPelajaranReceived(listPelajaranResponse: List<ListPelajaranResponse>) {
                val dataPelajaranList = ArrayList<ListPelajaranResponse>()
                for (response in listPelajaranResponse) {
                    val dataPelajaranListAdd = ListPelajaranResponse(
                        response.id_soal,
                        response.judul_pelajaran,
                        response.jenis_file,
                        response.link_file,
                        response.deskripsi_pelajaran,
                        response.jumlah_slide
                    )
                    dataPelajaranList.add(dataPelajaranListAdd)
                }
                dataPelajaranResult.postValue(dataPelajaranList)
            }
        })

        return dataPelajaranResult

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


}