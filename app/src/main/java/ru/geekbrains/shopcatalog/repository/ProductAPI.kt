package ru.geekbrains.shopcatalog.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import ru.geekbrains.shopcatalog.model.ImagesFromProductDTO
import ru.geekbrains.shopcatalog.model.ProductDTO

interface ProductAPI {

    @GET("entity/product/{id}")
    fun getProduct(
        @Header("Authorization")  token: String,
        @Path("id") id: String
    ): Call<ProductDTO>

    @GET("entity/product/{id}/images")
    fun getImagesFromProduct(
        @Header("Authorization")  token: String,
        @Path("id") id: String
    ): Call<ImagesFromProductDTO>

//    @GET("entity/variant/{id}")
//    fun getListVariantForProduct(
//    @Header("Authorization")  token: String,
//    @Path("id") id: String
//    ) : Call<Variant>
}