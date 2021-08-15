package ru.geekbrains.shopcatalog.apidata

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.geekbrains.shopcatalog.BuildConfig
import java.io.IOException

class ApiService {

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
        productAPI.getProduct("Bearer ${BuildConfig.API_AUTHORIZATION}", id)
            .enqueue(callback)
    }

    fun getImagesDetails(id: String, callback: Callback<ImagesFromProductDTO>) {
        productAPI.getImagesFromProduct("Bearer ${BuildConfig.API_AUTHORIZATION}", id)
            .enqueue(callback)
    }

    suspend fun getListCategory(): CategoryListDTO{
        return productAPI.getListCategory("Bearer ${BuildConfig.API_AUTHORIZATION}")
    }

    fun getListProductsInTheCategory(productFolderId: String, callback: Callback<ProductListDTO>) {
        productAPI.getProductsInTheCategory(
            "Bearer ${BuildConfig.API_AUTHORIZATION}",
            "$productFolderId;quantityMode=positiveOnly",
            "product",
            "supplier,productFolder,images",
            100
        ).enqueue(callback)
    }

    suspend fun getListProducts(id: String): ProductListDTO {
        return productAPI.getListProducts(
            "Bearer ${BuildConfig.API_AUTHORIZATION}",
            "$id;quantityMode=positiveOnly",
            "product",
            "supplier,productFolder,images",
            100
        )
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