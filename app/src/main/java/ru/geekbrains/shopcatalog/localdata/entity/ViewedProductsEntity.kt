package ru.geekbrains.shopcatalog.localdata.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class ViewedProductsEntity(
    @PrimaryKey()
    val id_product: String
): Parcelable