package com.adilashraf.orderkrouserapp.model


data class CartItems(
    var itemName: String? = null,
    var itemPrice: Int? = null,
    var itemIngredients: String? = null,
    var itemImage: String? = null,
    var itemDescription: String? = null,
    var quantity: Int = 1,

)