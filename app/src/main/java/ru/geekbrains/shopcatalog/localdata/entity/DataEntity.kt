package ru.geekbrains.shopcatalog.localdata.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class CategoryEntity(
    @PrimaryKey val id_category: String,
    val name: String,
    var pathName: String
): Parcelable

@Parcelize
@Entity
data class ProductEntity(
    @PrimaryKey val id_product: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "categoryId") val categoryId: String,
    @ColumnInfo(name = "imgLoad") val imgLoad: String?,
    @ColumnInfo(name = "imgMiniature") val imgMiniature: String?,
    @ColumnInfo(name = "imgTiny") val imgTiny: String?,
    @ColumnInfo(name = "supplier") val supplier: String?,
    @ColumnInfo(name = "prise") val prise: String?,
    @ColumnInfo(name = "stock") val stock: String?
): Parcelable