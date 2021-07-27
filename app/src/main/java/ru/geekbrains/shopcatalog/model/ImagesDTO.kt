package ru.geekbrains.shopcatalog.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImagesFromProductDTO(
    val rows: ArrayList<ImageDTO>?
): Parcelable

@Parcelize
data class ImageDTO(
    val meta: MetaImage?,
    val miniature: Miniature?,
    val tiny: Tiny?
): Parcelable

@Parcelize
data class MetaImage(
    val downloadHref: String?
) : Parcelable

@Parcelize
data class Miniature(
    val href: String?
): Parcelable

@Parcelize
data class Tiny(
    val href: String?
): Parcelable