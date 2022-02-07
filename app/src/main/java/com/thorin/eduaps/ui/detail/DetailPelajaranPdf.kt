package com.thorin.eduaps.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.danjdt.pdfviewer.PdfViewer
import com.danjdt.pdfviewer.interfaces.OnErrorListener
import com.danjdt.pdfviewer.interfaces.OnPageChangedListener
import com.danjdt.pdfviewer.utils.PdfPageQuality
import com.thorin.eduaps.R
import com.thorin.eduaps.data.source.remote.response.ListPelajaranResponse
import com.thorin.eduaps.databinding.ActivityPdfviewerBinding
import com.thorin.eduaps.viewmodel.adapter.PdfRecyclerViewAdapter
import java.io.IOException


class DetailPelajaranPdf : AppCompatActivity(), OnPageChangedListener, OnErrorListener {

    private var _binding: ActivityPdfviewerBinding? = null
    private val binding get() = _binding!!

    private lateinit var pdfRecylerview : PdfRecyclerViewAdapter

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityPdfviewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pdfRecylerview = PdfRecyclerViewAdapter(this)

        PdfViewer.Builder(binding.rootView)
            .view(PdfRecyclerViewAdapter(this))
            .setMaxZoom(3f)
            .setZoomEnabled(true)
            .quality(PdfPageQuality.QUALITY_1080)
            .setOnErrorListener(this)
            .setOnPageChangedListener(this)
            .build()
            .load("https://fv9-4.failiem.lv/down.php?i=bw7bxvaa8")

        //-----webview------

//        binding.webView.settings.javaScriptEnabled = true
//
//        val progressdialog = ProgressDialog(this)
//        progressdialog.setMessage("Memuat Data...")
//
//        progressdialog.show()
//
//        binding.webView.webViewClient = object : WebViewClient() {
//
//            override fun onReceivedError(
//                view: WebView,
//                errorCode: Int,
//                description: String,
//                failingUrl: String
//            ) {
//                try {
//                    binding.webView.stopLoading()
//                } catch (e: Exception) {
//                }
//                try {
//                    binding.webView.clearView()
//                } catch (e: Exception) {
//                }
//                if (binding.webView.canGoBack()) {
//                    binding.webView.goBack()
//                }
//                binding.webView.loadUrl("about:blank")
//                val alertDialog = AlertDialog.Builder(this@DetailPelajaranPdf).create()
//                alertDialog.setTitle("Gagal Membuka")
//                alertDialog.setMessage(
//                    """
//                    Pastikan android anda terhubung ke internet lalu coba lagi !
//                """.trimIndent()
//                )
//                alertDialog.setButton(
//                    DialogInterface.BUTTON_POSITIVE,
//                    "Ok",
//                    DialogInterface.OnClickListener { _: DialogInterface, _: Int ->
//                        moveBack()
//                    })
//                alertDialog.show()
//                super.onReceivedError(binding.webView, errorCode, description, failingUrl)
//            }
//
//            override fun onPageFinished(view: WebView?, url: String?) {
//                super.onPageFinished(view, url)
//                progressdialog.dismiss()
//            }
//
//        }
//
//        val dataPelajaran = intent.getParcelableExtra<ListPelajaranResponse>(EXTRA_PELAJARAN) as ListPelajaranResponse
//
//            binding.webView.loadUrl(dataPelajaran.link_file.toString())
//
//    }
//
//    private fun moveBack() {
//        Intent(this, MainActivity::class.java).also {
//            startActivity(it)
//            finish()
//        }
//    }
//
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.pelajaran_pdf_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.comment -> {
//                val dataPelajaran = intent.getParcelableExtra<ListPelajaranResponse>(EXTRA_PELAJARAN) as ListPelajaranResponse
//                intent = Intent(this, ChatActivity::class.java)
//                intent.putExtra("chat_id", dataPelajaran.id_soal.toString())
//                startActivity(intent)
//                finish()
//                return true
//            }
//            else -> true
//        }

        //-----webview------


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.pelajaran_pdf_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.comment -> {
                val dataPelajaran =
                    intent.getParcelableExtra<ListPelajaranResponse>(EXTRA_PELAJARAN) as ListPelajaranResponse
                intent = Intent(this, ChatActivity::class.java)
                intent.putExtra("chat_id", dataPelajaran.id_soal.toString())
                startActivity(intent)
                finish()
                return true
            }
            else -> true
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
