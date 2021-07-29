package ru.geekbrains.shopcatalog.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductListDTO(
    val rows: ArrayList<ProductDTO>?
): Parcelable

@Parcelize
data class ProductDTO(
    val id : String?,
    val updated: String?,
    val name: String?,
    val description: String?,
    val productFolder: ProductFolderDTO?,
    val images: ImagesFromProductDTO?,
    val salePrices: ArrayList<PriceDTO>?,
    val supplier: SupplierDTO?,
    val stock: Double?
    ): Parcelable

@Parcelize
data class ProductFolderDTO(
    val id : String?,
    val name : String?,
    val pathName : String?
): Parcelable

@Parcelize
data class PriceDTO(
    val value : Double?
): Parcelable

@Parcelize
data class SupplierDTO(
    val name: String?
): Parcelable

@Parcelize
data class CategoryListDTO(
    val rows: ArrayList<ProductFolderDTO>?
): Parcelable