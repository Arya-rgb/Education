package com.thorin.eduaps.ui.home.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thorin.eduaps.databinding.ActivityListQuestBinding
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences


class ActivityListQuest : AppCompatActivity() {

    private var _binding: ActivityListQuestBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityListQuestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progressdialog = ProgressDialog(applicationContext)
        progressdialog.setMessage("Memuat Data...")

        binding.navTest1.setOnClickListener {
            Intent(this, DataDemografiActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.navTest2.setOnClickListener {
            Intent(this, TestActivity::class.java).also {
                startActivity(it)
            }
        }

        val prefPreTest2: SharedPreferences =
            this.getSharedPreferences("prefPreTest2", Context.MODE_PRIVATE)
        val edit = prefPreTest2.edit()
        edit.putString("scorePreTest2", "0")
        edit.apply()

    }
}