@file:Suppress("DEPRECATION")

package com.adilashraf.orderkrouserapp
import android.content.Intent
 import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adilashraf.orderkrouserapp.databinding.ActivitySignUpBinding
import com.adilashraf.orderkrouserapp.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database


@Suppress("DEPRECATION")
class SignUpActivity : AppCompatActivity() {
    private lateinit var userName: String
    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient
    private val REQ_CODE = 10


    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
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

        binding.alreadyHaveAnAccount.setOnClickListener {
            val i = Intent(this, SignInActivity::class.java)
            startActivity(i)
        }
        // Sign up
        binding.btnSignUp.setOnClickListener {
            userName = binding.editName.text.toString().trim()
            userEmail = binding.editEmail.text.toString().trim()
            userPassword = binding.editPassword.text.toString().trim()

            if (userName.isBlank() || isValidateEmail(userEmail)  || isValidatePassword(userPassword)) {
                Toast.makeText(this, "Fill All details", Toast.LENGTH_SHORT).show()
            } else {
                createUser(userEmail, userPassword)
            }

        }

        binding.btnGoogle.setOnClickListener {
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, REQ_CODE)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            if (task.isSuccessful) {
                val account: GoogleSignInAccount = task.result
                val personName = account.displayName
                val personEmail = account.email
                val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credentials).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "User is Created Successfully", Toast.LENGTH_SHORT)
                            .show()
                        val user = UserModel(personName, personEmail, null)
                        try {
                            val uid = auth.currentUser!!.uid
                            databaseRef.child("user").child(uid).setValue(user)
                        } catch (_: Error) {}
                        val i = Intent(this, MainActivity::class.java)
                        startActivity(i)
                    } else {
                        Toast.makeText(this, "Failed User Creations", Toast.LENGTH_SHORT).show()
                    }
                }
            }

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

    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            Toast.makeText(this, "User is Created Successfully", Toast.LENGTH_SHORT).show()
            saveData()
             updateUI()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed User Creations", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveData() {
        userName = binding.editName.text.toString().trim()
        userEmail = binding.editEmail.text.toString().trim()
        userPassword = binding.editPassword.text.toString().trim()
        val user = UserModel(userName, userEmail, userPassword)
        val uid = auth.currentUser!!.uid
        try {
            databaseRef.child("user").child(uid).setValue(user)
        } catch (_: Error) {
        }
    }

    private fun updateUI() {
        val i = Intent(this, SignInActivity::class.java)
        startActivity(i)
    }

        //Email Validation
    private fun isValidateEmail(email: String): Boolean {

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(Regex(emailPattern))
    }
     //Password Validation
    private fun isValidatePassword(password: String): Boolean {
        // Password criteria: at least 8 characters, including upper, lower, digit, special
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*()_+=-]).{8,}\$"
        return password.matches(Regex(passwordPattern))
    }
}