package ru.geekbrains.shopcatalog.apidata

import retrofit2.Callback

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getListProducts(id: String): ProductListDTO {
        return apiService.getListProducts(id)
    }

    override suspend fun getListCategory(): CategoryListDTO {
        return apiService.getListCategory()
    }

    override suspend fun getListVariants(id: String): VariantListDTO {
        return apiService.getListVariants(id)
    }

}