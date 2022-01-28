package ru.geekbrains.shopcatalog.apidata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VariantsListDTO(
        val rows: List<VariantDTO>
): Parcelable

@Parcelize
data class VariantDTO(
        val id: String = "",
        val externalCode: String = "",
        val characteristics: List<Characteristic>
) : Parcelable

@Parcelize
data class Characteristic(
        val value : String = ""
) : Parcelable

@Parcelize
data class ListVariantIsStockDTO(
        val rows: List<VariantIsStockDTO>
): Parcelable

@Parcelize
data class VariantIsStockDTO(
        val stock: String = "",
        val externalCode: String = ""
):Parcelable