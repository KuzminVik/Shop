package ru.geekbrains.shopcatalog.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    val original: String = " ",
    val miniature: String = " ",
    val tiny: String = " "
): Parcelable, DeteilsEntity
