package com.thorin.eduaps.ui.home

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thorin.eduaps.databinding.FragmentHomeBinding
import com.thorin.eduaps.ui.home.pelajaran.PelajaranActivity
import com.thorin.eduaps.ui.home.test.pretest.DataDemografiActivity
import com.thorin.eduaps.ui.home.test.posttest.PostTestActivity
import com.thorin.eduaps.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })


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
            Intent(activity, PostTestActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.navBelajar.setOnClickListener {
            Intent(activity, PelajaranActivity::class.java).also {
                startActivity(it)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}