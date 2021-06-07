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
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.databinding.FragmentMainListBinding
import ru.geekbrains.shopcatalog.model.Product
import ru.geekbrains.shopcatalog.model.ProductDTO
import ru.geekbrains.shopcatalog.viewmodel.AppState
import ru.geekbrains.shopcatalog.viewmodel.MainViewModel

class MainListFragment : Fragment() {
    private var _binding: FragmentMainListBinding? = null
    private val binding get() = _binding!!
    private var columnCount = 2

    private val viewModel: MainViewModel by lazy {ViewModelProvider(this).get(MainViewModel::class.java)}

//    private val newProductsAdapter = NewProductsRecyclerAdapter()

    private val mainListAdapter = MainListRecyclerViewAdapter(
            object : OnItemViewClickListener{
        override fun onItemViewClick(product: Product) {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                        .replace(R.id.container, ProductFragment.newInstance(Bundle().apply {
                            putParcelable(ProductFragment.BUNDLE_EXTRA, product)
                        }))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.adapter = mainListAdapter
//        binding.listNewProduct.adapter = newProductsAdapter
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getProduct()

//        val rwNewProduct: RecyclerView = view.findViewById(R.id.list_new_product)
//        rwNewProduct.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)

        val list: RecyclerView = view.findViewById(R.id.list)
        list.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(view.context)
            else -> GridLayoutManager(view.context, columnCount)
        }
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"
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
                binding.listFragmentLoadingLayout.visibility = View.GONE
//                newProductsAdapter.setValues(appState.newProductsData)
                mainListAdapter.setValues(appState.productData as List<Product>)
            }
            is AppState.Loading -> {
                binding.listFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.listFragmentLoadingLayout.visibility = View.GONE
                binding.mainView.showSnackBar(
                        getString(R.string.error),
                        getString(R.string.reload),
                        { viewModel.getProduct() }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(product: Product)
    }
}

fun View.showSnackBar(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}
