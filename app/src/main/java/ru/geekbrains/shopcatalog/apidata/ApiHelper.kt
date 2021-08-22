package ru.geekbrains.shopcatalog.apidata

import retrofit2.Callback

interface ApiHelper {
    suspend fun getListProducts(id: String): ProductListDTO
    suspend fun getListCategory(): CategoryListDTO
    suspend fun getListVariants(id: String): VariantListDTO
}