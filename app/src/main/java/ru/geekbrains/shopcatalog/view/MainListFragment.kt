package ru.geekbrains.shopcatalog.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.geekbrains.shopcatalog.databinding.FragmentMainListBinding
import ru.geekbrains.shopcatalog.databinding.FragmentMainRecyclerItemBinding
import ru.geekbrains.shopcatalog.viewmodel.AppState
import ru.geekbrains.shopcatalog.viewmodel.MainViewModel

class MainListFragment : Fragment() {
    private var _binding: FragmentMainListBinding? = null
    private val binding get() = _binding!!
    private var columnCount = 2
    private lateinit var viewModel: MainViewModel
    private val adapter = MainListRecyclerViewAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainListBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.list.adapter = adapter


        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getProduct()

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(view.getContext())
                    else -> GridLayoutManager(view.getContext(), columnCount)
                }
                var myAdapter = MainListRecyclerViewAdapter()
                adapter = myAdapter

            }
        }
        return view
    }

    companion object {
        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            MainListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val productData = appState.productData
                binding.listFragmentLoadingLayout.visibility = View.GONE
                adapter.setValues(productData)
            }
            is AppState.Loading -> {
                binding.listFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.listFragmentLoadingLayout.visibility = View.GONE
                Snackbar
                        .make(binding.mainView, "Error", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Reload") { viewModel.getProduct() }
                        .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}