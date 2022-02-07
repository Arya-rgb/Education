package com.thorin.eduaps.ui.detail

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.thorin.eduaps.R
import com.thorin.eduaps.data.source.remote.response.ListPelajaranResponse
import com.thorin.eduaps.databinding.ActivityDetailPelajaranVideo2Binding
import com.thorin.eduaps.viewmodel.ChatViewModel
import com.thorin.eduaps.viewmodel.adapter.ChatAdapter
import com.thorin.eduaps.viewmodel.factory.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class DetailPelajaranVideo : AppCompatActivity() {


    private var _binding: ActivityDetailPelajaranVideo2Binding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth

    private lateinit var dbRef: DatabaseReference

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailPelajaranVideo2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val dataPelajaran =
            intent.getParcelableExtra<ListPelajaranResponse>(DetailPelajaranPdf.EXTRA_PELAJARAN) as ListPelajaranResponse

        val youtubePlayerView: YouTubePlayerView = binding.youtubeVideoPlayer
        lifecycle.addObserver(youtubePlayerView)
        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)

                val videoId = dataPelajaran.link_file
                youTubePlayer.loadVideo(videoId.toString(), 0F)
            }
        })

        val prefPreTest2: SharedPreferences =
            this.getSharedPreferences("state_info_rotate", Context.MODE_PRIVATE)
        val status = prefPreTest2.getString("status", null)

        if (!status.equals("sudah")) {
            alertInfo()
        }

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[ChatViewModel::class.java]

        val chatAdapter = ChatAdapter()

        viewModel.getChatData(dataPelajaran.id_soal.toString()).observe(this) { data ->
            if (data != null) {
                chatAdapter.setDataChat(data)
                chatAdapter.notifyDataSetChanged()
            }
        }

        with(binding.recyclerView) {
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            this.adapter = chatAdapter
        }

        binding.btSend.setOnClickListener {
            sendata()
        }


    }

    private fun sendata() {
        val progressdialog = ProgressDialog(this)
        progressdialog.setMessage("Mengirim Data...")

        progressdialog.show()

        mAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().reference


        val date = Date()
        val formatter = SimpleDateFormat("yyyyMMddHHmmssSSSSSS")
        val answer: String = formatter.format(date)

        val dataPelajaran =
            intent.getParcelableExtra<ListPelajaranResponse>(DetailPelajaranPdf.EXTRA_PELAJARAN) as ListPelajaranResponse

        dbRef = FirebaseDatabase.getInstance().reference.child(dataPelajaran.id_soal.toString())
            .child(answer)
        val userHashMap = HashMap<String, Any>()
        userHashMap["uid"] = mAuth.currentUser?.uid.toString()
        userHashMap["profile_photo"] = mAuth.currentUser?.photoUrl.toString()
        userHashMap["username"] = mAuth.currentUser?.displayName.toString()
        userHashMap["isi_pesan"] = binding.etMessage.text.toString()
        dbRef.updateChildren(userHashMap)
            .addOnCompleteListener { tasks ->
                if (tasks.isSuccessful) {
                    progressdialog.dismiss()
                } else {
                    progressdialog.dismiss()
                    Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        const val EXTRA_PELAJARAN = "extra_pelajaran"
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            binding.youtubeVideoPlayer.enterFullScreen()


        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            binding.youtubeVideoPlayer.exitFullScreen()

        }
    }

    override fun onBackPressed() {
        if (binding.youtubeVideoPlayer.isFullScreen()) binding.youtubeVideoPlayer.exitFullScreen() else super.onBackPressed()
    }

    fun alertInfo() {
        val builder = AlertDialog.Builder(this)
        builder.setView(LayoutInflater.from(this).inflate(R.layout.alert_rotate_phone, null))
        builder.setPositiveButton("Oke") { dialog, _ ->
            val prefPreTest2: SharedPreferences =
                this.getSharedPreferences("state_info_rotate", Context.MODE_PRIVATE)
            val edit = prefPreTest2.edit()
            edit.putString("status", "sudah")
            edit.apply()
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

}