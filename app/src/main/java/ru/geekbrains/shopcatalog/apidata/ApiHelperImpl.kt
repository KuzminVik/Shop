package ru.geekbrains.shopcatalog.apidata

import retrofit2.Callback

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override fun getListProductsInTheCategory(id: String, callback: Callback<ProductListDTO>) {
        apiService.getListProductsInTheCategory(id, callback)
    }

    override suspend fun getListProducts(id: String): ProductListDTO {
        return apiService.getListProducts(id)
    }

    override suspend fun getListCategory(): CategoryListDTO {
        return apiService.getListCategory()
    }

    override fun getProductFromServer(id: String, callback: Callback<ProductDTO>) {
        apiService.getProductDetails(id, callback)
    }

    override fun getImagesFromServer(id: String, callback: Callback<ImagesFromProductDTO>) {
        apiService.getImagesDetails(id, callback)
    }

}