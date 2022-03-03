package com.thorin.eduaps.ui.home.pelajaran

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thorin.eduaps.R
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

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = "Pelajaran"

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

        binding.fab.setOnClickListener {
            alertInfo()
        }

    }

    private fun alertInfo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Info Pembelajaran")
        builder.setMessage(
            """
            Pada kolom ini terdapat informasi pencegahan kekerasan seksual yang ditayangkan dalam bentuk slide dan videdo. Untuk masuk ke layar penuh (Fullscreen), silahkan untuk memiringkan (rotate) android (HP) yang anda gunakan untuk pengalaman belajar yang menarik. 
        """.trimIndent()
        )
        builder.setPositiveButton("Ya") { dialog, _ ->
            dialog.dismiss()

        }


        val alert = builder.create()
        alert.show()
    }
}