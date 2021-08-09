package ru.geekbrains.shopcatalog.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.databinding.FragmentBottomNavigationDrawerBinding
import ru.geekbrains.shopcatalog.googlemaps.GoogleMapsFragment
import ru.geekbrains.shopcatalog.utils.toast

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomNavigationDrawerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBottomNavigationDrawerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_settings -> toast("CLIC!!!")
                R.id.action_main ->
                    addFragment(CategoriesFragment())
                R.id.action_favorite -> toast("CLIC!!!")
                R.id.action_contacts -> toast("CLIC!!!")
                R.id.action_geolocation ->
                    addFragment(GoogleMapsFragment())
            }
//            binding.navigationView.visibility = View.GONE
            DrawerLayout(requireContext()).closeDrawers()
//            val dv: DrawerLayout = view.findViewById(R.id.drawer_layout)
//            dv.closeDrawers()
            true
        }
    }

    private fun addFragment(fragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }

}