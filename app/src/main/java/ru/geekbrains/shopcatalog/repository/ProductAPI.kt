package ru.geekbrains.shopcatalog.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import ru.geekbrains.shopcatalog.model.*

interface ProductAPI {

    @GET("entity/product/{id}")
    fun getProduct(
        @Header("Authorization")  token: String,
        @Path("id") id: String
    ): Call<ProductDTO>

    @GET("entity/product/{id}/images")
    fun getImagesFromProduct(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<ImagesFromProductDTO>

    @GET("entity/assortment")
    fun getProductsInTheCategory(
        @Header("Authorization")  token: String,
        @Query("filter") productFolderId: String,
        @Query("groupBy") groupBy: String,
        @Query("expand") expand: String,
        @Query("limit") limit: Int
    ): Call<ProductListDTO>

    @GET("entity/productfolder")
    fun getListCategory(
        @Header("Authorization")  token: String
    ): Call<CategoryListDTO>


//    @GET("entity/variant/{id}")
//    fun getListVariantForProduct(
//    @Header("Authorization")  token: String,
//    @Path("id") id: String
//    ) : Call<VariantDTO>
}