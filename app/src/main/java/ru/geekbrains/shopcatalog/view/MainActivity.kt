package ru.geekbrains.shopcatalog.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.location.*
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import ru.geekbrains.shopcatalog.googlemaps.GoogleMapsFragment
import ru.geekbrains.shopcatalog.R
import java.security.AccessController.getContext

class MainActivity : AppCompatActivity(R.layout.main_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.for_сontent, ContentFragment())
                .commitNow()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}