package ru.geekbrains.shopcatalog.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
        val id: String = "id default",
        val updated: String = "updated default",
        val name: String = "name default",
        val description: String = "description default",
        val productFolder: String = "productFolder default",
        val images: String = "images default",
        val salePrices : String = "salePrices default",
        val supplier: String = "supplier default",
        val stock: String = "stock default"
): Parcelable
