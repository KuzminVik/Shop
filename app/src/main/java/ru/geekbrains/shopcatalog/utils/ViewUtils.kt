package ru.geekbrains.shopcatalog.utils

import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import ru.geekbrains.shopcatalog.BuildConfig

fun View.showSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}

fun Fragment.toast(string: String?) {
    Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.BOTTOM, 0, 450)
        show()
    }
}

object OkHttpInstance {
    fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val newRequest: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.API_AUTHORIZATION}")
                    .build()
                chain.proceed(newRequest)
            })
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }
}

fun Fragment.picasso(): Picasso {
    val client = OkHttpInstance.getClient()
    return Picasso.Builder(requireContext())
        .downloader(OkHttp3Downloader(client))
        .build()
}


fun View.picassoViewHolder(): Picasso {
    val client = OkHttpInstance.getClient()
    return Picasso.Builder(context)
        .downloader(OkHttp3Downloader(client))
        .build()
}