package ru.geekbrains.shopcatalog.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import ru.geekbrains.shopcatalog.BuildConfig
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.apidata.ApiHelperImpl
import ru.geekbrains.shopcatalog.apidata.ApiService
import ru.geekbrains.shopcatalog.databinding.ProductFragmentBinding
import ru.geekbrains.shopcatalog.localdata.DatabaseBuilder
import ru.geekbrains.shopcatalog.localdata.DatabaseHelperImpl
import ru.geekbrains.shopcatalog.model.Image
import ru.geekbrains.shopcatalog.room.ProductEntity
import ru.geekbrains.shopcatalog.utils.AppState
import ru.geekbrains.shopcatalog.utils.showSnackBar
import ru.geekbrains.shopcatalog.view.adapters.HistoryViewedAdapter
import ru.geekbrains.shopcatalog.viewmodel.CategoriesViewModel
import ru.geekbrains.shopcatalog.viewmodel.DetailsViewModel
import ru.geekbrains.shopcatalog.viewmodel.ViewModelFactory

class ProductFragment : Fragment() {
    private var _binding: ProductFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var productBundle: ProductEntity
    private lateinit var viewModel: DetailsViewModel
//    private val viewModel: DetailsViewModel by lazy { ViewModelProvider(this).get(DetailsViewModel::class.java) }

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
            override fun onItemViewClick(product: ProductEntity) {
                activity?.supportFragmentManager?.apply {
                    beginTransaction()
                        .replace(R.id.container, newInstance(Bundle().apply {
                            putParcelable(BUNDLE_EXTRA, product)
                        }))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        }
    ) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        productBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: ProductEntity("0","0","0","0","0","0","0","0","0","0")
        viewModel.getProductLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getProductFromApi(productBundle.id_product)
        binding.historyViewedRecyclerview.adapter = historyViewedAdapter
        viewModel.historyLiveData.observe(viewLifecycleOwner, { renderDataHistory(it) })
        viewModel.getAllHistory()
        viewModel.imageLiveData.observe(viewLifecycleOwner, { renderDataImage(it) })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(ApiService()),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext()))
            )
        ).get(DetailsViewModel::class.java)
    }

    private fun renderData(appState: AppState) {
        when(appState){
            is AppState.SuccessProduct -> {
                binding.mainView.visibility = View.VISIBLE
                binding.productFragmentLoadingLayout.visibility = View.GONE
                setProduct(product = appState.productData)
            }

            is AppState.Loading -> {
                binding.mainView.visibility = View.GONE
                binding.productFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainView.visibility = View.VISIBLE
                binding.productFragmentLoadingLayout.visibility = View.GONE
                binding.mainView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        viewModel.getProductFromApi(productBundle.id_product)
                    }
                )
            }
            else -> {
                // code
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
            else -> {
                // code
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
            else -> {
            // code
        }
        }
    }

    private fun setProduct(product: ProductEntity) {
        binding.tvName.text = product.name
        binding.tvDesc.text = product.description
        binding.tvPrice.text = product.prise
        binding.btnAdd.setOnClickListener {
            Toast.makeText(context, "Товар добавлен в избранное", Toast.LENGTH_LONG).show()
        }
        saveProductToHistoryViewed(product)
    }

    private fun setImage(img: Image){
        val client = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val newRequest: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.API_AUTHORIZATION}")
                    .build()
                chain.proceed(newRequest)
            })
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        val picasso = Picasso.Builder(this.requireContext())
            .downloader(OkHttp3Downloader(client))
            .build()
        picasso
            .load(img.original)
            .into(binding.ivFirst)
    }

    private fun saveProductToHistoryViewed(product: ProductEntity){
        viewModel.saveHistoryProductToToDB(product)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}