package ru.geekbrains.shopcatalog.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Image(
    val original: String = " ",
    val miniature: String = " ",
    val tiny: String = " "
): Parcelable
