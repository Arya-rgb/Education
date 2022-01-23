package com.thorin.eduaps.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.thorin.eduaps.data.source.remote.response.UserResponse
import com.thorin.eduaps.databinding.FragmentProfileBinding
import com.thorin.eduaps.viewmodel.ProfileViewModel
import com.thorin.eduaps.viewmodel.factory.ViewModelFactory

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]

        viewModel.getUserProfile().observe(viewLifecycleOwner, {
            getData(it)
        })

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
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