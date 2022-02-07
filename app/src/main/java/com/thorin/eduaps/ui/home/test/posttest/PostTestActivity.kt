package com.thorin.eduaps.ui.home.test.posttest

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thorin.eduaps.R
import com.thorin.eduaps.databinding.ActivityPostTestBinding
import com.thorin.eduaps.ui.home.test.testresult.PostTestResultActivity
import com.thorin.eduaps.ui.navigation.MainActivity
import com.thorin.eduaps.viewmodel.Test2ViewModel
import com.thorin.eduaps.viewmodel.adapter.TestAdapter
import com.thorin.eduaps.viewmodel.factory.ViewModelFactory

class PostTestActivity : AppCompatActivity() {

    private var _binding: ActivityPostTestBinding? = null
    private val binding get() = _binding!!

    private val myLinearLayoutManager =
        object : LinearLayoutManager(this, HORIZONTAL, false) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }

    val dataTestAdapter = TestAdapter()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityPostTestBinding.inflate(layoutInflater)
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

    }

    fun nextPage() {
        if (myLinearLayoutManager.findLastCompletelyVisibleItemPosition() < dataTestAdapter.getItemCount() - 1) {
            myLinearLayoutManager.scrollToPosition(myLinearLayoutManager.findLastCompletelyVisibleItemPosition() + 1)
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
        val builder = AlertDialog.Builder(this)
        builder.setView(LayoutInflater.from(this).inflate(R.layout.alert_info1, null))
        builder.setPositiveButton("Ya") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    fun alertInfo2() {
        val builder = AlertDialog.Builder(this)
        builder.setView(LayoutInflater.from(this).inflate(R.layout.alert_info2, null))
        builder.setPositiveButton("Ya") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    fun alertInfo3() {
        val builder = AlertDialog.Builder(this)
        builder.setView(LayoutInflater.from(this).inflate(R.layout.alert_info3, null))
        builder.setPositiveButton("Ya") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    fun alertInfo4() {
        val builder = AlertDialog.Builder(this)
        builder.setView(LayoutInflater.from(this).inflate(R.layout.alert_info4, null))
        builder.setPositiveButton("Ya") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    fun movePostTest() {
        Intent(this, PostTestResultActivity::class.java).also { hahay ->
            startActivity(hahay)
            finish()
        }
    }

}