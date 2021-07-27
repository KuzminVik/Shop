package ru.geekbrains.shopcatalog.view

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.databinding.FragmentBottomNavigationDrawerBinding
import ru.geekbrains.shopcatalog.googlemaps.GoogleMapsFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomNavigationDrawerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBottomNavigationDrawerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_settings -> {
                    // открыть фрагмент в контейнере
                }
                R.id.action_main -> {
//                childFragmentManager.beginTransaction()
//                    .replace(R.id.container, ProductFragment.newInstance())
//                        .commitNow()
                }
                R.id.action_favorite -> {
                    // открыть фрагмент в контейнере
                }
                R.id.action_contacts -> toast("CLIC!!!")

                R.id.action_geolocation ->
                    parentFragmentManager.apply {
                        beginTransaction()
                            .add(R.id.container, GoogleMapsFragment())
                            .addToBackStack("GoogleMapsFragment")
                            .commitAllowingStateLoss()
                    }

            }
            true
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

}