package com.adilashraf.orderkrouserapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adilashraf.orderkrouserapp.FoodDetailsActivity
import com.adilashraf.orderkrouserapp.databinding.CartItemsLayoutBinding
import com.adilashraf.orderkrouserapp.model.CartItems
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class CartAdapter(private val CartList: MutableList<CartItems>, val context:Context) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var databaseRef: DatabaseReference

    init {
        val uid = Firebase.auth.currentUser?.uid.toString()
        databaseRef = Firebase.database.reference.child("user").child(uid).child("cart_items")
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.CartViewHolder {
        return CartViewHolder(
            CartItemsLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {
        val pos = CartList[position]

//        holder.binding.cartCard.setOnClickListener {
//            val i = Intent(context, FoodDetailsActivity::class.java).apply {
//                putExtra("itemName", pos.itemName)
//                putExtra("itemPrice", pos.itemPrice)
//                putExtra("itemIngredients", pos.itemIngredients)
//                putExtra("itemImage", pos.itemImage)
//                putExtra("itemDescription", pos.itemDescription)
//            }
//            context.startActivity(i)
//        }

        holder.deleteBtn.setOnClickListener {
            if (position > 0 && position < CartList.size) {
                itemRemove(position) { key ->
                    databaseRef.child(key).removeValue().addOnCompleteListener {
                        CartList.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, CartList.size)
                    }
                }
            }
        }
        holder.bind(pos, context)
    }

    private fun itemRemove(
        position: Int,
        onComplete: (String) -> Unit,
    ) {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var uniqueKey: String? = null
                try {
                    snapshot.children.forEachIndexed { index, dataSnapshot ->
                        if (index == position) {
                            uniqueKey = dataSnapshot.key.toString()
                            return@forEachIndexed
                        }
                        uniqueKey?.let { onComplete(it) }
                    }
                } catch (_: Error) {
                }

            }

            override fun onCancelled(error: DatabaseError) {}

        })

    }

    override fun getItemCount(): Int = CartList.size

    inner class CartViewHolder(val binding: CartItemsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val deleteBtn = binding.imgBtnDelete
        fun bind(pos: CartItems, context: Context) {
            val imgURL = pos.itemImage.toString()
            val uri = Uri.parse(imgURL)
             binding.apply {
                foodName.text = pos.itemName
                foodPrice.text = pos.itemPrice.toString()
                Glide.with(context).load(uri).into(cartImg)

            }
        }
    }


}