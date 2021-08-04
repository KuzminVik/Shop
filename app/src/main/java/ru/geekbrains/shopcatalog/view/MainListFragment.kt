package ru.geekbrains.shopcatalog.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.apidata.ApiHelperImpl
import ru.geekbrains.shopcatalog.apidata.ApiService
import ru.geekbrains.shopcatalog.databinding.FragmentMainListBinding
import ru.geekbrains.shopcatalog.localdata.DatabaseBuilder
import ru.geekbrains.shopcatalog.localdata.DatabaseHelperImpl
import ru.geekbrains.shopcatalog.room.ProductEntity
import ru.geekbrains.shopcatalog.utils.logTurnOn
import ru.geekbrains.shopcatalog.utils.AppState
import ru.geekbrains.shopcatalog.utils.showSnackBar
import ru.geekbrains.shopcatalog.view.adapters.MainListRecyclerViewAdapter
import ru.geekbrains.shopcatalog.viewmodel.MainViewModel
import ru.geekbrains.shopcatalog.viewmodel.ViewModelFactory

private const val TAG ="MainListFragment"

class MainListFragment : Fragment() {
    private var _binding: FragmentMainListBinding? = null
    private val binding get() = _binding!!
    private var columnCount = 2
//    private val viewModel: MainViewModel by lazy {ViewModelProvider(this).get(MainViewModel::class.java)}
    private lateinit var viewModel: MainViewModel
//    private val newProductsAdapter = NewProductsRecyclerAdapter()

    private val mainListAdapter = MainListRecyclerViewAdapter(
            object : OnItemViewClickListener{
        override fun onItemViewClick(product: ProductEntity) {
            parentFragmentManager.apply {
                beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.container, ProductFragment.newInstance(Bundle().apply {
                        putParcelable(ProductFragment.BUNDLE_EXTRA, product)
                    }))
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        }
    }
    )

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
        setupViewModel()

        binding.list.adapter = mainListAdapter
//        binding.listNewProduct.adapter = newProductsAdapter
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getListProductsFromApi("productFolder=https://online.moysklad.ru/api/remap/1.2/entity/productfolder/b7af289f-32c2-11e6-7a69-8f55000281bf")

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
            is AppState.SuccessList -> {
                binding.listFragmentLoadingLayout.visibility = View.GONE
//                newProductsAdapter.setValues(appState.newProductsData)
                mainListAdapter.setValues(appState.productListData)
                if(logTurnOn) {Log.d(TAG, "!!! mainListAdapter.setValues(appState.productListData)")}
            }
            is AppState.Loading -> {
                binding.listFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.ErrorList -> {
                binding.listFragmentLoadingLayout.visibility = View.GONE
                binding.mainView.showSnackBar(
                        getString(R.string.error),
                        getString(R.string.reload),
                        { viewModel.getListProductsFromApi("productFolder=https://online.moysklad.ru/api/remap/1.2/entity/productfolder/b7af289f-32c2-11e6-7a69-8f55000281bf") }
                )
            }
        }
    }

    fun setupViewModel(){
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(ApiService()),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext()))

            )
        ).get(MainViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(product: ProductEntity)
    }
}
