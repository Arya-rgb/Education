package com.thorin.eduaps.ui.login

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thorin.eduaps.databinding.ActivityIsiNamaBinding
import com.thorin.eduaps.databinding.ActivityLoginBinding
import com.thorin.eduaps.ui.navigation.MainActivity

class ActivityIsiNama : AppCompatActivity() {

    private var _binding: ActivityIsiNamaBinding? = null
    private val binding get() = _binding!!

    private lateinit var refUser: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityIsiNamaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.hide()


        binding.btnSetuju.setOnClickListener {
            saveProgress()
        }


    }

    private fun saveProgress() {
        val progressdialog = ProgressDialog(this)
        progressdialog.setMessage("Menyimpan Data...")

        progressdialog.show()

        mAuth = FirebaseAuth.getInstance()
        refUser = FirebaseDatabase.getInstance().reference

        refUser = FirebaseDatabase.getInstance().reference.child("data_persetujuan")
            .child(mAuth.currentUser?.uid.toString())
        val userHashMap = HashMap<String, Any>()
        userHashMap["nama_responden"] = binding.namaLengkap.text.toString()
        userHashMap["umur_responden"] = binding.umurUser.text.toString()
        userHashMap["alamat_responden"] = binding.alamatUser.text.toString()

        refUser.updateChildren(userHashMap)
            .addOnCompleteListener { tasks ->
                if (tasks.isSuccessful) {

                    val prefPreTest2: SharedPreferences =
                        this.getSharedPreferences("persetujuan", Context.MODE_PRIVATE)
                    val edit = prefPreTest2.edit()
                    edit.putString("setuju", "setuju")
                    edit.apply()

                    Intent(this, MainActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }

                    progressdialog.dismiss()
                    Toast.makeText(this, "Progress Di Simpan", Toast.LENGTH_SHORT).show()
                } else {
                    progressdialog.dismiss()
                    Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                }
            }
    }

}