package com.adilashraf.orderkrouserapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.adilashraf.orderkrouserapp.adapter.HistoryAdapter
import com.adilashraf.orderkrouserapp.databinding.FragmentHistoryBinding
import com.adilashraf.orderkrouserapp.model.OrderPlaceDetailsModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class HistoryFragment : Fragment() {

    lateinit var binding: FragmentHistoryBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private var listOfFoodImages: MutableList<String> = mutableListOf()
    private var listOfFoodNames: MutableList<String> = mutableListOf()
    private var listOfFoodPrices: MutableList<String> = mutableListOf()
    private var listOfItems = ArrayList<OrderPlaceDetailsModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // initialing Auth
        auth = Firebase.auth
        // initialing Database
        databaseRef = Firebase.database.reference

        uid = auth.currentUser?.uid ?: ""

        getData()

    }


    private fun getData() {
        val historyRef =
            databaseRef.child("user").child(uid).child("order_history").orderByChild("time")
        historyRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val item = data.getValue(OrderPlaceDetailsModel::class.java)
                        item?.let { listOfFoodNames.addAll(it.foodNames) }
                        item?.let { listOfFoodImages.addAll(it.foodImages) }
                        item?.let { listOfFoodPrices.addAll(it.foodPrices) }
                        item?.let { listOfItems.add(it) }

                    }

                     setAdapter(listOfFoodNames, listOfFoodImages, listOfFoodPrices)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }



    private fun setAdapter(
        listOfFoodNames: MutableList<String>,
        listOfFoodImages: MutableList<String>,
        listOfFoodPrices: MutableList<String>,

        ) {
        val adapter =
            HistoryAdapter(listOfFoodNames, listOfFoodImages, listOfFoodPrices, requireContext())
        binding.recyclerView2.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView2.adapter = adapter
        binding.recyclerView2.setHasFixedSize(false)
    }

}