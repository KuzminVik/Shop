package ru.geekbrains.shopcatalog.utils

import android.content.Context
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import ru.geekbrains.shopcatalog.BuildConfig
import ru.geekbrains.shopcatalog.view.adapters.MainListRecyclerViewAdapter
import java.security.AccessController.getContext

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

fun Fragment.picasso(): Picasso{
    val client = OkHttpInstance.getClient()
    val picasso = Picasso.Builder(this.requireContext())
        .downloader(OkHttp3Downloader(client))
        .build()
    return picasso
}


fun View.picassoViewHolder(): Picasso{
    val client = OkHttpInstance.getClient()
    val picassoViewHolder = Picasso.Builder(this.context)
        .downloader(OkHttp3Downloader(client))
        .build()
    return picassoViewHolder
}