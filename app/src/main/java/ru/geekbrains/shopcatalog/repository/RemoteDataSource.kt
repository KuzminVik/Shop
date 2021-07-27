package ru.geekbrains.shopcatalog.repository

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.geekbrains.shopcatalog.BuildConfig
import ru.geekbrains.shopcatalog.model.ImagesFromProductDTO
import ru.geekbrains.shopcatalog.model.ProductDTO
import ru.geekbrains.shopcatalog.model.ProductListDTO
import java.io.IOException

class RemoteDataSource {

    private val productAPI = Retrofit.Builder()
        .baseUrl("https://online.moysklad.ru/api/remap/1.2/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd").create()
            )
        )
        .client(createOkHttpClient(ProductApiInterceptor()))
        .build().create(ProductAPI::class.java)

    fun getProductDetails(id: String, callback: Callback<ProductDTO>) {
        productAPI.getProduct("Bearer ${BuildConfig.API_AUTHORIZATION}", id).enqueue(callback)
    }

    fun getImagesDetails(id: String, callback: Callback<ImagesFromProductDTO>) {
        productAPI.getImagesFromProduct("Bearer ${BuildConfig.API_AUTHORIZATION}", id)
            .enqueue(callback)
    }

    fun getListProductsInTheCategory(productFolderId: String, callback: Callback<ProductListDTO>) {
        productAPI.getProductsInTheCategory(
            "Bearer ${BuildConfig.API_AUTHORIZATION}",
            productFolderId,
            "positiveOnly",
            "product",
            "supplier,productFolder,images",
            100
        ).enqueue(callback)
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    inner class ProductApiInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

            return chain.proceed(chain.request())
        }
    }

}