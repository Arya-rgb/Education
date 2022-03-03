package com.thorin.eduaps.ui.detail

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.danjdt.pdfviewer.PdfViewer
import com.danjdt.pdfviewer.interfaces.OnErrorListener
import com.danjdt.pdfviewer.interfaces.OnPageChangedListener
import com.danjdt.pdfviewer.utils.PdfPageQuality
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thorin.eduaps.R
import com.thorin.eduaps.data.source.remote.response.ListPelajaranResponse
import com.thorin.eduaps.databinding.ActivityPdfviewerBinding
import com.thorin.eduaps.viewmodel.ChatViewModel
import com.thorin.eduaps.viewmodel.adapter.ChatAdapter
import com.thorin.eduaps.viewmodel.adapter.PdfRecyclerViewAdapter
import com.thorin.eduaps.viewmodel.factory.ViewModelFactory
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class DetailPelajaranPdf : AppCompatActivity(), OnPageChangedListener, OnErrorListener {

    private var _binding: ActivityPdfviewerBinding? = null
    private val binding get() = _binding!!

    private lateinit var pdfRecylerview: PdfRecyclerViewAdapter

    private lateinit var mAuth: FirebaseAuth

    private lateinit var dbRef: DatabaseReference

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityPdfviewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pdfRecylerview = PdfRecyclerViewAdapter(this)

        supportActionBar?.hide()

        val prefPreTest2: SharedPreferences =
            this.getSharedPreferences("state_info_rotate_pdf", Context.MODE_PRIVATE)
        val status = prefPreTest2.getString("status", null)

        if (!status.equals("sudah")) {
            alertInfo()
        }


        val dataPelajaran =
            intent.getParcelableExtra<ListPelajaranResponse>(EXTRA_PELAJARAN) as ListPelajaranResponse

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[ChatViewModel::class.java]

        val chatAdapter = ChatAdapter()

        viewModel.getChatData(dataPelajaran.id_soal.toString()).observe(this) { data ->
            if (data != null) {
                chatAdapter.setDataChat(data)
                chatAdapter.notifyDataSetChanged()
            }
        }

        PdfViewer.Builder(binding.rootView)
            .view(PdfRecyclerViewAdapter(this))
            .setMaxZoom(3f)
            .setZoomEnabled(true)
            .quality(PdfPageQuality.QUALITY_1080)
            .setOnErrorListener(this)
            .setOnPageChangedListener(this)
            .build()
            .load(dataPelajaran.link_file.toString())


        with(binding.recyclerView) {
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            this.adapter = chatAdapter
        }

        binding.btSend.setOnClickListener {

            when {
                binding.etMessage.text.isNullOrBlank() -> {
                    Snackbar.make(
                        binding.root,
                        "Silahkan isi pesan terlebih dahulu ya...",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    getDataChat()

                }
            }


        }

    }

    private fun alertInfo() {
        val builder = AlertDialog.Builder(this)
        builder.setView(LayoutInflater.from(this).inflate(R.layout.alert_rotate_phone, null))
        builder.setPositiveButton("Oke") { dialog, _ ->
            val prefPreTest2: SharedPreferences =
                this.getSharedPreferences("state_info_rotate_pdf", Context.MODE_PRIVATE)
            val edit = prefPreTest2.edit()
            edit.putString("status", "sudah")
            edit.apply()
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Checks the orientation of the screen
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            binding.rootView.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            binding.frameLayout.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            binding.frameLayout.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT


        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            binding.rootView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            binding.frameLayout.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            binding.frameLayout.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
        }
    }

    private fun getDataChat() {

        val progressdialog = ProgressDialog(this)
        progressdialog.setMessage("Mengirim Data...")

        val preTestData: SharedPreferences =
            this.getSharedPreferences("dataUser", Context.MODE_PRIVATE)

        progressdialog.show()

        mAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().reference

        val dataPelajaran =
            intent.getParcelableExtra<ListPelajaranResponse>(EXTRA_PELAJARAN) as ListPelajaranResponse

        val date = Date()
        val formatter = SimpleDateFormat("yyyyMMddHHmmssSSSSSS")
        val answer: String = formatter.format(date)
        val soalId = dataPelajaran.id_soal.toString()
        dbRef = FirebaseDatabase.getInstance().reference.child(soalId).child(answer)
        val userHashMap = HashMap<String, Any>()
        userHashMap["uid"] = mAuth.currentUser?.uid.toString()
        userHashMap["profile_photo"] = mAuth.currentUser?.photoUrl.toString()
        userHashMap["username"] = preTestData.getString("nama_user", mAuth.currentUser?.email).toString()
        userHashMap["isi_pesan"] = binding.etMessage.text.toString()
        dbRef.updateChildren(userHashMap)
            .addOnCompleteListener { tasks ->
                if (tasks.isSuccessful) {
                    binding.etMessage.setText("")
                    progressdialog.dismiss()
                } else {
                    progressdialog.dismiss()
                    Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                }
            }

    }

    override fun onPageChanged(page: Int, total: Int) {
        binding.tvCounter.text = getString(R.string.pdf_page_counter, page, total)
    }

    override fun onFileLoadError(e: Exception) {
        //Handle error ...
        e.printStackTrace()
    }

    override fun onAttachViewError(e: Exception) {
        //Handle error ...
        e.printStackTrace()
    }

    override fun onPdfRendererError(e: IOException) {
        //Handle error ...
        e.printStackTrace()
    }

    companion object {
        const val EXTRA_PELAJARAN = "extra_pelajaran"
    }


}
