package ru.geekbrains.shopcatalog.view

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
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
            childFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.container, MainListFragment())
                addToBackStack(null)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> toast("Fav menu item is clicked!")
            R.id.app_bar_main -> toast("childFragmentManager ${childFragmentManager.backStackEntryCount.toString()}")
            R.id.app_bar_settings -> toast("parentFragmentManager${parentFragmentManager.backStackEntryCount.toString()}")
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
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (childFragmentManager.backStackEntryCount > 1 && parentFragmentManager.backStackEntryCount == 0 ) {
                    childFragmentManager.popBackStack()
                    return
                }else if(childFragmentManager.backStackEntryCount > 1 && parentFragmentManager.backStackEntryCount > 0){
                    parentFragmentManager.popBackStack()
                } else if(childFragmentManager.backStackEntryCount == 1 && parentFragmentManager.backStackEntryCount == 0)
                parentFragmentManager.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backCallback)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ContentFragment()
    }
}