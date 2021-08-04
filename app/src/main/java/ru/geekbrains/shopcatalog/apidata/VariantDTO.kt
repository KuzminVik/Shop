package ru.geekbrains.shopcatalog.apidata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VariantDTO(
        val id : String = "",
        val updated : String = "",
        val name : String = "",
        val characteristics : List<Size>,
        val productInVariant : ProductInVariant,

        ) : Parcelable

@Parcelize
data class ProductInVariant(
        val meta : Meta
) : Parcelable

@Parcelize
data class Meta(
       val href : String = "",
) : Parcelable

@Parcelize
data class Size(
        val value : String = ""
) : Parcelable