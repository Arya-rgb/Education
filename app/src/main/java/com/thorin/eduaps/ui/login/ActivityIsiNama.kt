package com.thorin.eduaps.ui.login

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thorin.eduaps.databinding.ActivityIsiNamaBinding
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

        mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser?.email.equals("dickyarya.barterin@gmail.com")) {
            binding.btnTestNext.visibility = View.VISIBLE
        } else {
            binding.btnTestNext.visibility = View.GONE
        }

        binding.btnTestNext.setOnClickListener {
            val progressdialog = ProgressDialog(this)
            progressdialog.setMessage("Menyimpan Data...")

            progressdialog.show()

            mAuth = FirebaseAuth.getInstance()
            refUser = FirebaseDatabase.getInstance().reference

            refUser = FirebaseDatabase.getInstance().reference.child("data_demografi")
                .child(mAuth.currentUser?.uid.toString())
            val userHashMap = HashMap<String, Any>()
            userHashMap["nama_responden"] = "Google App Reviewer"
            userHashMap["alamat_responden"] = "USA"

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
                        Toast.makeText(this, "Data Telah Di Simpan...", Toast.LENGTH_SHORT).show()
                    } else {
                        progressdialog.dismiss()
                        Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                    }
                }
        }


        binding.btnSetuju.setOnClickListener {

            when {
                binding.namaLengkap.text.isNullOrBlank() -> {
                    Snackbar.make(
                        binding.root,
                        "Silahkan isi form nama lengkap dulu ya...",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                binding.alamatUser.text.isNullOrBlank() -> {
                    Snackbar.make(
                        binding.root,
                        "Silahkan isi form alamat dulu ya...",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    saveProgress()
                }
            }
        }
    }


    private fun saveProgress() {
        val progressdialog = ProgressDialog(this)
        progressdialog.setMessage("Menyimpan Data...")

        progressdialog.show()

        mAuth = FirebaseAuth.getInstance()
        refUser = FirebaseDatabase.getInstance().reference

        refUser = FirebaseDatabase.getInstance().reference.child("data_demografi")
            .child(mAuth.currentUser?.uid.toString())
        val userHashMap = HashMap<String, Any>()
        userHashMap["nama_responden"] = binding.namaLengkap.text.toString()
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
                    Toast.makeText(this, "Data Telah Di Simpan...", Toast.LENGTH_SHORT).show()
                } else {
                    progressdialog.dismiss()
                    Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                }
            }
    }


}