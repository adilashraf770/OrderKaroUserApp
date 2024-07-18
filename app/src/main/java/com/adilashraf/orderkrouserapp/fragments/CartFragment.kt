package com.adilashraf.orderkrouserapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.adilashraf.orderkrouserapp.OrderPlaceActivity
import com.adilashraf.orderkrouserapp.adapter.CartAdapter
import com.adilashraf.orderkrouserapp.databinding.FragmentCartBinding
import com.adilashraf.orderkrouserapp.model.CartItems
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var databaseRef: DatabaseReference
    private   var prices: Int = 0
    private var cartItemsList = mutableListOf<CartItems>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseRef = Firebase.database.reference
        getData()



    }

    private fun getData() {
        val uid = Firebase.auth.currentUser?.uid.toString()
        val itemRef = databaseRef.child("user").child(uid).child("cart_items")
        itemRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (data in snapshot.children){
                        val item = data.getValue(CartItems::class.java)
                        prices += item!!.itemPrice!!.toInt()
                        cartItemsList.add(item)
                    }
                    gotoPlaceOrderActivity(prices)
                    cartItemsList.reverse()
                    setAdapter(cartItemsList)
                }
             }

            override fun onCancelled(error: DatabaseError) {
             }

        })
    }

    private fun gotoPlaceOrderActivity(prices: Int) {
        binding.proceed.setOnClickListener {
            val i = Intent(requireContext(), OrderPlaceActivity::class.java)
            i.putExtra("price", prices.toString())
            startActivity(i)
        }
    }

    private fun setAdapter(cartItemsList: MutableList<CartItems>) {
        val adapter = CartAdapter(cartItemsList, requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(false)
     }

}