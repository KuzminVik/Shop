package ru.geekbrains.shopcatalog.apidata

interface ApiHelper {
    suspend fun getListProducts(id: String): ProductsListDTO
    suspend fun getListCategory(): CategoriesListDTO
    suspend fun getListVariants(id: String): VariantsListDTO
    suspend fun getVariantIsStock(id: String): ListVariantIsStockDTO
}