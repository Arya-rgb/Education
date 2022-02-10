package com.thorin.eduaps.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thorin.eduaps.databinding.ActivityLoginBinding
import com.thorin.eduaps.databinding.ActivityPersetujuanUserBinding

class PersetujuanUserActivity : AppCompatActivity() {

    private var _binding: ActivityPersetujuanUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityPersetujuanUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnSelanjutnya.setOnClickListener {

            Intent(this, ActivityIsiNama::class.java).also {
                startActivity(it)
            }

        }

    }
}