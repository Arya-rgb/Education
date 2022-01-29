package com.thorin.eduaps.ui.home.test

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thorin.eduaps.R
import com.thorin.eduaps.databinding.ActivityDataDemografiBinding
import com.thorin.eduaps.ui.home.test.testresult.DataDemografiResultActivity

class DataDemografiActivity : AppCompatActivity() {

    private var _binding: ActivityDataDemografiBinding? = null
    private val binding get() = _binding!!

    private lateinit var refUser: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        _binding = ActivityDataDemografiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listValue()

    }

    private fun listValue() {

        val itemsGender = listOf("Laki-Laki", "Perempuan")
        val adapterGender = ArrayAdapter(applicationContext, R.layout.list_item, itemsGender)
        (binding.idGenderDropdown as? AutoCompleteTextView)?.setAdapter(adapterGender)

        val itemsKerja = listOf("Bekerja", "Tidak bekerja", "Guru SD", "Guru SMP", "Guru SMA", "PNS", "TNI", "POLRI", "Wiraswasta", "Pensiunan", "Lainnya, Ketik Manual")
        val adapterKerja = ArrayAdapter(applicationContext, R.layout.list_item, itemsKerja)
        (binding.idPekerjaanDropdown as? AutoCompleteTextView)?.setAdapter(adapterKerja)

        val itemsPendidikan = listOf("Tidak sekolah", "Sekolah Dasar", "Sekolah Menengah Pertama", "Sekolah Menengah Atas", "Perguruan Tinggi/Universitas")
        val adapterPendidikan = ArrayAdapter(applicationContext, R.layout.list_item, itemsPendidikan)
        (binding.idPendidikanDropdown as? AutoCompleteTextView)?.setAdapter(adapterPendidikan)

        val itemsStatusKawin = listOf("Kawin", "Cerai", "Tidak kawin")
        val adapterStatusKawin = ArrayAdapter(applicationContext, R.layout.list_item, itemsStatusKawin)
        (binding.idStatusKawinDropdown as? AutoCompleteTextView)?.setAdapter(adapterStatusKawin)

        val itemsPendapatanKeluargaGarut = listOf("Rp. 1,961,084 atau kurang/bulan", "Rp 1,961,085 atau lebih/bulan")
        val adapterPendapatanKeluargaGarut = ArrayAdapter(applicationContext, R.layout.list_item, itemsPendapatanKeluargaGarut)
        (binding.idPendapatanGarutDropdown as? AutoCompleteTextView)?.setAdapter(adapterPendapatanKeluargaGarut)

        val itemsPendapatanKeluargaBandung = listOf("Kurang Rp. 3.742.276,48/bulan", "lebih Rp 3.742.276,48/bulan")
        val adapterPendapatanKeluargaBandung = ArrayAdapter(applicationContext, R.layout.list_item, itemsPendapatanKeluargaBandung)
        (binding.idPendapatanBandungDropdown as? AutoCompleteTextView)?.setAdapter(adapterPendapatanKeluargaBandung)

        val itemsPendapatanKeluargaKabupatenBandung = listOf("Rp. 3.241.929,66/bulan", "Rp 3.241.929,67/bulan")
        val adapterPendapatanKeluargaKabupatenBandung = ArrayAdapter(applicationContext, R.layout.list_item, itemsPendapatanKeluargaKabupatenBandung)
        (binding.idPendapatanKabupatenBandungDropdown as? AutoCompleteTextView)?.setAdapter(adapterPendapatanKeluargaKabupatenBandung)

        val itemsOrangTuaAtauGuruKelas = listOf("1", "2", "3", "4", "5", "6")
        val adapterOrangTuaAtauGuruKelas = ArrayAdapter(applicationContext, R.layout.list_item, itemsOrangTuaAtauGuruKelas)
        (binding.idOrtuDarikelasDropdown as? AutoCompleteTextView)?.setAdapter(adapterOrangTuaAtauGuruKelas)

        val itemsSuku = listOf("Sunda", "Batak", "Jawa", "Padang", "Lainya, Ketik Manual")
        val adapterSuku = ArrayAdapter(applicationContext, R.layout.list_item, itemsSuku)
        (binding.idSukuDropdown as? AutoCompleteTextView)?.setAdapter(adapterSuku)

        val itemInfoPendidikanSeksualAnak = listOf("Ya", "Tidak")
        val adapterInfoPendidikanSeksualanak = ArrayAdapter(applicationContext, R.layout.list_item, itemInfoPendidikanSeksualAnak)
        (binding.idYatidakDropdown as? AutoCompleteTextView)?.setAdapter(adapterInfoPendidikanSeksualanak)

        val itemYaDariMana = listOf("Koran", "Majalah", "Televisi", "Radio", "Web/Internet", "Video", "Petugas Kesehatan", "Kader kesehatan", "Teman", "Pemuka agama", "Keluarga", "Guru/kepala sekolah", "Lainnya, Ketik Manual")
        val adapterYaDariMana = ArrayAdapter(applicationContext, R.layout.list_item, itemYaDariMana)
        (binding.idYadarimanaDropdown as? AutoCompleteTextView)?.setAdapter(adapterYaDariMana)

        val itemJmlhAnggotaKeluarga = listOf("3-4 orang", "lebih dari 4 orang")
        val adapterJmlhAnggotaKeluarga = ArrayAdapter(applicationContext, R.layout.list_item, itemJmlhAnggotaKeluarga)
        (binding.idJmlhKeluargaDropdown as? AutoCompleteTextView)?.setAdapter(adapterJmlhAnggotaKeluarga)

        val itemKluargaYgTinggalSerumah = listOf("orang tua dan anak", "orang tua, anak, dan kakek/nenek", "orang tua, kakek/nenek, dan  paman/bibi", "orang tua dan paman/bibi", "orang tua tiri, orang tua kandung, anak", "orang tua tiri, orang tua kandung, anak,  saudara tiri", "Lainya, Ketik Manual")
        val adapterKluargaYgTinggalSerumah = ArrayAdapter(applicationContext, R.layout.list_item, itemKluargaYgTinggalSerumah)
        (binding.idKeluargaYgTinggalSerumahDropdown as? AutoCompleteTextView)?.setAdapter(adapterKluargaYgTinggalSerumah)

        val itemOrangYgRawatAnak = listOf("orang tua", "ayah saja", "ibu saja", "kakek atau nenek", "pembantu")
        val adapterOrangYgRawatAnak = ArrayAdapter(applicationContext, R.layout.list_item, itemOrangYgRawatAnak)
        (binding.idOrgYgrawatanakDropdown as? AutoCompleteTextView)?.setAdapter(adapterOrangYgRawatAnak)

        binding.btnNext.setOnClickListener {
            uploadFile()
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
        userHashMap["status"] = "pretest_selesai"
        userHashMap["Usia"] = binding.idusia.text.toString()
        userHashMap["jenis_kelamin"] = binding.idGenderDropdown.text.toString()
        userHashMap["pekerjaan"] = binding.idPekerjaanDropdown.text.toString()
        userHashMap["pendidikan"] = binding.idPendidikanDropdown.text.toString()
        userHashMap["status_perkawinan"] = binding.idStatusKawinDropdown.text.toString()
        userHashMap["pendapatan_keluarga"] = binding.idPendapatanGarutDropdown.text.toString() + binding.idPendapatanBandungDropdown.text.toString() + binding.idPendapatanKabupatenBandungDropdown.text.toString()
        userHashMap["orang_tua_guru_kelas"] = binding.idOrtuDarikelasDropdown.text.toString()
        userHashMap["suku"] = binding.idSukuDropdown.text.toString()
        userHashMap["informasi_pencegahan_seksual"] = binding.idYatidakDropdown.text.toString() + binding.idYadarimanaDropdown.text.toString()
        userHashMap["jmlh_anggota_keluarga"] = binding.idJmlhKeluargaDropdown.text.toString()
        userHashMap["keluarga_tinggal_serumah"] = binding.idKeluargaYgTinggalSerumahDropdown.text.toString()
        userHashMap["orangyg_rawat_anak"] = binding.idOrgYgrawatanakDropdown.text.toString()
        refUser.updateChildren(userHashMap)
            .addOnCompleteListener { tasks ->
                if (tasks.isSuccessful) {
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