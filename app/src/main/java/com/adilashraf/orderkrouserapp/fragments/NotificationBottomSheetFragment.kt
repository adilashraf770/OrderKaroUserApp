package com.adilashraf.orderkrouserapp.fragments

 import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.adilashraf.orderkrouserapp.R
import com.adilashraf.orderkrouserapp.adapter.NotificationAdapter
import com.adilashraf.orderkrouserapp.databinding.FragmentNotificationBottomSheetBinding
import com.adilashraf.orderkrouserapp.model.NotifyItems
 import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class NotificationBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentNotificationBottomSheetBinding
    private val notifyList = ArrayList<NotifyItems>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNotificationBottomSheetBinding.inflate(inflater, container, false)
         return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notifyList.add(NotifyItems(R.drawable.sademoji, "Your order has been Canceled Successfully"))
        notifyList.add(NotifyItems(R.drawable.truck, "Order has been taken by the driver"))
        notifyList.add(NotifyItems(R.drawable.congrats, "Congrats Your Order Placed" ))

        val adapter = NotificationAdapter(notifyList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(false)



    }
 }