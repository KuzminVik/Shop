package ru.geekbrains.shopcatalog.apidata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VariantListDTO(
        val rows: List<VariantDTO>
): Parcelable

@Parcelize
data class VariantDTO(
        val id: String = "",
        val characteristics: List<Characteristic>
) : Parcelable

@Parcelize
data class Characteristic(
        val value : String = ""
) : Parcelable