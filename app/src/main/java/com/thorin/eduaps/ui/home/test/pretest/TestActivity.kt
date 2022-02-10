package com.thorin.eduaps.ui.home.test.pretest

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thorin.eduaps.R
import com.thorin.eduaps.databinding.ActivityTest2Binding
import com.thorin.eduaps.ui.home.test.testresult.TestResultActivity
import com.thorin.eduaps.ui.navigation.MainActivity
import com.thorin.eduaps.viewmodel.Test2ViewModel
import com.thorin.eduaps.viewmodel.adapter.TestAdapter
import com.thorin.eduaps.viewmodel.factory.ViewModelFactory


class TestActivity : AppCompatActivity() {

    private var _binding: ActivityTest2Binding? = null
    private val binding get() = _binding!!

    private val myLinearLayoutManager =
        object : LinearLayoutManager(this, HORIZONTAL, false) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }

    private lateinit var refUser: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    private val dataTestAdapter = TestAdapter()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityTest2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[Test2ViewModel::class.java]

        viewModel.getTest2Data().observe(this) { data ->
            dataTestAdapter.setDataTest(data)
            dataTestAdapter.notifyDataSetChanged()
        }

        with(binding.rvSoal) {
            layoutManager = myLinearLayoutManager
            setHasFixedSize(true)
            adapter = dataTestAdapter

        }

        val preNextSoal: SharedPreferences =
            this.getSharedPreferences("prefNextSoal", Context.MODE_PRIVATE)
        val valueNext = preNextSoal.getString("prefNextSoal", null)

        if (valueNext?.contains("nextPage") == true) {
            binding.btnNextTest2.callOnClick()
        }

//        binding.btnNextTest2.setOnClickListener {
//
//        }
    }

    fun nextPage() {
        if (myLinearLayoutManager.findLastCompletelyVisibleItemPosition() < dataTestAdapter.getItemCount() - 1) {
            myLinearLayoutManager.scrollToPosition(myLinearLayoutManager.findLastCompletelyVisibleItemPosition() + 1)
        }
    }

    fun saveProgress1() {

        val progressdialog = ProgressDialog(this)
        progressdialog.setMessage("Menyimpan Progress...")

        progressdialog.show()

        mAuth = FirebaseAuth.getInstance()
        refUser = FirebaseDatabase.getInstance().reference

        val prefPreTest2: SharedPreferences =
            this.getSharedPreferences("prefPreTest2", Context.MODE_PRIVATE)

        refUser = FirebaseDatabase.getInstance().reference.child("nilai_test")
            .child(mAuth.currentUser?.uid.toString())
        val userHashMap = HashMap<String, Any>()
        userHashMap["nilaiPreTest1"] = prefPreTest2.getString("scorePreTest2", null).toString()

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

    fun saveProgress2() {

        val progressdialog = ProgressDialog(this)
        progressdialog.setMessage("Menyimpan Progress...")

        progressdialog.show()

        mAuth = FirebaseAuth.getInstance()
        refUser = FirebaseDatabase.getInstance().reference

        val prefPreTest2: SharedPreferences =
            this.getSharedPreferences("prefPreTest2", Context.MODE_PRIVATE)

        refUser = FirebaseDatabase.getInstance().reference.child("nilai_test")
            .child(mAuth.currentUser?.uid.toString())
        val userHashMap = HashMap<String, Any>()
        userHashMap["nilaiPreTest2"] = prefPreTest2.getString("scorePreTest2", null).toString()

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

    fun saveProgress3() {

        val progressdialog = ProgressDialog(this)
        progressdialog.setMessage("Menyimpan Progress...")

        progressdialog.show()

        mAuth = FirebaseAuth.getInstance()
        refUser = FirebaseDatabase.getInstance().reference

        val prefPreTest2: SharedPreferences =
            this.getSharedPreferences("prefPreTest2", Context.MODE_PRIVATE)

        refUser = FirebaseDatabase.getInstance().reference.child("nilai_test")
            .child(mAuth.currentUser?.uid.toString())
        val userHashMap = HashMap<String, Any>()
        userHashMap["nilaiPreTest3"] = prefPreTest2.getString("scorePreTest2", null).toString()

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

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Apakah Anda Yakin ?.")
        builder.setMessage(
            """
            Jika Ujian ini di hentikan, anda harus memulai dari awal.
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

    fun alertInfo1() {
        val prefPreTest2: SharedPreferences =
            this.getSharedPreferences("prefPreTest2", Context.MODE_PRIVATE)
        val edit = prefPreTest2.edit()
        edit.putString("scorePreTest2", "0")
        edit.apply()
        val builder = AlertDialog.Builder(this)
        builder.setView(LayoutInflater.from(this).inflate(R.layout.alert_info1, null))
        builder.setPositiveButton("Ya") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    fun alertInfo2() {
        val prefPreTest2: SharedPreferences =
            this.getSharedPreferences("prefPreTest2", Context.MODE_PRIVATE)
        val edit = prefPreTest2.edit()
        edit.putString("scorePreTest2", "0")
        edit.apply()
        val builder = AlertDialog.Builder(this)
        builder.setView(LayoutInflater.from(this).inflate(R.layout.alert_info2, null))
        builder.setPositiveButton("Ya") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    fun alertInfo3() {
        val prefPreTest2: SharedPreferences =
            this.getSharedPreferences("prefPreTest2", Context.MODE_PRIVATE)
        val edit = prefPreTest2.edit()
        edit.putString("scorePreTest2", "0")
        edit.apply()
        val builder = AlertDialog.Builder(this)
        builder.setView(LayoutInflater.from(this).inflate(R.layout.alert_info3, null))
        builder.setPositiveButton("Ya") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    fun alertInfo4() {
        val prefPreTest2: SharedPreferences =
            this.getSharedPreferences("prefPreTest2", Context.MODE_PRIVATE)
        val edit = prefPreTest2.edit()
        edit.putString("scorePreTest2", "0")
        edit.apply()
        val builder = AlertDialog.Builder(this)
        builder.setView(LayoutInflater.from(this).inflate(R.layout.alert_info4, null))
        builder.setPositiveButton("Ya") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    fun movePreTest() {

        val progressdialog = ProgressDialog(this)
        progressdialog.setMessage("Menyimpan Progress...")

        progressdialog.show()

        mAuth = FirebaseAuth.getInstance()
        refUser = FirebaseDatabase.getInstance().reference

        val prefPreTest2: SharedPreferences =
            this.getSharedPreferences("prefPreTest2", Context.MODE_PRIVATE)

        refUser = FirebaseDatabase.getInstance().reference.child("nilai_test")
            .child(mAuth.currentUser?.uid.toString())
        val userHashMap = HashMap<String, Any>()
        userHashMap["nilaiPreTest4"] = prefPreTest2.getString("scorePreTest2", null).toString()

        refUser.updateChildren(userHashMap)
            .addOnCompleteListener { tasks ->
                if (tasks.isSuccessful) {
                    progressdialog.dismiss()
                    Intent(this, TestResultActivity::class.java).also { hahay ->
                        startActivity(hahay)
                        finish()
                    }
                    Toast.makeText(this, "Progress Di Simpan", Toast.LENGTH_SHORT).show()
                } else {
                    progressdialog.dismiss()
                    Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                }
            }
    }

}

