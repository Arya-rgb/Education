package com.thorin.eduaps.ui.home.test.testresult

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
import com.thorin.eduaps.databinding.ActivityListQuestBinding
import com.thorin.eduaps.databinding.ActivityTest2ResultBinding
import com.thorin.eduaps.ui.home.test.ActivityListQuest

class TestResultActivity : AppCompatActivity() {

    private var _binding: ActivityTest2ResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var refUser: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityTest2ResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        saveProgress()

        val prefPreTest2: SharedPreferences =
            this.getSharedPreferences("prefPreTest2", Context.MODE_PRIVATE)
//        prefPreTest2.getString("scorePreTest2", null)

        binding.textView5.text = prefPreTest2.getString("scorePreTest2", null)

        binding.button5.setOnClickListener {
            Intent(this, ActivityListQuest::class.java).also {
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
        userHashMap["pretest"] = "selesai"

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

}