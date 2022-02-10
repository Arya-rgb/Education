package com.thorin.eduaps.ui.home.test.testresult

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.thorin.eduaps.databinding.ActivityPostTestResultBinding
import com.thorin.eduaps.ui.navigation.MainActivity

class PostTestResultActivity : AppCompatActivity() {

    private lateinit var refUser: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    private var _binding: ActivityPostTestResultBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityPostTestResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        saveProgress()

        binding.button5.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

    }

    private fun saveProgress() {
        val progressdialog = ProgressDialog(this)
        progressdialog.setMessage("Menyimpan Progress...")

        progressdialog.show()

        mAuth = FirebaseAuth.getInstance()
        refUser = FirebaseDatabase.getInstance().reference

        refUser = FirebaseDatabase.getInstance().reference.child("progress")
            .child(mAuth.currentUser?.uid.toString())
        val userHashMap = HashMap<String, Any>()
        userHashMap["Status_Post_Test"] = "Selesai"

        refUser.updateChildren(userHashMap)
            .addOnCompleteListener { tasks ->
                if (tasks.isSuccessful) {
                    getdataNilai()
                    progressdialog.dismiss()
                    Toast.makeText(this, "Progress Di Simpan", Toast.LENGTH_SHORT).show()
                } else {
                    progressdialog.dismiss()
                    Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun getdataNilai() {

        val progressdialog = ProgressDialog(this)
        progressdialog.setMessage("Mengambil Nilai...")

        progressdialog.show()

        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val reff: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("nilai_test")
                .child(mAuth.currentUser?.uid.toString())
        reff.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val nilaiPreTest1 = snapshot.child("nilaiPostTest1").value.toString()
                    val nilaiPreTest2 = snapshot.child("nilaiPostTest2").value.toString()
                    val nilaiPreTest3 = snapshot.child("nilaiPostTest3").value.toString()
                    val nilaiPreTest4 = snapshot.child("nilaiPostTest4").value.toString()

                    binding.nilaiposttest1.text = "Anda Benar $nilaiPreTest1 Dari 30 Pertanyaan"
                    binding.nilaiposttest2.text = "Anda Benar $nilaiPreTest2 Dari 13 Pertanyaan"
                    binding.nilaiposttest3.text = "Anda Benar $nilaiPreTest3 Dari 17 Pertanyaan"
                    binding.nilaiposttest4.text = "Anda Benar $nilaiPreTest4 Dari 20 Pertanyaan"

                    progressdialog.dismiss()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "error: " + error.message)
                progressdialog.dismiss()
            }


        })
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}