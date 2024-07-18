package com.adilashraf.orderkrouserapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adilashraf.orderkrouserapp.FoodDetailsActivity
import com.adilashraf.orderkrouserapp.databinding.MainMenuItemsLayoutBinding
import com.adilashraf.orderkrouserapp.model.MenuItems
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference

class MainMeniAdapter(
    private val MenuList: List<MenuItems>,
    private val context: Context,
 ) :
    RecyclerView.Adapter<MainMeniAdapter.MainMenuViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MainMeniAdapter.MainMenuViewHolder {
        return MainMenuViewHolder(
            MainMenuItemsLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainMeniAdapter.MainMenuViewHolder, position: Int) {
        val pos = MenuList[position]
        val image = pos.itemImage.toString()
        val uri = Uri.parse(image)
        holder.name.text = pos.itemName
        holder.price.text = pos.itemPrice
        Glide.with(context).load(uri).into(holder.image)


        holder.binding.menuCard.setOnClickListener {
            val i = Intent(context, FoodDetailsActivity::class.java).apply {
                putExtra("itemName", pos.itemName)
                putExtra("itemPrice", pos.itemPrice)
                putExtra("itemImage", pos.itemImage)
                putExtra("itemDescription", pos.itemDescription)
                putExtra("itemIngredients", pos.itemIngredients)
            }
            context.startActivity(i)

        }
    }

    override fun getItemCount(): Int = MenuList.size

    inner class MainMenuViewHolder(val binding: MainMenuItemsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val name = binding.foodName
        val price = binding.foodPrice
        val image = binding.popularImg
//        fun bind(pos: MainMenuItems, context: Context) {
//            binding.apply {
//               foodName.text = pos.itemName
//               foodPrice.text = pos.itemPrice
//                val imgUrl = pos.itemImage.toString()
//                val uri = Uri.parse(imgUrl)
//                Glide.with(context).load(uri).into(popularImg)
//
//            }
//        }


    }

}