package ru.geekbrains.shopcatalog.view

import android.app.IntentService
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import ru.geekbrains.shopcatalog.BuildConfig
import ru.geekbrains.shopcatalog.model.Price
import ru.geekbrains.shopcatalog.model.Product
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val ID_EXTRA = "id_extra"
private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10000

class DetailsService (name: String = "DetailService") : IntentService(name) {

    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        } else {
            val id = intent.getStringExtra(ID_EXTRA) ?: " "
            if(id==" "){
                onEmptyData()
            }else{
                loadProduct(id)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadProduct(id : String){
        try {
            val uri = URL("https://online.moysklad.ru/api/remap/1.2/entity/product/$id")
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.apply {
                    requestMethod = REQUEST_GET
                    addRequestProperty("Authorization", "Bearer ${BuildConfig.API_AUTHORIZATION}")
                    readTimeout = REQUEST_TIMEOUT
                }
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val product: Product = Gson().fromJson(getLines(bufferedReader), Product::class.java)
                onResponse(product)
                } catch (e: Exception) {
                    Log.e("", "Fail connection!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", e)
                    onErrorRequest(e.message ?: "Empty error")
                } finally {
                    urlConnection.disconnect()
                }
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            onMalformedURL()        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun onResponse(product: Product) {
        if (product.id == null) {
            onEmptyResponse()
        } else {
            onSuccessResponse(product.id, product.name, product.description, product.salePrices)
        }
    }

    private fun onSuccessResponse(id: String?, name: String?, description: String?, salePrices: ArrayList<Price>) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(DETAILS_ID_EXTRA, id)
        broadcastIntent.putExtra(DETAILS_NAME_EXTRA, name)
        broadcastIntent.putExtra(DETAILS_DESC_EXTRA, description)
        broadcastIntent.putParcelableArrayListExtra(DETAILS_PRICE_EXTRA, salePrices)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onMalformedURL() {
        putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onErrorRequest(error: String) {
        putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, error)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoadResult(DETAILS_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyData() {
        putLoadResult(DETAILS_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)
    }
}