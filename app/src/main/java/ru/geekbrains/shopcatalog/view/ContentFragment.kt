package ru.geekbrains.shopcatalog.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import com.google.android.material.bottomappbar.BottomAppBar
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.databinding.FragmentContentBinding

class ContentFragment : Fragment() {

    private var _binding : FragmentContentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomAppBar(view)
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.container, MainListFragment())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> toast("Fav menu item is clicked!")
            R.id.app_bar_main -> toast("Search menu item is clicked!")
            R.id.app_bar_settings -> toast("Settings item is clicked!")
            android.R.id.home -> {
                val bottomNavDrawerFragment = BottomNavigationDrawerFragment()
                bottomNavDrawerFragment.show(parentFragmentManager, bottomNavDrawerFragment.tag)
            }
        }
        return true
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
        binding.fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_shopping_cart))
        binding.fab.setOnClickListener {
                // code
        }
//        binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
//        binding.bottomAppBar.inflateMenu(R.menu.menu_bottom_bar)
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ContentFragment()
    }
}