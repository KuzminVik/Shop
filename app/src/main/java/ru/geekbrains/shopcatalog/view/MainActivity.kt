package ru.geekbrains.shopcatalog.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import ru.geekbrains.shopcatalog.googlemaps.GoogleMapsFragment
import ru.geekbrains.shopcatalog.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        registerDrawer(toolbar)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainListFragment())
                .commitNow()
        }

        val navView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        navView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MainListFragment())
                        .commitNow()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_favorite -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, FavoriteFragment())
                        .commitNow()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_categories -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, CategoriesFragment())
                        .commitNow()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun registerDrawer(toolbar: Toolbar) {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            val id = item.itemId
            if (navigateFragment(id)) {
                drawer.closeDrawer(GravityCompat.START)
                return@OnNavigationItemSelectedListener true
            }
            false
        })
    }

    private fun navigateFragment(id: Int): Boolean {
        when (id) {
            R.id.action_settings -> {
                // открыть фрагмент в контейнере
                return true
            }
            R.id.action_main -> {
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, ProductFragment.newInstance())
//                        .commitNow()
                return true
            }
            R.id.action_favorite -> {
                // открыть фрагмент в контейнере
                return true
            }
            R.id.action_contacts -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, ContentProviderFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }

                return true
            }
            R.id.action_geolocation -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .add(R.id.container, GoogleMapsFragment())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
//                setOnClickListener { checkPermission() }
                }
                return true
            }
        }
        return false
    }

    companion object {
        fun removeGeofences(context: Context, triggeringGeofenceList: MutableList<Geofence>) {
            val geofenceIdList = mutableListOf<String>()
            for (entry in triggeringGeofenceList) {
                geofenceIdList.add(entry.requestId)
            }
            LocationServices.getGeofencingClient(context).removeGeofences(geofenceIdList)
        }

//        fun showNotification(context: Context?, message: String) {
//            val CHANNEL_ID = "REMINDER_NOTIFICATION_CHANNEL"
//            var notificationId = 1589
//            notificationId += Random(notificationId).nextInt(1, 30)
//
//            val notificationBuilder = NotificationCompat.Builder(context!!.applicationContext, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_alarm)
//                .setContentTitle(context.getString(R.string.app_name))
//                .setContentText(message)
//                .setStyle(
//                    NotificationCompat.BigTextStyle()
//                        .bigText(message)
//                )
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val channel = NotificationChannel(
//                    CHANNEL_ID,
//                    context.getString(R.string.app_name),
//                    NotificationManager.IMPORTANCE_DEFAULT
//                ).apply {
//                    description = context.getString(R.string.app_name)
//                }
//                notificationManager.createNotificationChannel(channel)
//            }
//            notificationManager.notify(notificationId, notificationBuilder.build())
//        }
    }
}