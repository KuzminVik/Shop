package ru.geekbrains.shopcatalog.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import ru.geekbrains.shopcatalog.BuildConfig
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.databinding.ProductFragmentBinding
import ru.geekbrains.shopcatalog.model.Image
import ru.geekbrains.shopcatalog.model.Product
import ru.geekbrains.shopcatalog.viewmodel.AppState
import ru.geekbrains.shopcatalog.viewmodel.DetailsViewModel
import java.io.IOException

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            is AppState.SuccessProduct -> {
                binding.mainView.visibility = View.VISIBLE
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                setProduct(appState.productData[0])
            }

            is AppState.Loading -> {
                binding.mainView.visibility = View.GONE
                binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainView.visibility = View.VISIBLE
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
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
        binding.btnAdd.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(context, "Товар добавлен в избранное", Toast.LENGTH_LONG).show()
            }
        })
        saveProductToHistoryViewed(product)
    }

    private fun setImage(img: Image){
        val client = OkHttpClient.Builder()
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val newRequest: Request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer ${BuildConfig.API_AUTHORIZATION}")
                        .build()
                    return chain.proceed(newRequest)
                }
            })
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        val picasso = Picasso.Builder(this.requireContext())
            .downloader(OkHttp3Downloader(client))
            .build()
        picasso
            .load(img.original)
            .into(binding.ivFirst);
    }

    private fun saveProductToHistoryViewed(product: Product){
        viewModel.saveHistoryProductToToDB(product)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}