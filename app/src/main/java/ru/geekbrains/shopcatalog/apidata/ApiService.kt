package ru.geekbrains.shopcatalog.apidata

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

    suspend fun getListCategory(): CategoriesListDTO{
        return productAPI.getListCategories("Bearer ${BuildConfig.API_AUTHORIZATION}")
    }

    suspend fun getListProducts(id: String): ProductsListDTO {
        return productAPI.getListProducts(
            "Bearer ${BuildConfig.API_AUTHORIZATION}",
            "$id;quantityMode=positiveOnly",
            "product",
            "supplier,productFolder,images",
            100
        )
    }

    suspend fun getListVariants(productId: String): VariantsListDTO{
        return productAPI.getListVariantForProduct(
            "Bearer ${BuildConfig.API_AUTHORIZATION}",
            "productid=$productId"
        )
    }

    suspend fun getVariantIsStock(id: String): ListVariantIsStockDTO{
        return productAPI.getVariantIsStock(
            "Bearer ${BuildConfig.API_AUTHORIZATION}",
            "variant=https://online.moysklad.ru/api/remap/1.2/entity/variant/$id"
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