package ru.geekbrains.shopcatalog.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.request.ImageRequest
import ru.geekbrains.shopcatalog.BuildConfig
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.databinding.ProductFragmentBinding
import ru.geekbrains.shopcatalog.model.Image
import ru.geekbrains.shopcatalog.model.Product
import ru.geekbrains.shopcatalog.viewmodel.AppState
import ru.geekbrains.shopcatalog.viewmodel.DetailsViewModel

class ProductFragment : Fragment() {
    private var _binding: ProductFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var productBundle: Product
    private val viewModel: DetailsViewModel by lazy { ViewModelProvider(this).get(DetailsViewModel::class.java) }

    companion object {
        const val BUNDLE_EXTRA = "product"
        fun newInstance(bundle: Bundle) : ProductFragment{
            val fragment = ProductFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val historyViewedAdapter: HistoryViewedAdapter by lazy { HistoryViewedAdapter(
        object : MainListFragment.OnItemViewClickListener {
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
        }
    ) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = ProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Product()
        viewModel.getProductLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getProductFromRemoteSource(productBundle.id)
        binding.historyViewedRecyclerview.adapter = historyViewedAdapter
        viewModel.historyLiveData.observe(viewLifecycleOwner, Observer { renderDataHistory(it) })
        viewModel.getAllHistory()
        viewModel.imageLiveData.observe(viewLifecycleOwner, Observer { renderDataImage(it) })
    }

    private fun renderData(appState: AppState) {
        when(appState){
            is AppState.Success -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                setProduct(appState.productData[0])

            }

            is AppState.Loading -> {
                binding.mainView.visibility = View.GONE
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                binding.mainView.showSnackBar(
                        getString(R.string.error),
                        getString(R.string.reload),
                        {
                            viewModel.getProductFromRemoteSource(productBundle.id)
                        }
                )
            }
        }
    }

    private fun renderDataHistory(appState: AppState){
        when(appState){
            is AppState.SuccessHistory -> {
                historyViewedAdapter.setData(appState.historyData)
            }

            is AppState.Loading -> {
                //добавить свой прогресс бар
//                binding.mainView.visibility = View.GONE
//                binding.loadingLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun renderDataImage(appState: AppState){
        when(appState){
            is AppState.SuccessImage -> {
                setImage(appState.imageData[0])
            }

            is AppState.Loading -> {
                //добавить свой прогресс бар
            }
        }
    }

    private fun setProduct(product: Product) {
        binding.tvName.text = product.name
        binding.tvDesc.text = product.description
        binding.tvPrice.text = product.salePrices
        binding.btnAdd.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                Toast.makeText(context, "Товар добавлен в избранное", Toast.LENGTH_LONG).show()
            }
        })
        saveProductToHistoryViewed(product)
    }

    private fun setImage(img: Image){
        val imageLoader = ImageLoader.Builder(this.requireContext())
                .crossfade(true)
                .build()
        val request = ImageRequest.Builder(this.requireContext())
                .data(img.original)
                .setHeader("Authorization", "Bearer ${BuildConfig.API_AUTHORIZATION}")
                .crossfade(true)
                .target(binding.ivFirst)
                .build()
        imageLoader.enqueue(request)
    }

    private fun saveProductToHistoryViewed(product: Product){
        viewModel.saveHistoryProductToToDB(product)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}