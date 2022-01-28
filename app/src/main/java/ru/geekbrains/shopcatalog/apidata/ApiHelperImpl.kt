package ru.geekbrains.shopcatalog.apidata

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getListProducts(id: String): ProductsListDTO {
        return apiService.getListProducts(id)
    }

    override suspend fun getListCategory(): CategoriesListDTO {
        return apiService.getListCategory()
    }

    override suspend fun getListVariants(id: String): VariantsListDTO {
        return apiService.getListVariants(id)
    }

    override suspend fun getVariantIsStock(id: String): ListVariantIsStockDTO {
        return apiService.getVariantIsStock(id)
    }

}