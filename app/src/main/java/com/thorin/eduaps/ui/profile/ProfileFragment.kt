package com.thorin.eduaps.ui.profile

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.thorin.eduaps.R
import com.thorin.eduaps.data.source.remote.response.UserResponse
import com.thorin.eduaps.databinding.FragmentProfileBinding
import com.thorin.eduaps.ui.login.LoginActivity
import com.thorin.eduaps.viewmodel.ProfileViewModel
import com.thorin.eduaps.viewmodel.factory.ViewModelFactory

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]

        mAuth = FirebaseAuth.getInstance()

        val currentUser = mAuth.currentUser

        if(currentUser != null) {

            viewModel.getUserProfile().observe(viewLifecycleOwner) {
                getData(it)
            }

        } else {

            Toast.makeText(
                activity, "Data Kosong",
                Toast.LENGTH_LONG).show()

        }

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.idBtnLogout.setOnClickListener {

            userChoiceLogout()

        }

        return binding.root


    }

    private fun userChoiceLogout() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Apakah anda yakin ?")
        builder.setMessage(
            """
            Anda akan keluar aplikasi setelah logout !
        """.trimIndent()
        )
        builder.setPositiveButton("Ya") { _, _ ->

            val prefPreTest2: SharedPreferences? =
                activity?.getSharedPreferences("persetujuan", Context.MODE_PRIVATE)

            prefPreTest2?.edit()?.clear()?.apply()

            mAuth.signOut()
            Intent(activity, LoginActivity::class.java).also { back ->
                startActivity(back)
                activity?.finish()
            }
        }
        builder.setNegativeButton("Tidak") { dialog, _ -> // Do nothing
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun getData(it: UserResponse) {

        Glide.with(this)
            .load(it.photoUrl)
            .into(binding.imageView2)

        binding.idNama.text = it.nameUser
        binding.idEmail.text = it.emailUser

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}