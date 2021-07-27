package ru.geekbrains.shopcatalog.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ViewedProductsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val id_product: String,
    val name: String,
    val description: String,
    val prise: String
)