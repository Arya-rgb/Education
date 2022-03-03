package com.thorin.eduaps.viewmodel.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thorin.eduaps.R
import com.thorin.eduaps.data.source.remote.response.ListPelajaranResponse
import com.thorin.eduaps.databinding.ItemBelajarBinding
import com.thorin.eduaps.ui.detail.DetailPelajaranPdf
import com.thorin.eduaps.ui.detail.DetailPelajaranVideo

class PelajaranAdapter : RecyclerView.Adapter<PelajaranAdapter.ViewHolder>() {

    private var listBelajar = ArrayList<ListPelajaranResponse>()

    fun setDataBelajar(dataBelajar: List<ListPelajaranResponse>?) {

        //roundicons pro icon video
        //robbin lee icon dokumen

        //Success animation
        //Originally by Franklin Lopez. Changed only colors!

        if (dataBelajar != null) {
            this.listBelajar.clear()
            this.listBelajar.addAll(dataBelajar)
        }
    }

    class ViewHolder(private val binding: ItemBelajarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataBelajar: ListPelajaranResponse) {
            with(binding) {
                tvItemTitle.text = dataBelajar.judul_pelajaran
                tvItemSubtitle.text = dataBelajar.jenis_file
                Glide.with(itemView.context)
                    .load(dataBelajar.z_linkgambar)
                    .into(ivItemImage)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemViewbinding =
            ItemBelajarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemViewbinding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dataBelajar = listBelajar[position]
        holder.bind(dataBelajar)

        val dataPelajaran = ListPelajaranResponse(
            dataBelajar.deskripsi_pelajaran,
            dataBelajar.id_soal,
            dataBelajar.jenis_file,
            dataBelajar.judul_pelajaran,
            dataBelajar.jumlah_slide,
            dataBelajar.link_file
        )


        holder.itemView.setOnClickListener {
            if (dataBelajar.jenis_file == "pdf") {
                val moveDetailPdf = Intent(holder.itemView.context, DetailPelajaranPdf::class.java)
                moveDetailPdf.putExtra(DetailPelajaranPdf.EXTRA_PELAJARAN, dataPelajaran)
                holder.itemView.context.startActivity(moveDetailPdf)
            } else {
                val moveDetailPdf =
                    Intent(holder.itemView.context, DetailPelajaranVideo::class.java)
                moveDetailPdf.putExtra(DetailPelajaranVideo.EXTRA_PELAJARAN, dataPelajaran)
                holder.itemView.context.startActivity(moveDetailPdf)
            }
        }

    }

    override fun getItemCount(): Int = listBelajar.size
}