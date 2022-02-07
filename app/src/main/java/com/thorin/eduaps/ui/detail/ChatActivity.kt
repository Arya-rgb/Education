package com.thorin.eduaps.ui.detail

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thorin.eduaps.databinding.ActivityChatBinding
import com.thorin.eduaps.viewmodel.ChatViewModel
import com.thorin.eduaps.viewmodel.adapter.ChatAdapter
import com.thorin.eduaps.viewmodel.factory.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity() {

    private var _binding: ActivityChatBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth

    private lateinit var dbRef: DatabaseReference
//    private lateinit var userRecyclerView: RecyclerView
//    private lateinit var chatArrayList: ArrayList<ChatResponse>

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[ChatViewModel::class.java]

        val chatAdapter = ChatAdapter()

        val soalId = intent.getStringExtra("chat_id")

        viewModel.getChatData(soalId.toString()).observe(this) { data ->
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

            getDataChat()

        }
    }

    private fun getDataChat() {

        val progressdialog = ProgressDialog(this)
        progressdialog.setMessage("Mengirim Data...")

        progressdialog.show()

        mAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().reference

        val date = Date()
        val formatter = SimpleDateFormat("yyyyMMddHHmmssSSSSSS")
        val answer: String = formatter.format(date)
        val soalId = intent.getStringExtra("chat_id")
        dbRef = FirebaseDatabase.getInstance().reference.child(soalId.toString()).child(answer)
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
}