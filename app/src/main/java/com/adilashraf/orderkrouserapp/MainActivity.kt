package com.adilashraf.orderkrouserapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.adilashraf.orderkrouserapp.databinding.ActivityMainBinding
import com.adilashraf.orderkrouserapp.fragments.NotificationBottomSheetFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController: NavController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController)

        binding.imageBell.setOnClickListener {
            val bottomSheet = NotificationBottomSheetFragment()
            bottomSheet.show(supportFragmentManager, "Text")
        }

        binding.imageLogout.setOnClickListener {
            Firebase.auth.signOut()
            val i = Intent(this@MainActivity, SignInActivity::class.java )
            startActivity(i)
            finish()
            Toast.makeText(this, "User is SignOut Successfully", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onStart() {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            val i = Intent(this, SignInActivity::class.java)
            startActivity(i)
            finish()
        }
    }


    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}