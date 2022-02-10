package com.thorin.eduaps.ui.home

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.thorin.eduaps.data.source.remote.response.ProgressResponse
import com.thorin.eduaps.databinding.FragmentHomeBinding
import com.thorin.eduaps.ui.home.pelajaran.PelajaranActivity
import com.thorin.eduaps.ui.home.test.posttest.PostTestActivity
import com.thorin.eduaps.ui.home.test.pretest.DataDemografiActivity
import com.thorin.eduaps.viewmodel.HomeViewModel
import com.thorin.eduaps.viewmodel.ProgressViewModel
import com.thorin.eduaps.viewmodel.factory.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[ProgressViewModel::class.java]

        viewModel.getProgress().observe(viewLifecycleOwner) {
            getData(it)
        }

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val progressdialog = ProgressDialog(activity)
        progressdialog.setMessage("Memuat Data...")

        binding.navPretest.setOnClickListener {
            progressdialog.show()
            Intent(activity, DataDemografiActivity::class.java).also {
                startActivity(it)
                activity?.finish()
            }
        }

        binding.navPostTest.setOnClickListener {
            if (binding.idBagian2Selesai.text.equals("Belum Selesai")) {
                progressHaltPostTest()
            } else {
                Intent(activity, PostTestActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

        binding.navBelajar.setOnClickListener {

            if (binding.idBagian1Selesai.text.equals("Belum Selesai")) {
                progressHalt()
            } else {
                Intent(activity, PelajaranActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

        return binding.root
    }

    private fun getData(it: ProgressResponse) {

        if (it.Status_Pre_Test != "null") {
            binding.idBagian1Selesai.text = it.Status_Pre_Test
        } else {
            binding.idBagian1Selesai.text = "Belum Selesai"
        }

        if (it.Status_Belajar != "null") {
            binding.idBagian2Selesai.text = it.Status_Belajar
        } else {
            binding.idBagian2Selesai.text = "Belum Selesai"
        }

        if (it.Status_Post_Test != "null") {
           binding.idBagian3Selesai.text =  it.Status_Post_Test
        } else {
            binding.idBagian3Selesai.text = "Belum Selesai"
        }

    }

    private fun progressHalt() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Tidak Bisa Di Buka")
        builder.setMessage(
            """
            Anda Belum Menyelesaikan Pretest, Selesaikan Terlebih Dahulu Ya.
        """.trimIndent()
        )
        builder.setPositiveButton("Ya") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }


    private fun progressHaltPostTest() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Tidak Bisa Di Buka")
        builder.setMessage(
            """
            Anda Belum Menyelesaikan Progress Belajar, Selesaikan Terlebih Dahulu Ya.
        """.trimIndent()
        )
        builder.setPositiveButton("Ya") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}