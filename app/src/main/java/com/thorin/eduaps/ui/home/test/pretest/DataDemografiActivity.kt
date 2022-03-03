package com.thorin.eduaps.ui.home.test.pretest

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thorin.eduaps.R
import com.thorin.eduaps.databinding.ActivityDataDemografiBinding
import com.thorin.eduaps.ui.navigation.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DataDemografiActivity : AppCompatActivity() {

    private var _binding: ActivityDataDemografiBinding? = null
    private val binding get() = _binding!!

    private lateinit var refUser: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDataDemografiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkFlow()

        checkSelesai()

    }


    private fun checkSelesai() {
        val prefPreTest2: SharedPreferences =
            this.getSharedPreferences("data_demografi", Context.MODE_PRIVATE)

        val status = prefPreTest2.getString("data_demografi_finish", null)

        if (status == "selesai") {
            Intent(this, TestActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

    }

    private fun checkFlow() {

        binding.idStatusResponden.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable) {

                binding.idStatusResponden.onItemClickListener =
                    OnItemClickListener { _, _, _, _ ->
                        if (binding.idStatusResponden.text.toString() == "Orang Tua") {

                            Toast.makeText(
                                applicationContext,
                                "Silahklan Masukan Pekerjaan Anda Sekarang",
                                Toast.LENGTH_LONG
                            ).show()

                            binding.idPekerjaanDropdown.visibility = View.VISIBLE
                            binding.idPekerjaanDropdown2.visibility = View.VISIBLE
                            binding.idPekerjaanDropdown3.visibility = View.VISIBLE
                        } else if (binding.idStatusResponden.text.toString() == "Guru SD") {
                            binding.idPekerjaanDropdown.visibility = View.GONE
                            binding.idPekerjaanDropdown2.visibility = View.GONE
                            binding.idPekerjaanDropdown3.visibility = View.GONE
                        }
                    }


            }
        })


        binding.idYatidakDropdown.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                binding.idYatidakDropdown.onItemClickListener =
                    OnItemClickListener { _, _, _, _ ->
                        if (binding.idYatidakDropdown.text.toString() == "Ya") {
                            Toast.makeText(
                                applicationContext,
                                "Anda Tahu Dari Mana ?",
                                Toast.LENGTH_LONG
                            ).show()
                            binding.idInfoDarimana.visibility = View.VISIBLE
                            binding.idYadarimanaDropdown.visibility = View.VISIBLE
                        } else if (binding.idYatidakDropdown.text.toString() == "Tidak") {
                            binding.idInfoDarimana.visibility = View.GONE
                            binding.idYadarimanaDropdown.visibility = View.GONE
                        }
                    }
            }
        })

        binding.idAsalSekolah.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                binding.idAsalSekolah.onItemClickListener =
                    OnItemClickListener { _, _, _, _ ->
                        if (binding.idAsalSekolah.text.toString().equals("SD di kota Bandung")) {
                            binding.idShowPendapatanBandung.visibility = View.VISIBLE
                            binding.idShowPendapatanGarut.visibility = View.GONE
                            binding.idShowPendapatanKabupatenbandung.visibility = View.GONE
                        }
                        else if (binding.idAsalSekolah.text.toString().equals("SD di kabupaten Bandung")) {
                            binding.idShowPendapatanKabupatenbandung.visibility = View.VISIBLE
                            binding.idShowPendapatanBandung.visibility = View.GONE
                            binding.idShowPendapatanGarut.visibility = View.GONE
                        }
                        else if (binding.idAsalSekolah.text.toString().equals("SD di kabupaten Garut")) {
                            binding.idShowPendapatanGarut.visibility = View.VISIBLE
                            binding.idShowPendapatanBandung.visibility = View.GONE
                            binding.idShowPendapatanKabupatenbandung.visibility = View.GONE
                        }
                    }

            }

        })

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                listValue()
            }
        }
    }

    override fun onBackPressed() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Apakah Anda Yakin ?.")
        builder.setMessage(
            """
            Jika form ini di hentikan, anda harus memulai dari awal.
        """.trimIndent()
        )
        builder.setPositiveButton("Oke") { _, _ ->
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        builder.setNegativeButton("Tidak Jadi") { dialog, _ -> // Do nothing
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun listValue() {

        val itemsSekolah =
            listOf("SD di kota Bandung", "SD di kabupaten Bandung", "SD di kabupaten Garut")
        val adapterSekolah = ArrayAdapter(applicationContext, R.layout.list_item, itemsSekolah)
        (binding.idAsalSekolah as? AutoCompleteTextView)?.setAdapter(adapterSekolah)

        val itemsGender = listOf("Laki-Laki", "Perempuan")
        val adapterGender = ArrayAdapter(applicationContext, R.layout.list_item, itemsGender)
        (binding.idGenderDropdown as? AutoCompleteTextView)?.setAdapter(adapterGender)

        val itemStatus = listOf("Guru SD", "Orang Tua")
        val adapterStatus = ArrayAdapter(applicationContext, R.layout.list_item, itemStatus)
        (binding.idStatusResponden as? AutoCompleteTextView)?.setAdapter(adapterStatus)

        val itemsKerja = listOf(
            "Tidak bekerja",
            "Guru SD",
            "Guru SMP",
            "Guru SMA",
            "PNS",
            "TNI",
            "POLRI",
            "Wiraswasta",
            "Pensiunan",
            "Lainnya, Ketik Manual"
        )
        val adapterKerja = ArrayAdapter(applicationContext, R.layout.list_item, itemsKerja)
        (binding.idPekerjaanDropdown as? AutoCompleteTextView)?.setAdapter(adapterKerja)

        val itemsPendidikan = listOf(
            "Tidak sekolah",
            "Sekolah Dasar",
            "Sekolah Menengah Pertama",
            "Sekolah Menengah Atas",
            "Perguruan Tinggi/Universitas"
        )
        val adapterPendidikan =
            ArrayAdapter(applicationContext, R.layout.list_item, itemsPendidikan)
        (binding.idPendidikanDropdown as? AutoCompleteTextView)?.setAdapter(adapterPendidikan)

        val itemsStatusKawin = listOf("Kawin", "Cerai", "Tidak kawin")
        val adapterStatusKawin =
            ArrayAdapter(applicationContext, R.layout.list_item, itemsStatusKawin)
        (binding.idStatusKawinDropdown as? AutoCompleteTextView)?.setAdapter(adapterStatusKawin)

        val itemsPendapatanKeluargaGarut =
            listOf("Rp. 1,961,084 atau kurang/bulan", "Rp 1,961,085 atau lebih/bulan")
        val adapterPendapatanKeluargaGarut =
            ArrayAdapter(applicationContext, R.layout.list_item, itemsPendapatanKeluargaGarut)
        (binding.idPendapatanGarutDropdown as? AutoCompleteTextView)?.setAdapter(
            adapterPendapatanKeluargaGarut
        )

        val itemsPendapatanKeluargaBandung =
            listOf("Kurang Rp. 3.742.276,48/bulan", "lebih Rp 3.742.276,48/bulan")
        val adapterPendapatanKeluargaBandung =
            ArrayAdapter(applicationContext, R.layout.list_item, itemsPendapatanKeluargaBandung)
        (binding.idPendapatanBandungDropdown as? AutoCompleteTextView)?.setAdapter(
            adapterPendapatanKeluargaBandung
        )

        val itemsPendapatanKeluargaKabupatenBandung =
            listOf("Rp. 3.241.929,66/bulan", "Rp 3.241.929,67/bulan")
        val adapterPendapatanKeluargaKabupatenBandung = ArrayAdapter(
            applicationContext,
            R.layout.list_item,
            itemsPendapatanKeluargaKabupatenBandung
        )
        (binding.idPendapatanKabupatenBandungDropdown as? AutoCompleteTextView)?.setAdapter(
            adapterPendapatanKeluargaKabupatenBandung
        )

        val itemsOrangTuaAtauGuruKelas = listOf("1", "2", "3", "4", "5", "6")
        val adapterOrangTuaAtauGuruKelas =
            ArrayAdapter(applicationContext, R.layout.list_item, itemsOrangTuaAtauGuruKelas)
        (binding.idOrtuDarikelasDropdown as? AutoCompleteTextView)?.setAdapter(
            adapterOrangTuaAtauGuruKelas
        )

        val itemsSuku = listOf("Sunda", "Batak", "Jawa", "Padang", "Lainya, Ketik Manual")
        val adapterSuku = ArrayAdapter(applicationContext, R.layout.list_item, itemsSuku)
        (binding.idSukuDropdown as? AutoCompleteTextView)?.setAdapter(adapterSuku)

        val itemInfoPendidikanSeksualAnak = listOf("Ya", "Tidak")
        val adapterInfoPendidikanSeksualanak =
            ArrayAdapter(applicationContext, R.layout.list_item, itemInfoPendidikanSeksualAnak)
        (binding.idYatidakDropdown as? AutoCompleteTextView)?.setAdapter(
            adapterInfoPendidikanSeksualanak
        )

        val itemYaDariMana = listOf(
            "Koran",
            "Majalah",
            "Televisi",
            "Radio",
            "Web/Internet",
            "Video",
            "Petugas Kesehatan",
            "Kader kesehatan",
            "Teman",
            "Pemuka agama",
            "Keluarga",
            "Guru/kepala sekolah",
            "Lainnya, Ketik Manual"
        )
        val adapterYaDariMana = ArrayAdapter(applicationContext, R.layout.list_item, itemYaDariMana)
        (binding.idYadarimanaDropdown as? AutoCompleteTextView)?.setAdapter(adapterYaDariMana)

        val itemJmlhAnggotaKeluarga = listOf("3-4 orang", "lebih dari 4 orang")
        val adapterJmlhAnggotaKeluarga =
            ArrayAdapter(applicationContext, R.layout.list_item, itemJmlhAnggotaKeluarga)
        (binding.idJmlhKeluargaDropdown as? AutoCompleteTextView)?.setAdapter(
            adapterJmlhAnggotaKeluarga
        )

        val itemKluargaYgTinggalSerumah = listOf(
            "orang tua dan anak",
            "orang tua, anak, dan kakek/nenek",
            "orang tua, kakek/nenek, dan  paman/bibi",
            "orang tua dan paman/bibi",
            "orang tua tiri, orang tua kandung, anak",
            "orang tua tiri, orang tua kandung, anak,  saudara tiri",
            "Lainya, Ketik Manual"
        )
        val adapterKluargaYgTinggalSerumah =
            ArrayAdapter(applicationContext, R.layout.list_item, itemKluargaYgTinggalSerumah)
        (binding.idKeluargaYgTinggalSerumahDropdown as? AutoCompleteTextView)?.setAdapter(
            adapterKluargaYgTinggalSerumah
        )

        val itemOrangYgRawatAnak =
            listOf("orang tua", "ayah saja", "ibu saja", "kakek atau nenek", "pembantu", "yang lainya, ketik manual")
        val adapterOrangYgRawatAnak =
            ArrayAdapter(applicationContext, R.layout.list_item, itemOrangYgRawatAnak)
        (binding.idOrgYgrawatanakDropdown as? AutoCompleteTextView)?.setAdapter(
            adapterOrangYgRawatAnak
        )

        binding.btnNext.setOnClickListener {

            if (binding.idusia.text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    "Silahkan isi form usia dulu ya...",
                    Snackbar.LENGTH_SHORT
                ).show()

            } else if (binding.idAsalSekolah.text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    "Silahkan isi form asal sekolah ya...",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (binding.idGenderDropdown.text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    "Silahkan isi form jenis kelamin ya...",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (binding.idStatusResponden.text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    "Silahkan isi form status ya...",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (binding.idStatusResponden.text.toString() == "Orang Tua" && binding.idPekerjaanDropdown.text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    "Silahkan isi form jenis pekerjaan ya...",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (binding.idPendidikanDropdown.text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    "Silahkan isi form pendidikan ya...",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (binding.idStatusKawinDropdown.text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    "Silahkan isi form status perkawinan ya...",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (binding.idPendapatanBandungDropdown.text.isNullOrBlank() && binding.idPendapatanGarutDropdown.text.isNullOrBlank() && binding.idPendapatanKabupatenBandungDropdown.text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    "Silahkan isi form pendapatan ya...",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (binding.idOrtuDarikelasDropdown.text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    "Silahkan isi form pendapatan ya...",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (binding.idSukuDropdown.text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    "Silahkan isi form suku ya...",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (binding.idYatidakDropdown.text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    "Silahkan isi form tahu informasi pencegahan kekerasan seksual ya...",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (binding.idYatidakDropdown.text.toString() == "Ya" && binding.idYadarimanaDropdown.text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    "Silahkan isi form darimana anda tahu informasi kekerasan seksual ya...",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (binding.idJmlhKeluargaDropdown.text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    "Silahkan isi form jumlah anggota keluarga ya...",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (binding.idKeluargaYgTinggalSerumahDropdown.text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    "Silahkan isi form keluarga yang tinggal serumah ya...",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (binding.idOrgYgrawatanakDropdown.text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    "Silahkan isi form orang yang merawat anak  ya...",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                uploadFile()
            }


        }

    }


    private fun uploadFile() {

        val progressdialog = ProgressDialog(this)
        progressdialog.setMessage("Mengirim Data...")

        progressdialog.show()

        mAuth = FirebaseAuth.getInstance()
        refUser = FirebaseDatabase.getInstance().reference

        refUser = FirebaseDatabase.getInstance().reference.child("data_demografi")
            .child(mAuth.currentUser?.uid.toString())
        val userHashMap = HashMap<String, Any>()
        userHashMap["uid"] = mAuth.currentUser?.uid.toString()
        userHashMap["profile_photo"] = mAuth.currentUser?.photoUrl.toString()
        userHashMap["username"] = mAuth.currentUser?.displayName.toString()
        userHashMap["email"] = mAuth.currentUser?.email.toString()
        userHashMap["Usia"] = binding.idusia.text.toString()
        userHashMap["asal_sekolah"] = binding.idAsalSekolah.text.toString()
        userHashMap["jenis_kelamin"] = binding.idGenderDropdown.text.toString()
        userHashMap["status_responden"] = binding.idStatusResponden.text.toString()
        userHashMap["pekerjaan"] = binding.idPekerjaanDropdown.text.toString()
        userHashMap["pendidikan"] = binding.idPendidikanDropdown.text.toString()
        userHashMap["status_perkawinan"] = binding.idStatusKawinDropdown.text.toString()
        userHashMap["pendapatan_keluarga"] =
            binding.idPendapatanGarutDropdown.text.toString() + binding.idPendapatanBandungDropdown.text.toString() + binding.idPendapatanKabupatenBandungDropdown.text.toString()
        userHashMap["orang_tua_guru_kelas"] = binding.idOrtuDarikelasDropdown.text.toString()
        userHashMap["suku"] = binding.idSukuDropdown.text.toString()
        userHashMap["informasi_pencegahan_seksual"] =
            binding.idYatidakDropdown.text.toString() + binding.idYadarimanaDropdown.text.toString()
        userHashMap["jmlh_anggota_keluarga"] = binding.idJmlhKeluargaDropdown.text.toString()
        userHashMap["keluarga_tinggal_serumah"] =
            binding.idKeluargaYgTinggalSerumahDropdown.text.toString()
        userHashMap["orangyg_rawat_anak"] = binding.idOrgYgrawatanakDropdown.text.toString()
        refUser.updateChildren(userHashMap)
            .addOnCompleteListener { tasks ->
                if (tasks.isSuccessful) {
                    val prefPreTest2: SharedPreferences =
                        this.getSharedPreferences("data_demografi", Context.MODE_PRIVATE)
                    val edit = prefPreTest2.edit()
                    edit.putString("data_demografi_finish", "selesai")
                    edit.apply()
                    progressdialog.dismiss()
                    Intent(this, TestActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                } else {
                    progressdialog.dismiss()
                    Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                }
            }
    }

}