package ru.geekbrains.shopcatalog.model

import android.R.attr.password
import android.os.Build
import android.os.Handler
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import ru.geekbrains.shopcatalog.BuildConfig
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection
import kotlin.text.Charsets.UTF_8

@RequiresApi(Build.VERSION_CODES.N)
class ProductLoader(private val listener: ProductLoaderListener) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadProduct(){
        try {
            val uri = URL("https://online.moysklad.ru/api/remap/1.2/entity/product/7f25bcde-81c3-11e3-240b-002590a28eca")
            val handler = Handler()
            Thread(Runnable {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.addRequestProperty("Authorization", "Bearer 63e3dec348cffea668be63ea7fa545fb94063a34")
                    urlConnection.readTimeout = 10000
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val product: Product =
                        Gson().fromJson(getLines(bufferedReader), Product::class.java)

                    handler.post {
                        listener.onLoaded(product)
                    }

                } catch (e: Exception) {
                    Log.e("", "Fail connection!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", e)
                    e.printStackTrace()
                    listener.onFailed(e)
                } finally {
                    urlConnection.disconnect()
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            listener.onFailed(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    interface ProductLoaderListener {
        fun onLoaded(product: Product)
        fun onFailed(throwable: Throwable)
    }
}


