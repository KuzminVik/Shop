package ru.geekbrains.shopcatalog.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.bumptech.glide.Glide
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = ProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Product()
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getProductFromRemoteSource(productBundle.id)
    }

    private fun renderData(appState: AppState) {
        when(appState){
            is AppState.Success -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                if(appState.productData[0] is Product){
                    setProduct(appState.productData[0] as Product)
                }else if(appState.productData[0] is Image){
                    setImage(appState.productData[0] as Image)
                }
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

    private fun setProduct(product: Product) {
        binding.tvName.text = product.name
        binding.tvDesc.text = product.description
        binding.tvPrice.text = product.salePrices
        binding.btnAdd.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                Toast.makeText(context, "Товар добавлен в избранное", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setImage(img: Image){
//        binding.ivFirst.load(img.original)
        Glide.with(this).load(img.original).into(binding.ivFirst)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}