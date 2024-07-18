package com.adilashraf.orderkrouserapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
 import com.adilashraf.orderkrouserapp.databinding.FragmentProfileBinding
import com.adilashraf.orderkrouserapp.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var address: String
    private lateinit var phone: String
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // initialing Auth
        auth = Firebase.auth
        // initialing Database
        databaseRef = Firebase.database.reference

        binding.savaInfo.setOnClickListener {
            saveData()
        }
        getData()

        binding.apply {
            var isEnabled = false
            editName.isEnabled = isEnabled
            editAddress.isEnabled = isEnabled
            editEmail.isEnabled = isEnabled
            editPhone.isEnabled = isEnabled
            editProfile.setOnClickListener {
                isEnabled = !isEnabled
                editName.isEnabled = isEnabled
                editAddress.isEnabled = isEnabled
                editEmail.isEnabled = isEnabled
                editPhone.isEnabled = isEnabled

                if (isEnabled) {
                    editName.requestFocus()
                }

            }
        }

    }

    private fun saveData() {
        name = binding.editName.text.toString().trim()
        email = binding.editEmail.text.toString().trim()
        address = binding.editAddress.text.toString().trim()
        phone = binding.editPhone.text.toString().trim()

        if (name.isBlank() || email.isBlank() || address.isBlank() || phone.isBlank()) {
            Toast.makeText(requireContext(), "Fill all Details", Toast.LENGTH_SHORT).show()
        } else {
            updateProfile(name, email, address, phone)
        }


    }

    private fun updateProfile(name: String, email: String, address: String, phone: String) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val itemRef = databaseRef.child("user").child(uid)
            val userData = hashMapOf(
                "name" to name,
                "email" to email,
                "address" to address,
                "phone" to phone
            )
            itemRef.updateChildren(userData as Map<String, Any>)
            Toast.makeText(requireContext(), "Profile is Updated", Toast.LENGTH_SHORT).show()


        }
    }

    private fun getData() {
        val uid = auth.currentUser?.uid.toString()
        val itemRef = databaseRef.child("user").child(uid)

        itemRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(UserModel::class.java)
                    binding.apply {
                        if (user != null) {
                            editName.setText(user.name.toString())
                            editEmail.setText(user.email.toString())
                            editAddress.setText(user.address.toString())
                            editPhone.setText(user.phone.toString())
                        }

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

}