package ru.geekbrains.shopcatalog.room

import androidx.room.*

@Entity
data class CategoryEntity(
    @PrimaryKey val id_category: String,
    val name: String
)

@Entity
data class ProductEntity(
    @PrimaryKey val id_product: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "category_id") val categoryId: String,  //сылка на родительскую категорию
    @ColumnInfo(name = "img_load") val imgLoad: String,
    @ColumnInfo(name = "img_miniature") val imgMiniature: String,
    @ColumnInfo(name = "img_tiny") val imgTiny: String,
    @ColumnInfo(name = "prise") val prise: String
)