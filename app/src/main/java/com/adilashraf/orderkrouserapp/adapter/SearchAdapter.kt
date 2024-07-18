package com.adilashraf.orderkrouserapp.adapter


import android.annotation.SuppressLint
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

class SearchAdapter(private val searchList: MutableList<MenuItems>,   val context: Context) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var filterSearchItems = searchList.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SearchViewHolder {
        return SearchViewHolder(
            PopularItemsLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val pos = filterSearchItems[position]

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

    override fun getItemCount(): Int = filterSearchItems.size


    class SearchViewHolder(val binding: PopularItemsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pos: MenuItems, context: Context) {
            val imgUrl = pos.itemImage.toString()
            val uri = Uri.parse(imgUrl)

            binding.apply {
                foodName.text = pos.itemName
                foodPrice.text = pos.itemPrice
                Glide.with(context).load(uri).into(popularImg)
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filterSearchItems = if (query.isEmpty()) {
            searchList.toMutableList()
        } else {
            searchList.filter {
                it.itemName!!.contains(query, ignoreCase = true) ||
                        it.itemPrice!!.contains(query, ignoreCase = true)
            }.toMutableList()

        }

        notifyDataSetChanged()
    }
}