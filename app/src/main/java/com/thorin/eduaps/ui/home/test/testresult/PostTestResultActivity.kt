package com.thorin.eduaps.ui.home.test.testresult

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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

        val prefPreTest2: SharedPreferences =
            this.getSharedPreferences("prefPreTest2", Context.MODE_PRIVATE)

        binding.nilaiPosttest.text = "Anda Benar ${prefPreTest2.getString("scorePreTest2", null)} Dari 78 Soal"

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

        val prefPreTest2: SharedPreferences =
            this.getSharedPreferences("prefPreTest2", Context.MODE_PRIVATE)

        refUser = FirebaseDatabase.getInstance().reference.child("progress")
            .child(mAuth.currentUser?.uid.toString())
        val userHashMap = HashMap<String, Any>()
        userHashMap["posttest"] = "selesai"
        userHashMap["nilai_posttest"] =  prefPreTest2.getString("scorePreTest2", null).toString()

        refUser.updateChildren(userHashMap)
            .addOnCompleteListener { tasks ->
                if (tasks.isSuccessful) {
                    progressdialog.dismiss()
                    Toast.makeText(this, "Progress Di Simpan", Toast.LENGTH_SHORT).show()
                } else {
                    progressdialog.dismiss()
                    Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}