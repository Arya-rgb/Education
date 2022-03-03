package com.thorin.eduaps.ui.login

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.thorin.eduaps.R
import com.thorin.eduaps.databinding.ActivityLoginBinding
import com.thorin.eduaps.ui.DaftarActivity
import com.thorin.eduaps.ui.navigation.MainActivity

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignClient = GoogleSignIn.getClient(this, gso)

        mAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {

            signIn()

        }

        checkLogin()

        binding.buttonLogin.setOnClickListener {


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

                    mAuth.signInWithEmailAndPassword(
                        binding.idEmail.text.toString().trim(),
                        binding.idPassword.text.toString().trim()
                    )
                        .addOnSuccessListener {
                            progressdialog.dismiss()
                            Intent(this, PersetujuanActivity::class.java).also {
                                startActivity(it)
                                finish()
                            }
                        }
                        .addOnFailureListener { e ->
                            progressdialog.dismiss()
                            Toast.makeText(
                                this,
                                "Login Failed Due To ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                }
            }


        }

        binding.belumpunyaakun.setOnClickListener {
            Intent(this, DaftarActivity::class.java).also {
                startActivity(it)
            }
        }

    }

    private fun checkLogin() {

        val prefPreTest2: SharedPreferences =
            this.getSharedPreferences("persetujuan", Context.MODE_PRIVATE)

        val persetujuan = prefPreTest2.getString("setuju", null)

        val user = mAuth.currentUser

        if (null != user && persetujuan == "setuju") {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("Sign in Activity", "FirebaseAuthWithGoogle" + account.id)
                    fireBaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    Log.w("Sign in Activity", "Google Sign in failed")
                }
            } else {
                Log.w("Sign in Activity", exception.toString())
            }
        }
    }

    private fun fireBaseAuthWithGoogle(idToken: String) {

        val progressdialog = ProgressDialog(this)
        progressdialog.setMessage("Loading...")

        progressdialog.show()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Sign in Activity", "SignInWithCredential:success")

                    progressdialog.dismiss()
                    val intent = Intent(this, PersetujuanActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()

                } else {
                    progressdialog.dismiss()
                    Log.w("sign in activity", "SignInWithCredential:failure")
                }
            }
    }

    companion object {
        private const val RC_SIGN_IN = 120
    }

}