package com.thorin.eduaps.ui.home.pelajaran

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thorin.eduaps.databinding.ActivityPelajaranBinding
import com.thorin.eduaps.viewmodel.PelajaranViewModel
import com.thorin.eduaps.viewmodel.adapter.PelajaranAdapter
import com.thorin.eduaps.viewmodel.factory.ViewModelFactory

class PelajaranActivity : AppCompatActivity() {

    private var _binding: ActivityPelajaranBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityPelajaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[PelajaranViewModel::class.java]

        val pelajaranAdapter = PelajaranAdapter()

        viewModel.getPelajaranData().observe(this) { data ->
            if (data != null) {
                pelajaranAdapter.setDataBelajar(data)
                pelajaranAdapter.notifyDataSetChanged()
            }
        }

        with(binding.recyclerView) {
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            this.adapter = pelajaranAdapter
        }
    }
}