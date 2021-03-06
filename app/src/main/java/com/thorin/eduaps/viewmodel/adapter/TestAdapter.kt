package com.thorin.eduaps.viewmodel.adapter


import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import com.thorin.eduaps.data.source.remote.response.TestQuestioner
import com.thorin.eduaps.databinding.ListTestItemsBinding
import com.thorin.eduaps.ui.home.test.posttest.PostTestActivity
import com.thorin.eduaps.ui.home.test.pretest.TestActivity


class TestAdapter : RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    private var listDataTest2 = ArrayList<TestQuestioner>()


    fun setDataTest(dataTest: List<TestQuestioner>?) {
        if (dataTest != null) {
            this.listDataTest2.clear()
            this.listDataTest2.addAll(dataTest)
        }
    }

    class ViewHolder(private val binding: ListTestItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataTest: TestQuestioner) {
            with(binding) {
                idSoal.text = dataTest.Soal
                kunciJawaban.text = dataTest.kunci_jawaban
                opsi1.text = dataTest.opsi_1
                opsi2.text = dataTest.opsi_2
                opsi3.text = dataTest.opsi_3
                opsi4.text = dataTest.opsi_4
                bagiansoalOpsi5.text = dataTest.opsi_5
                idLikert.text = dataTest.opsi_6

                if (idSoal.text.contains("1. Kekerasan seksual pada anak adalah kegiatan yang melibatkan anak dalam aktivitas seksual, baik melalui alat kelamin/anus atau bukan, yang melanggar hukum dan norma, dilakukan dengan paksaan, kekerasan atau ancaman")) {
                    if (itemView.context is TestActivity) {
                        (itemView.context as TestActivity).alertInfo1()
                    }

                    //for post test activity
                    if (itemView.context is PostTestActivity) {
                        (itemView.context as PostTestActivity).alertInfo1()
                    }

                }

                if (idSoal.text.contains("1. Saya setuju jika orang tua siswa dan guru diberikan penyuluhan materi pencegahan kekerasan seksual pada anak di sekolah")) {
                    if (itemView.context is TestActivity) {
                        (itemView.context as TestActivity).alertInfo2()
                    }

                    if (itemView.context is TestActivity) {
                        (itemView.context as TestActivity).saveProgress1()
                    }

                    if (itemView.context is PostTestActivity) {
                        (itemView.context as PostTestActivity).saveProgress1()
                    }

                    if (itemView.context is PostTestActivity) {
                        (itemView.context as PostTestActivity).alertInfo2()
                    }

                }

                if (idSoal.text.contains("1. Saya mengajarkan kepada anak tentang daerah pribadi tubuh anak yang harus selalu ditutupi pakaian dan tidak boleh disentuh orang lain, kecuali oleh orang yang dikenal anak")) {
                    if (itemView.context is TestActivity) {
                        (itemView.context as TestActivity).alertInfo3()
                    }

                    if (itemView.context is TestActivity) {
                        (itemView.context as TestActivity).saveProgress2()
                    }

                    if (itemView.context is PostTestActivity) {
                        (itemView.context as PostTestActivity).saveProgress2()
                    }

                    if (itemView.context is PostTestActivity) {
                        (itemView.context as PostTestActivity).alertInfo3()
                    }

                }

                if (idSoal.text.contains("1. Saya menyadari bahwa anak anak usia berapapun berisiko mengalami kekerasan seksual")) {
                    if (itemView.context is TestActivity) {
                        (itemView.context as TestActivity).alertInfo4()
                    }

                    if (itemView.context is TestActivity) {
                        (itemView.context as TestActivity).saveProgress3()
                    }

                    if (itemView.context is PostTestActivity) {
                        (itemView.context as PostTestActivity).saveProgress3()
                    }

                    if (itemView.context is PostTestActivity) {
                        (itemView.context as PostTestActivity).alertInfo4()
                    }

                }

                if (opsi3.text.contains("C")) {
                    opsi3.visibility = View.VISIBLE
                } else {
                    opsi3.visibility = View.GONE
                }

                if (opsi4.text.contains("D")) {
                    opsi4.visibility = View.VISIBLE
                } else {
                    opsi4.visibility = View.GONE
                }

                val prefPreTest2: SharedPreferences =
                    itemView.context.getSharedPreferences("prefPreTest2", Context.MODE_PRIVATE)
                val edit = prefPreTest2.edit()

                binding.btnNextSoal2.isEnabled = false

                val radioGroup = binding.radioGrupOpsi2 as RadioGroup
                radioGroup.setOnCheckedChangeListener { _, _ ->
                    binding.btnNextSoal2.isEnabled = true
                }

                binding.btnNextSoal2.setOnClickListener {

                    val selectedId: Int = binding.radioGrupOpsi2.checkedRadioButtonId
                    val radioButton = itemView.findViewById(selectedId) as RadioButton?

                    if (itemView.context is TestActivity) {
                        var nomerLikert = ""
                        if (idLikert.text == "positif") {
                            when (radioButton?.text) {
                                "A. Sangat Setuju" -> {
                                    nomerLikert = "4"
                                }
                                "B. Setuju" -> {
                                    nomerLikert = "3"
                                }
                                "C. Tidak Setuju" -> {
                                    nomerLikert = "2"
                                }
                                "D. Sangat Tidak Setuju" -> {
                                    nomerLikert = "1"
                                }
                            }
                        }

                        if (idLikert.text == "negatif") {
                            when (radioButton?.text) {
                                "A. Sangat Setuju" -> {
                                    nomerLikert = "1"
                                }
                                "B. Setuju" -> {
                                    nomerLikert = "2"
                                }
                                "C. Tidak Setuju" -> {
                                    nomerLikert = "3"
                                }
                                "D. Sangat Tidak Setuju" -> {
                                    nomerLikert = "4"
                                }
                            }
                        }

                        if (radioButton?.text?.equals(kunciJawaban.text.toString()) == true && radioButton.text?.equals(
                                "A. YA"
                            ) == true
                        ) {
                            nomerLikert = "1"
                        }

                        if (radioButton?.text?.equals(kunciJawaban.text.toString()) == true && radioButton.text?.equals(
                                "B. TIDAK"
                            ) == true
                        ) {
                            nomerLikert = "1"
                        }

                        if (radioButton?.text?.equals(kunciJawaban.text.toString()) == false && radioButton.text?.equals(
                                "A. YA"
                            ) == true
                        ) {
                            nomerLikert = "0"
                        }

                        if (radioButton?.text?.equals(kunciJawaban.text.toString()) == false && radioButton.text?.equals(
                                "B. TIDAK"
                            ) == true
                        ) {
                            nomerLikert = "0"
                        }

                        if (idLikert.text == "" && radioButton?.text?.equals("B. TIDAK") == false && radioButton.text?.equals("A. YA") == false) {
                            nomerLikert = radioButton.text.toString()
                        }

                        (itemView.context as TestActivity).saveJawaban(
                            bagiansoalOpsi5.text.toString(),
                            nomerLikert
                        )
                    }

                    if (itemView.context is PostTestActivity) {
                        var nomerLikert = ""
                        if (idLikert.text == "positif") {
                            when (radioButton?.text) {
                                "A. Sangat Setuju" -> {
                                    nomerLikert = "4"
                                }
                                "B. Setuju" -> {
                                    nomerLikert = "3"
                                }
                                "C. Tidak Setuju" -> {
                                    nomerLikert = "2"
                                }
                                "D. Sangat Tidak Setuju" -> {
                                    nomerLikert = "1"
                                }
                            }
                        }

                        if (idLikert.text == "negatif") {
                            when (radioButton?.text) {
                                "A. Sangat Setuju" -> {
                                    nomerLikert = "1"
                                }
                                "B. Setuju" -> {
                                    nomerLikert = "2"
                                }
                                "C. Tidak Setuju" -> {
                                    nomerLikert = "3"
                                }
                                "D. Sangat Tidak Setuju" -> {
                                    nomerLikert = "4"
                                }
                            }
                        }

                        if (radioButton?.text?.equals(kunciJawaban.text.toString()) == true && radioButton.text?.equals(
                                "A. YA"
                            ) == true
                        ) {
                            nomerLikert = "1"
                        }

                        if (radioButton?.text?.equals(kunciJawaban.text.toString()) == true && radioButton.text?.equals(
                                "B. TIDAK"
                            ) == true
                        ) {
                            nomerLikert = "1"
                        }

                        if (radioButton?.text?.equals(kunciJawaban.text.toString()) == false && radioButton.text?.equals(
                                "A. YA"
                            ) == true
                        ) {
                            nomerLikert = "0"
                        }

                        if (radioButton?.text?.equals(kunciJawaban.text.toString()) == false && radioButton.text?.equals(
                                "B. TIDAK"
                            ) == true
                        ) {
                            nomerLikert = "0"
                        }

                        if (idLikert.text == "" && radioButton?.text?.equals("B. TIDAK") == false && radioButton.text?.equals("A. YA") == false) {
                            nomerLikert = radioButton.text.toString()
                        }
                        (itemView.context as PostTestActivity).saveJawaban(
                            bagiansoalOpsi5.text.toString(),
                            nomerLikert
                        )
                    }

                    if (radioButton?.text?.contains(kunciJawaban.text) == true) {
                        val results =
                            prefPreTest2.getString("scorePreTest2", 0.toString())?.toInt()?.plus(1)
                        edit.putString("scorePreTest2", results.toString())
                        edit.apply()
                    }

                    if (itemView.context is TestActivity) {
                        (itemView.context as TestActivity).nextPage()
                    } else if (itemView.context is PostTestActivity) {
                        (itemView.context as PostTestActivity).nextPage()
                    }

                    if (kunciJawaban.text.contains("akhir_soal")) {

                        if (itemView.context is TestActivity) {
                            (itemView.context as TestActivity).movePreTest()
                        } else if (itemView.context is PostTestActivity) {
                            (itemView.context as PostTestActivity).movePostTest()
                        }
                    }

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemViewBinding =
            ListTestItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataTest = listDataTest2[position]
        holder.setIsRecyclable(false)
        holder.bind(dataTest)

    }

    override fun getItemCount(): Int = listDataTest2.size
}