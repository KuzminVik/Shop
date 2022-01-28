package ru.geekbrains.shopcatalog.apidata

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ProductAPI {

    @GET("entity/assortment")
    suspend fun getListProducts(
        @Header("Authorization")  token: String,
        @Query("filter") productFolderId: String,
        @Query("groupBy") groupBy: String,
        @Query("expand") expand: String,
        @Query("limit") limit: Int
    ): ProductsListDTO

    @GET("entity/productfolder")
    suspend fun getListCategories(
        @Header("Authorization")  token: String
    ): CategoriesListDTO

    @GET("entity/variant")
    suspend fun getListVariantForProduct(
    @Header("Authorization")  token: String,
    @Query("filter") productId: String
    ) : VariantsListDTO

    @GET("report/stock/all")
    suspend fun getVariantIsStock(
        @Header("Authorization")  token: String,
        @Query("filter") variantId: String
    ): ListVariantIsStockDTO

}