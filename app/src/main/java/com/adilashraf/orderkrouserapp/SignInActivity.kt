@file:Suppress("DEPRECATION")

package com.adilashraf.orderkrouserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.adilashraf.orderkrouserapp.databinding.ActivitySignInBinding
import com.adilashraf.orderkrouserapp.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.SignInAccount
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database


@Suppress("DEPRECATION")
class SignInActivity : AppCompatActivity() {

    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient
    private val REQ_CODE = 10


    private val binding: ActivitySignInBinding by lazy {
        ActivitySignInBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // Initialing Auth
        auth = Firebase.auth
        // Initialing Database
        databaseRef = Firebase.database.reference

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        binding.dontHaveAccount.setOnClickListener {
            val i = Intent(this, SignUpActivity::class.java)
            startActivity(i)
        }

        binding.btnSignIn.setOnClickListener {
            userEmail = binding.editEmail.text.toString().trim()
            userPassword = binding.editPassword.text.toString().trim()

            if (userEmail.isBlank() || userPassword.isBlank()) {
                Toast.makeText(this, "Fill All details", Toast.LENGTH_SHORT).show()
            } else {
                signIn(userEmail, userPassword)
            }
        }

        binding.btnGoogle.setOnClickListener {
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, REQ_CODE)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            Toast.makeText(this, "User is Signed in Successfully", Toast.LENGTH_SHORT).show()
            updateUI()
        }.addOnFailureListener {
            Toast.makeText(this, " User is Not Avaliable", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            if (task.isSuccessful) {
                val account: GoogleSignInAccount = task.result
                val name = account.displayName
                val email = account.email
                val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credentials).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "User is Signed in Successfully", Toast.LENGTH_SHORT)
                            .show()
                        val user = UserModel(name, email, null)
                        try {
                            val uid = auth.currentUser!!.uid
                            databaseRef.child("user").child(uid).setValue(user)
                        } catch (_: Error) {
                        }
                        updateUI()
                    } else {
                        Toast.makeText(this, " User is Not Avaliable", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}