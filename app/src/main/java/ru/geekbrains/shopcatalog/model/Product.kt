package ru.geekbrains.shopcatalog.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
        val id: String = "default",
        val updated: String = "default",
        val name: String = "default",
        val description: String = "default",
        val productFolder: String = "default",
        val images: String = "default",
        val salePrices : String = "default",
        val supplier: String = "default",
        val stock: String = "default"
): Parcelable
