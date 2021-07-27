package ru.geekbrains.shopcatalog.room

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity
data class ViewedProductsEntity2222(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val id_product: String,
    val name: String,
    val description: String,
    val prise: String
)

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val id_category: String,
    val name: String
)

@Entity
data class Products(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val id_product: String,
    val name: String,
    val description: String,
    val prise: String
)