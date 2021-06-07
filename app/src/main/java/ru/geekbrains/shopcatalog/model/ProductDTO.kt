package ru.geekbrains.shopcatalog.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductDTO(
    val id : String?,
//    val shared : Boolean?,
//    val updated : String?,
    val name : String?,
    val description : String?,
    val salePrices : ArrayList<Price>?,
//    val images: Meta?
//    val variants : ArrayList<Variant>
    ) : Parcelable


@Parcelize
data class Price(
    val value : Double?
) : Parcelable
