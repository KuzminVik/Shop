package ru.geekbrains.shopcatalog.view

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
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

    private fun registerDrawer(toolbar: Toolbar){
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
                // открыть фрагмент в контейнере
                return true
            }
        }
        return false
    }

//    private fun checkPermission() {
//        activity?.let {
//            when {
//                ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) ==
//                        PackageManager.PERMISSION_GRANTED -> {
//                    getLocation()
//                }
//                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
//                    showRationaleDialog()
//                }
//                else -> {
//                    requestPermission()
//                }
//            }
//        }
//    }


    override fun onDestroy() {
        super.onDestroy()
    }
}