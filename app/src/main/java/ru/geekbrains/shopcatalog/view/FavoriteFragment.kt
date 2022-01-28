package ru.geekbrains.shopcatalog.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.apidata.ApiHelperImpl
import ru.geekbrains.shopcatalog.apidata.ApiService
import ru.geekbrains.shopcatalog.databinding.FavoriteFragmentBinding
import ru.geekbrains.shopcatalog.localdata.DatabaseBuilder
import ru.geekbrains.shopcatalog.localdata.DatabaseHelperImpl
import ru.geekbrains.shopcatalog.utils.COLUMN_COUNT_BASE
import ru.geekbrains.shopcatalog.utils.Status
import ru.geekbrains.shopcatalog.view.adapters.FavoriteAdapter
import ru.geekbrains.shopcatalog.view.adapters.OnItemViewClickListenerDelete
import ru.geekbrains.shopcatalog.view.adapters.OnItemViewClickListenerFavorite
import ru.geekbrains.shopcatalog.viewmodel.FavoriteViewModel
import ru.geekbrains.shopcatalog.viewmodel.ViewModelFactory

class FavoriteFragment : Fragment() {

    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUi()
        setupObserver()
    }

    private fun setupUi(){
        val rv = binding.listFavorite
        adapter = FavoriteAdapter(object : OnItemViewClickListenerFavorite {
            override fun onItemViewClick(id: String) {
//                parentFragmentManager.apply {
//                    beginTransaction()
//                        .setReorderingAllowed(true)
//                        .replace(R.id.container, ProductFragment.newInstance(COLUMN_COUNT_BASE, id))
//                        .addToBackStack("ProductFragment.newInstance")
//                        .commitAllowingStateLoss()
//                }
            }
        }, object : OnItemViewClickListenerDelete{
            override fun onItemViewClick(id: Long) {
                viewModel.deleteFavoriteProduct(id)
            }
        })
        rv.adapter = adapter
    }

    private fun setupObserver(){
        viewModel.getListFavorites().observe(viewLifecycleOwner, {
            when(it.status){
                Status.SUCCESS -> {
                    binding.favoriteLoadingLayout.visibility = View.GONE
                    binding.listFavorite.visibility = View.VISIBLE
                    adapter.addData(it.data!!)
                }
                Status.LOADING -> {
                    binding.listFavorite.visibility = View.GONE
                    binding.favoriteLoadingLayout.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.favoriteLoadingLayout.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(ApiService()),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext()))
            )
        ).get(FavoriteViewModel::class.java)
    }

}