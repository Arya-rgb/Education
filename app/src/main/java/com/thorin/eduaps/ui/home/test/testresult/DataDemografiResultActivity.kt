package com.thorin.eduaps.ui.home.test.testresult

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thorin.eduaps.databinding.ActivityDataDemografiResultBinding

class DataDemografiResultActivity : AppCompatActivity() {

    private var _binding: ActivityDataDemografiResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var refUser: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDataDemografiResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        saveProgress()

    }

    private fun saveProgress() {

        val progressdialog = ProgressDialog(this)
        progressdialog.setMessage("Mengirim Data...")

        progressdialog.show()

        mAuth = FirebaseAuth.getInstance()
        refUser = FirebaseDatabase.getInstance().reference

        refUser = FirebaseDatabase.getInstance().reference.child("progress")
            .child(mAuth.currentUser?.uid.toString())
        val userHashMap = HashMap<String, Any>()
        userHashMap["uid"] = mAuth.currentUser?.uid.toString()
        userHashMap["data_demografi"] = "selesai"

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