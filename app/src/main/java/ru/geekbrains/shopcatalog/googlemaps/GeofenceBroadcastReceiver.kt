package ru.geekbrains.shopcatalog.googlemaps

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import ru.geekbrains.shopcatalog.utils.logTurnOn

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
                    GoogleMapsFragment
                        .showNotification(
                            context.applicationContext,
                            text
                        )
                } else{
                    if(logTurnOn) Log.d(TAG, "intent == null")
                }

                // remove geofence
                val triggeringGeofences = geofencingEvent.triggeringGeofences
                GoogleMapsFragment.removeGeofences(context, triggeringGeofences)
            }
        }
    }
}