package ru.geekbrains.shopcatalog.model

import android.os.Build
import android.os.Handler
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import ru.geekbrains.shopcatalog.BuildConfig
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Thread.sleep
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class RepositoryImpl : Repository {

    private var data : Product? = null

    private val onLoadListener: ProductLoader.ProductLoaderListener =
        object : ProductLoader.ProductLoaderListener {
            override fun onLoaded(product: Product) {
                getTestData(product)
            }
            override fun onFailed(throwable: Throwable) {
                //Обработка ошибки
            }
        }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getProductFromServer(): MutableList<Product> {
        val loader = ProductLoader(onLoadListener)
        loader.loadProduct()
        val listProduct : MutableList<Product> = ArrayList()
//        while (data==null){
//            sleep(1000)
//            Log.e("", "ДАННЫЕ С СЕРВЕРА РАВНЫ NULL")
//        }
        for (i in 0..10){
            listProduct.add(data!!)
        }
        return listProduct
    }

    override fun getProductFromLocalStorage(): MutableList<Product> {
        val salePrices : ArrayList<Price> = ArrayList()
        salePrices.add(Price(850000.0))
        val listProduct : MutableList<Product> = ArrayList()
        for (i in 0..10){
            listProduct.add(Product(
                    "d90a534c-be28-11e7-7a6c-d2a9001ad6c2",
                    true,
                    "2017-10-31 13:47:27.986",
                    "Комплект 2в1 зимн. \"Ванкувер\" для беременных лайм",
                    "Тип: для беременных, обычная" +
                            "\n В комплекте: куртка, вставка на животик для беременных" +
                            "\n Утеплитель: Термофинн 250 гр/м (куртка), синтепон 150 гр/м (брюки)" +
                            "\n Капюшон: на молнии, отстегивается" +
                            "\n Карманы: боковые на молнии" +
                            "\n Кулиски по низу куртки: есть" +
                            "\n Длина рукава: 63 см (с митенками 68 см)" +
                            "\n Длина изделия по спине: 72 см" +
                            "\n Пояс брюк: трикотажная вставка на живот, пояс на резинке регулируется" +
                            "\n Длина брюк по внутреннему шву: 77 см" +
                            "\n Рекомендации по уходу: химчистка",
                salePrices))
        }
        return listProduct
    }

    override fun getNewProductsFromLocalStorage(): List<Product> {
        val salePrices : ArrayList<Price> = ArrayList()
        salePrices.add(Price(850000.0))
        val listProduct : MutableList<Product> = ArrayList()
        for (i in 0..10){
            listProduct.add(Product(
                    "d90a534c-be28-11e7-7a6c-d2a9001ad6c2",
                    true,
                    "2017-10-31 13:47:27.986",
                    "Комплект 2в1 зимн. \"Ванкувер\" для беременных лайм",
                    "Тип: для беременных, обычная" +
                            "\n В комплекте: куртка, вставка на животик для беременных" +
                            "\n Утеплитель: Термофинн 250 гр/м (куртка), синтепон 150 гр/м (брюки)" +
                            "\n Капюшон: на молнии, отстегивается" +
                            "\n Карманы: боковые на молнии" +
                            "\n Кулиски по низу куртки: есть" +
                            "\n Длина рукава: 63 см (с митенками 68 см)" +
                            "\n Длина изделия по спине: 72 см" +
                            "\n Пояс брюк: трикотажная вставка на живот, пояс на резинке регулируется" +
                            "\n Длина брюк по внутреннему шву: 77 см" +
                            "\n Рекомендации по уходу: химчистка",
                salePrices))
        }
        return listProduct
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getTestData(product: Product) {
        data = product
    }
}