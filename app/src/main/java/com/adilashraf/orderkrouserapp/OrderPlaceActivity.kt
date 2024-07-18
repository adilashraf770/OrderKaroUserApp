package com.adilashraf.orderkrouserapp

 import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.adilashraf.orderkrouserapp.databinding.ActivityOrderPlaceBinding
import com.adilashraf.orderkrouserapp.model.CartItems
import com.adilashraf.orderkrouserapp.model.OrderPlaceDetailsModel
import com.adilashraf.orderkrouserapp.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class OrderPlaceActivity : AppCompatActivity() {
    val binding: ActivityOrderPlaceBinding by lazy {
        ActivityOrderPlaceBinding.inflate(layoutInflater)
    }

    private lateinit var databaseRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var name: String
    private lateinit var address: String
    private lateinit var email: String
    private lateinit var phone: String
    private lateinit var uid: String
    private lateinit var totalPrice: String
    private var listOfFoodImages: MutableList<String> = mutableListOf()
    private var listOfFoodNames: MutableList<String> = mutableListOf()
    private var listOfFoodIngredients: MutableList<String> = mutableListOf()
    private var listOfFoodDescription: MutableList<String> = mutableListOf()
    private var listOfFoodPrices: MutableList<String> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // initialing auth
        auth = Firebase.auth
        // initialing database
        databaseRef = Firebase.database.reference
        // Uid
        uid = auth.currentUser?.uid.toString()

        totalPrice = intent.getStringExtra("price").toString()
        binding.foodPrice.text = totalPrice
        binding.backImage.setOnClickListener {
            finish()
        }

        getData()

        binding.placeOrder.setOnClickListener {
            val userName = binding.userName.text.toString().trim()
            val userAddress = binding.userAddress.text.toString().trim()
            val userPhone = binding.userPhone.text.toString().trim()
            if (userAddress.isBlank() || userPhone.isBlank() || userName.isBlank()) {
                Toast.makeText(this, "Complete Your Profile", Toast.LENGTH_SHORT).show()
            } else {
                saveOrderDetails()
            }
        }

    }

    private fun saveOrderDetails() {
        val phoneNumber = phone
        val time = System.currentTimeMillis().toString()
        val isOrderAccepted = false
        val isPaymentReceived = false
        val orderKey = databaseRef.push().key.toString()
        val orderRef = databaseRef.child("order_details").child(orderKey)

        val order = OrderPlaceDetailsModel(
            uid,
            orderKey,
            name,
            address,
            email,
            phoneNumber,
            totalPrice,
            time,
            listOfFoodNames,
            listOfFoodImages,
            listOfFoodIngredients,
            listOfFoodDescription,
            listOfFoodPrices,
            isOrderAccepted,
            isPaymentReceived
        )

        orderRef.setValue(order).addOnCompleteListener {
            orderHistory(order, orderKey)
            Toast.makeText(this, "Your Order is placed", Toast.LENGTH_SHORT).show()

            clearCart()
            showDialog("Order Details", "Your Order is Placed")

        }


    }

    private fun clearCart() {
        val cartRef = databaseRef.child("user").child(uid).child("cart_items")
        cartRef.removeValue()
    }

    private fun orderHistory(order: OrderPlaceDetailsModel, orderKey: String) {
        val historyRef = databaseRef.child("user").child(uid).child("order_history").child(orderKey)
        historyRef.setValue(order).addOnSuccessListener {
            orderHistory(order, orderKey)
        }
    }

    private fun getData() {

        val cartItemRef = databaseRef.child("user").child(uid).child("cart_items")
        val itemRef = databaseRef.child("user").child(uid)
        // User Data From Firebase
        itemRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val data = snapshot.getValue(UserModel::class.java)
                    name = data!!.name.toString()
                    address = data.address.toString()
                    phone = data.phone.toString()
                    email = data.email.toString()

                    setValue(name, address, phone)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        // Cart Data From Firebase
        cartItemRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val item = data.getValue(CartItems::class.java)
                        item!!.itemName?.let { listOfFoodNames.add(it) }
                        item.itemImage?.let { listOfFoodImages.add(it) }
                        item.itemPrice?.let { listOfFoodPrices.add(it.toString()) }
                        item.itemDescription?.let { listOfFoodDescription.add(it) }
                        item.itemIngredients?.let { listOfFoodIngredients.add(it) }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })


    }

    private fun setValue(name: String, address: String, phone: String) {
        binding.apply {
            userName.text = name
            userAddress.text = address
            userPhone.text = phone
        }
    }
 
    private fun showDialog(title: String, message: String){

        val builder = AlertDialog.Builder(this, R.style.MyDialogTheme)

         with(builder) {
            setTitle(title)
            setMessage(message)
            setPositiveButton("Ok") { _, _ ->
                val i = Intent(this@OrderPlaceActivity, MainActivity::class.java)
                startActivity(i)
            }
             show()

        }


    }


}