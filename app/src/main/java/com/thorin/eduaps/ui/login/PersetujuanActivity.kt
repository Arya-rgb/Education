package com.thorin.eduaps.ui.login

import android.content.Intent
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.thorin.eduaps.R
import com.thorin.eduaps.databinding.ActivityPersetujuanBinding

class PersetujuanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersetujuanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPersetujuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = "Persetujuan"
        actionBar?.title = " "
        binding.fab.setOnClickListener {
            Intent(this, ActivityIsiNama::class.java).also {
                startActivity(it)
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            }
        }
    }
}