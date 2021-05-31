package ru.geekbrains.shopcatalog.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id : String = "7f25bcde-81c3-11e3-240b-002590a28eca",
    val shared : Boolean?,
    val updated : String?,
    val name : String?,
    val description : String?,
    val salePrices : ArrayList<Price>,
//    val variants : ArrayList<Variant>
    ) : Parcelable


@Parcelize
data class Price(
    val value : Double?
) : Parcelable
