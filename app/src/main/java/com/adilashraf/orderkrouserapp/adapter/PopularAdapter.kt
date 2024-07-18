package com.adilashraf.orderkrouserapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adilashraf.orderkrouserapp.FoodDetailsActivity
import com.adilashraf.orderkrouserapp.databinding.PopularItemsLayoutBinding
import com.adilashraf.orderkrouserapp.model.MenuItems
import com.bumptech.glide.Glide

class PopularAdapter(
    private val popularList: List<MenuItems>,
    private val context: Context,
) : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val pv =
            PopularItemsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularViewHolder(pv)
    }

    override fun getItemCount(): Int {
        return popularList.size
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val pos = popularList[position]

        holder.bind(pos, context)
        holder.binding.popularCard.setOnClickListener {

            val i = Intent(context, FoodDetailsActivity::class.java).apply {
                putExtra("itemName", pos.itemName)
                putExtra("itemPrice", pos.itemPrice)
                putExtra("itemIngredients", pos.itemIngredients)
                putExtra("itemImage", pos.itemImage)
                putExtra("itemDescription", pos.itemDescription)
            }
            context.startActivity(i)
        }
    }


    class PopularViewHolder(val binding: PopularItemsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: MenuItems, context: Context) {
            val imgURL = pos.itemImage.toString()
            val uri = Uri.parse(imgURL)
            binding.apply {
                foodName.text = pos.itemName
                foodPrice.text = pos.itemPrice

                Glide.with(context).load(uri).into(popularImg)


            }

        }


    }
}