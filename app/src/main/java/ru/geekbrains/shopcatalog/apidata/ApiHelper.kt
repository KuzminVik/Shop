package ru.geekbrains.shopcatalog.apidata

import retrofit2.Callback

interface ApiHelper {
    fun getListProductsInTheCategory(id: String, callback: Callback<ProductListDTO>)
    suspend fun getListCategory(): CategoryListDTO
    fun getProductFromServer(id: String, callback: Callback<ProductDTO>)
    fun getImagesFromServer(id: String, callback: Callback<ImagesFromProductDTO>)
}