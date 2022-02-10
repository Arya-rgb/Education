package com.thorin.eduaps.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.thorin.eduaps.ui.navigation.MainActivity
import com.thorin.eduaps.R
import com.thorin.eduaps.databinding.ActivityLoginBinding

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

        binding.idLogin.setOnClickListener {

            signIn()

        }

        checkLogin()

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
        binding.idProgressbar.visibility = View.VISIBLE
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Sign in Activity", "SignInWithCredential:success")

                    binding.idProgressbar.visibility = View.GONE
                    val intent = Intent(this, PersetujuanUserActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()

                } else {
                    binding.idProgressbar.visibility = View.GONE
                    Log.w("sign in activity", "SignInWithCredential:failure")
                }
            }
    }

    companion object {
        private const val RC_SIGN_IN = 120
    }

}