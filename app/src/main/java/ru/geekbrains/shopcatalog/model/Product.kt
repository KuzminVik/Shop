package ru.geekbrains.shopcatalog.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Product(
        val id: String = " ",
        val name: String = "default",
        val description: String = "default",
        val salePrices : String = "default",
//        val images: String = "default",
//        val sizes: List<String> = listOf("42", "44", "46", "48", "50", "52")
): Parcelable
