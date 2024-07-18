package com.adilashraf.orderkrouserapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.adilashraf.orderkrouserapp.R
import com.adilashraf.orderkrouserapp.adapter.PopularAdapter
import com.adilashraf.orderkrouserapp.databinding.FragmentHomeBinding
import com.adilashraf.orderkrouserapp.model.MenuItems
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class HomeFragment : Fragment() {

    private var popularItems = ArrayList<MenuItems>()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var databaseRef: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.appCompatButton.setOnClickListener {
            val bottomSheetDialogFragment = MainMenuBottomSheet()
            bottomSheetDialogFragment.show(parentFragmentManager, "Test")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseRef = Firebase.database.reference
        //slider COde
        val imageList = ArrayList<SlideModel>() // Create image list
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))
        binding.imageSlider.setImageList(imageList)
        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)

        getData()


    }


    private fun getData() {
        val itemRef = databaseRef.child("menu")
        itemRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val item = data.getValue(MenuItems::class.java)
                        popularItems.add(item!!)
                    }
                    shuffleItem()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }

    private fun shuffleItem() {
        val index = popularItems.indices.toList().shuffled()
        val shuffledItems = index.take(6).map { popularItems[it] }

        setAdapter(shuffledItems)
    }

    private fun setAdapter(shuffledItems: List<MenuItems>) {
        val adapter = PopularAdapter(shuffledItems, requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(false)
    }




}