package com.thorin.eduaps.viewmodel.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.thorin.eduaps.data.source.remote.response.ChatResponse
import com.thorin.eduaps.databinding.ChatItemBinding

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private var listChat = ArrayList<ChatResponse>()

    fun setDataChat(dataChat: List<ChatResponse>?) {

        if (dataChat != null) {
            this.listChat.clear()
            this.listChat.addAll(dataChat)
        }

    }


    class ViewHolder(private val binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dataChat: ChatResponse) {

            var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

            with(binding) {
                Glide.with(itemView.context)
                    .load(dataChat.profile_photo)
                    .into(imgThumbnail)
                messageSender.text = dataChat.username
                messageBody.text = dataChat.isi_pesan

                if (dataChat.uid.toString() == mAuth.currentUser?.uid.toString()) {
                    messageSender.setTextColor(Color.parseColor("#2196F3"))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemViewBinding =
            ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemViewBinding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dataChat = listChat[position]
        holder.bind(dataChat)

    }

    override fun getItemCount(): Int = listChat.size
}