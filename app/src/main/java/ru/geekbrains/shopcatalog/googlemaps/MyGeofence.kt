package ru.geekbrains.shopcatalog.googlemaps

import com.google.android.gms.location.Geofence
import java.io.Serializable


class MyGeofence(
    private val id: Int,
    private val latitude: Double,
    private val longitude: Double,
    private val radius: Float,
    val transitionType: Int
) :
    Serializable {

    fun toGeofence(): Geofence {
        return Geofence.Builder()
            .setRequestId(id.toString())
            .setTransitionTypes(transitionType)
            .setCircularRegion(latitude, longitude, radius)
            .setExpirationDuration(ONE_MINUTE.toLong())
            .build()
    }

    companion object {
        private const val ONE_MINUTE = 60000
    }

    @JvmName("getTransitionType1")
    fun getTransitionType(): Int {
        return transitionType
    }
}