package com.thorin.eduaps.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.thorin.eduaps.databinding.ActivityDaftarBinding
import com.thorin.eduaps.ui.login.PersetujuanActivity

class DaftarActivity : AppCompatActivity() {

    private var _binding: ActivityDaftarBinding? = null
    private val binding get() = _binding!!

    private val ref = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDaftar.setOnClickListener {


            when {
                binding.idEmail.text.isNullOrBlank() -> {
                    Snackbar.make(
                        binding.root,
                        "Silahkan isi form email dulu ya...",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                binding.idPassword.text.isNullOrBlank() -> {
                    Snackbar.make(
                        binding.root,
                        "Silahkan isi form password dulu ya...",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                else -> {

                    val progressdialog = ProgressDialog(this)
                    progressdialog.setMessage("Loading...")

                    progressdialog.show()

                    ref.createUserWithEmailAndPassword(
                        binding.idEmail.text.toString().trim(),
                        binding.idPassword.text.toString().trim()
                    )
                        .addOnSuccessListener {
                            progressdialog.dismiss()
                            val email = ref.currentUser?.email
                            Toast.makeText(this, "Daftar Berhasil Dengan Email $email", Toast.LENGTH_SHORT)
                                .show()
                            startActivity(Intent(this, PersetujuanActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener { e ->
                            progressdialog.dismiss()
                            Toast.makeText(
                                this,
                                "Register Failed Due To ${e.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                }
            }

        }
    }
}