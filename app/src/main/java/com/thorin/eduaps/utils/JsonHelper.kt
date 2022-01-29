package com.thorin.eduaps.utils

import android.content.Context
import com.thorin.eduaps.data.source.remote.response.TestQuestioner
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {

    private fun parsingFileToString(filename: String): String? {
        return try {
            val `is` = context.assets.open(filename)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (
            ex: IOException
        ) {
            ex.printStackTrace()
            null
        }
    }

    fun loadTest2(): List<TestQuestioner> {
        val list = ArrayList<TestQuestioner>()

        try {
            val responseObject = JSONObject(parsingFileToString("test2Quest.json").toString())
            val listArray = responseObject.getJSONArray("bagian_dua_kuisioner")
            for (i in 0 until listArray.length()) {
                val dataTest2 = listArray.getJSONObject(i)

                val soal = dataTest2.getString("Soal")
                val opsi1 = dataTest2.getString("opsi_1")
                val opsi2 = dataTest2.getString("opsi_2")
                val opsi3 = dataTest2.getString("opsi_3")
                val opsi4 = dataTest2.getString("opsi_4")
                val kunciJawaban = dataTest2.getString("kunci_jawaban")

                val dataTest2Response = TestQuestioner(soal, opsi1, opsi2, opsi3, opsi4, kunciJawaban)
                list.add(dataTest2Response)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }

    fun loadListsoal(): List<TestQuestioner> {
        val list = ArrayList<TestQuestioner>()

        try {
            val responseObject = JSONObject(parsingFileToString("test2Quest.json").toString())
            val listArray = responseObject.getJSONArray("bagian_dua_kuisioner")
            for (i in 0 until listArray.length()) {
                val dataTest2 = listArray.getJSONObject(i)

                val soal = dataTest2.getString("Soal")
                val opsi1 = dataTest2.getString("opsi_1")
                val opsi2 = dataTest2.getString("opsi_2")
                val opsi3 = dataTest2.getString("opsi_3")
                val opsi4 = dataTest2.getString("opsi_4")
                val kunciJawaban = dataTest2.getString("kunci_jawaban")

                val dataTest2Response = TestQuestioner(soal, opsi1, opsi2, opsi3, opsi4, kunciJawaban)
                list.add(dataTest2Response)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }



//    fun loadTest2(): List<Test2Questioner> {
//        val list = ArrayList<Test2Questioner>()
//        try {
//            val reff: DatabaseReference = FirebaseDatabase.getInstance().reference.child("bagian_dua_kuisioner")
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
//
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//        return list
//    }

}