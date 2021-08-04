package ru.geekbrains.shopcatalog.apidata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductListDTO(
    val rows: List<ProductDTO>
): Parcelable

@Parcelize
data class ProductDTO(
    val id : String = "",
    val name: String = "",
    val description: String = "",
    val productFolder: ProductFolderDTO,
    val images: ImagesFromProductDTO,
    val salePrices: List<PriceDTO>,
    val supplier: SupplierDTO?,
    val stock: Double = 0.0
    ): Parcelable

@Parcelize
data class RowsFolderDTO(
    val rows: List<ProductFolderDTO>
): Parcelable

@Parcelize
data class ProductFolderDTO(
    val id : String = "",
    val name : String = " ",
    val pathName : String = " "
): Parcelable

@Parcelize
data class PriceDTO(
    val value : Double = 0.0
): Parcelable

@Parcelize
data class SupplierDTO(
    val name: String = ""
): Parcelable

@Parcelize
data class CategoryListDTO(
    val rows: List<ProductFolderDTO>
): Parcelable