package ru.geekbrains.shopcatalog.view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.shopcatalog.R

class MainActivity : AppCompatActivity(R.layout.main_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.for_сontent, ContentFragment.newInstance())
//                .addToBackStack("")
                .commitNow()
        }
    }

}