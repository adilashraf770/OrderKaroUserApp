package com.adilashraf.orderkrouserapp.adapter


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adilashraf.orderkrouserapp.MainActivity
import com.adilashraf.orderkrouserapp.databinding.HistoryItemsLayoutBinding
import com.bumptech.glide.Glide

class HistoryAdapter(
    val foodNames: MutableList<String>,
    val foodImages: MutableList<String>,
    val foodPrices: MutableList<String>,
    val context: Context,
) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            HistoryItemsLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = foodNames.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {

        val uriString = foodImages[position]
        val uri = Uri.parse(uriString)
        holder.foodName.text = foodNames[position]
        holder.foodPrice.text = foodPrices[position]
        Glide.with(context).load(uri).into(holder.foodImage)


        holder.buy.setOnClickListener {
            val i = Intent(context, MainActivity::class.java)
            context.startActivity(i)
        }
    }

    inner class HistoryViewHolder(val binding: HistoryItemsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val buy = binding.buyAgain
        val foodName = binding.fName
        val foodImage = binding.fImage
        val foodPrice = binding.fPrice
    }
}