package com.thorin.eduaps.ui.home

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.thorin.eduaps.R
import com.thorin.eduaps.data.source.remote.response.ProfileResponse
import com.thorin.eduaps.data.source.remote.response.ProgressResponse
import com.thorin.eduaps.data.source.remote.response.UserResponse
import com.thorin.eduaps.databinding.FragmentHomeBinding
import com.thorin.eduaps.ui.home.pelajaran.PelajaranActivity
import com.thorin.eduaps.ui.home.test.posttest.PostTestActivity
import com.thorin.eduaps.ui.home.test.pretest.DataDemografiActivity
import com.thorin.eduaps.viewmodel.ProfileViewModel
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
        val viewModel2 = ViewModelProvider(this, factory)[ProfileViewModel::class.java]

        viewModel2.getUserProfile().observe(viewLifecycleOwner) {
            getData2(it)
        }


        viewModel.getProgress().observe(viewLifecycleOwner) {
            getData(it)
        }

        viewModel2.getUserData().observe(viewLifecycleOwner) {
            getdata3(it)
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

            if (binding.idBagian1Selesai.text.equals("Belum Selesai") || binding.idBagian1Selesai.text.equals("Belum Ada Progress/Memuat Progress...")) {
                progressHalt()
            } else {
                Intent(activity, PostTestActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

        binding.navBelajar.setOnClickListener {

            if (binding.idBagian1Selesai.text.equals("Belum Selesai") || binding.idBagian1Selesai.text.equals("Belum Ada Progress/Memuat Progress...")) {
                progressHalt()
            } else {
                Intent(activity, PelajaranActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

        return binding.root

    }

    private fun getdata3(it: ProfileResponse?) {

        val preTestData: SharedPreferences =
            requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        val edit = preTestData.edit()

        val alamatResponden = preTestData.getString("alamat_responden", null)
        val lokasiResponden = preTestData.getString("nama_user", null)

        if (alamatResponden.isNullOrEmpty() || lokasiResponden.isNullOrEmpty()) {
            edit.putString("nama_user", it?.alamat_responden)
            edit.putString("alamat_responden", it?.nama_responden)
            edit.apply()
        } else {
            binding.nameUser.text = "Selamat Datang ${preTestData.getString("nama_user", "Tidak Di Ketahui")}"
            binding.lokasi.text = preTestData.getString("alamat_responden", "Tidak Di Ketahui")
        }

        binding.nameUser.text = "Selamat Datang ${preTestData.getString("nama_user", "Tidak Di Ketahui")}"
        binding.lokasi.text = preTestData.getString("alamat_responden", "Tidak Di Ketahui")
    }


    private fun getData2(it: UserResponse?) {
//        binding.nameUser.text = "Selamat Datang ${it?.nameUser}"

        Glide.with(this)
            .load(it?.photoUrl)
            .placeholder(R.drawable.ic_default_profile)
            .into(binding.imgUser)
    }

    private fun getData(it: ProgressResponse) {

        if (it.Status_Pre_Test != "null") {
            binding.idBagian1Selesai.text = it.Status_Pre_Test
            binding.idBagian1Selesai.visibility = View.VISIBLE
        } else {
            binding.idBagian1Selesai.text = "Belum Selesai"
            binding.idBagian1Selesai.visibility = View.VISIBLE
        }

        if (it.Status_Post_Test != "null") {
            binding.idBagian3Selesai.text = it.Status_Post_Test
            binding.idBagian3Selesai.visibility = View.VISIBLE
        } else {
            binding.idBagian3Selesai.text = "Belum Selesai"
            binding.idBagian3Selesai.visibility = View.VISIBLE
        }

    }

    private fun progressHalt() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Tidak Bisa Di Buka")
        builder.setMessage(
            """
            Anda Belum Menyelesaikan Pretest, Selesaikan Terlebih Dahulu Ya, pastikan pelajari materi terlebih dahulu sebelum mengambil post test, Semangat !
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