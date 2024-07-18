package com.adilashraf.orderkrouserapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.adilashraf.orderkrouserapp.adapter.SearchAdapter
import com.adilashraf.orderkrouserapp.databinding.FragmentSearchBinding
import com.adilashraf.orderkrouserapp.model.MenuItems
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private var searchItemsList = mutableListOf<MenuItems>()
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // initialing Auth
        auth = Firebase.auth
        // initialing Auth
        databaseRef = Firebase.database.reference

        uid = auth.currentUser?.uid ?: ""

        getData()

    }


    private fun getData() {
        databaseRef.child("menu")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (data in snapshot.children) {
                            val item = data.getValue(MenuItems::class.java)
                            searchItemsList.add(item!!)
                        }
                        setAdapter(searchItemsList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {}

            })
    }


    private fun setAdapter(searchItemsList: MutableList<MenuItems>) {
        val adapter = SearchAdapter(searchItemsList, requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        searchMenu(adapter)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun searchMenu(adapter: SearchAdapter) {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                adapter.filter(query!!)
                return true
            }

        })


    }


}