package com.adilashraf.orderkrouserapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.adilashraf.orderkrouserapp.adapter.MainMeniAdapter
import com.adilashraf.orderkrouserapp.databinding.FragmentMainMenuBottomSheetBinding
import com.adilashraf.orderkrouserapp.model.MenuItems
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class MainMenuBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMainMenuBottomSheetBinding
    private lateinit var databaseRef: DatabaseReference
    private val menuList = ArrayList<MenuItems>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainMenuBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseRef = Firebase.database.reference
        getData()

    }

    /// Getting Data from Firebase Realtime Database
    private fun getData() {
        val itemRef = databaseRef.child("menu")
        itemRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (data in snapshot.children){
                         val item = data.getValue(MenuItems::class.java)
                        menuList.add(item!!)
                    }
                    setAdapter()
                }
            }
            override fun onCancelled(error: DatabaseError) {
             }
        })
     }

    // Setting Data in Adapter
    private fun setAdapter() {
         val adapter = MainMeniAdapter(menuList, requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(false)
        binding.recyclerView.adapter = adapter

    }
}