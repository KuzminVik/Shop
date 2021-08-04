package ru.geekbrains.shopcatalog.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
        val id_product: String = "id default",
        val name: String = "name default",
        val description: String = "description default",
        val categoryId: String = "productFolder default",
        val imgLoad: String = "images default",
        val imgMiniature: String = "images default",
        val imgTiny: String = "images default",
        val supplier: String = "supplier default",
        val prise : String = "salePrices default",
        val stock: String = "stock default"
): Parcelable
