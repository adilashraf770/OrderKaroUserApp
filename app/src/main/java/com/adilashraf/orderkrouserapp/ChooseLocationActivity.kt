package com.adilashraf.orderkrouserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
 import com.adilashraf.orderkrouserapp.databinding.ActivityChooseLocationBinding

class ChooseLocationActivity : AppCompatActivity() {

      private val binding: ActivityChooseLocationBinding by lazy {
        ActivityChooseLocationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val locationList = arrayOf( "China", "India", "Japan", "South Korea", "Indonesia", "Pakistan", "Bangladesh", "Vietnam", "Philippines", "Thailand")
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, locationList)
        binding.listOfLocations.setAdapter(adapter)

        binding.btnNext.setOnClickListener{
            val  i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
     }
}