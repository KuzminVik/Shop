package ru.geekbrains.shopcatalog.googlemaps

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import ru.geekbrains.shopcatalog.R

private const val TAG = "GeofenceBroadcastReceiver"

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    lateinit var key: String
    lateinit var text: String

    override fun onReceive(context: Context?, intent: Intent?) {

        if (context != null) {
            val geofencingEvent = GeofencingEvent.fromIntent(intent)
            val geofencingTransition = geofencingEvent.geofenceTransition

            if (geofencingTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofencingTransition == Geofence.GEOFENCE_TRANSITION_DWELL
            ) {
                // Retrieve data from intent
                if (intent != null) {
                    key = intent.getStringExtra("key")!!
                    text = intent.getStringExtra("message")!!
                }

                Toast.makeText(context, "ВЫ вошли в геозону", Toast.LENGTH_LONG).show()


                // remove geofence
                val triggeringGeofences = geofencingEvent.triggeringGeofences
                GoogleMapsFragment.removeGeofences(context, triggeringGeofences)
            }
        }

//        val geofencingEvent = GeofencingEvent.fromIntent(intent)
//        if (geofencingEvent.hasError()) {
//            val errorMessage = GeofenceStatusCodes
//                .getStatusCodeString(geofencingEvent.errorCode)
//            Log.e(TAG, errorMessage)
//            return
//        }else{
//            val geofenceTransition = geofencingEvent.geofenceTransition
//            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
//                geofencingEvent.triggeringGeofences.forEach {
//                    val geofenceTransitionDetails = StringBuilder().apply {
//                        if (intent != null) {
//                            append("Action: ${intent.action}\n")
//                            append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
//                            toString().also {
//                                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
//                            }
//
//                        }
//                    }
//                    Log.i(TAG, geofenceTransitionDetails.toString())
//                }
//            } else {
//                // Log the error.
//                Log.e(TAG, "error sendNotification")
//            }
//        }

    }
}