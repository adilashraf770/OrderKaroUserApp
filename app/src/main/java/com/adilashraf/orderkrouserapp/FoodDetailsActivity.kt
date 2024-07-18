package com.adilashraf.orderkrouserapp

 import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.adilashraf.orderkrouserapp.databinding.ActivityFoodDetailsBinding
 import com.adilashraf.orderkrouserapp.model.CartItems
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
 import com.google.firebase.auth.FirebaseAuth
 import com.google.firebase.auth.auth
 import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class FoodDetailsActivity : AppCompatActivity() {
    private lateinit var databaseRef: DatabaseReference
    val binding: ActivityFoodDetailsBinding by lazy {
        ActivityFoodDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        databaseRef = Firebase.database.reference
        val name = intent.getStringExtra("itemName")
        val price = intent.getStringExtra("itemPrice")
        val image = intent.getStringExtra("itemImage")
        val description = intent.getStringExtra("itemDescription")
        val ingredients = intent.getStringExtra("itemIngredients")

        binding.apply {
            foodName.text = name
            foodDescription.text = description
            foodIngredients.text = ingredients
            Glide.with(this@FoodDetailsActivity).load(Uri.parse(image.toString())).into(foodImage)
        }

        binding.addToCart.setOnClickListener {
            val cartItem = CartItems(name, price?.toInt(), ingredients, image, description)
            try {
                val uid = Firebase.auth.currentUser?.uid.toString()
                val key = databaseRef.push().key.toString()
                databaseRef.child("user").child(uid).child("cart_items").child(key).setValue(cartItem)
                Toast.makeText(this, "Item Added to Cart", Toast.LENGTH_SHORT).show()

            }catch (_: Error){}

        }

        binding.backImage.setOnClickListener {
            finish()
        }


    }
}