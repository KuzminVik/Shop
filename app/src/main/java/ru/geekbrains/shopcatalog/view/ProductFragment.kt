package ru.geekbrains.shopcatalog.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.apidata.ApiHelperImpl
import ru.geekbrains.shopcatalog.apidata.ApiService
import ru.geekbrains.shopcatalog.databinding.ProductFragmentBinding
import ru.geekbrains.shopcatalog.localdata.DatabaseBuilder
import ru.geekbrains.shopcatalog.localdata.DatabaseHelperImpl
import ru.geekbrains.shopcatalog.localdata.entity.ProductEntity
import ru.geekbrains.shopcatalog.utils.AppState
import ru.geekbrains.shopcatalog.utils.picasso
import ru.geekbrains.shopcatalog.utils.showSnackBar
import ru.geekbrains.shopcatalog.view.adapters.HistoryViewedAdapter
import ru.geekbrains.shopcatalog.view.adapters.OnItemViewClickListener
import ru.geekbrains.shopcatalog.viewmodel.DetailsViewModel
import ru.geekbrains.shopcatalog.viewmodel.ViewModelFactory

class ProductFragment : Fragment() {
    private var _binding: ProductFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var productBundle: ProductEntity
    private lateinit var viewModel: DetailsViewModel

    companion object {
        const val BUNDLE_EXTRA = "product"
        fun newInstance(bundle: Bundle): ProductFragment {
            val fragment = ProductFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val historyViewedAdapter: HistoryViewedAdapter by lazy {
        HistoryViewedAdapter(
            object : OnItemViewClickListener {
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
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: ProductEntity(
            "0",
            "0",
            "0",
            "0",
            "0",
            "0",
            "0",
            "0",
            "0",
            "0"
        )
        setupViewModel()
        setupObserverHistory()
        setupObserverDetails()
        setupObserverVariants()
        binding.historyViewedRecyclerview.adapter = historyViewedAdapter
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

    private  fun setupObserverDetails(){
        viewModel.getProductLD().observe(viewLifecycleOwner, {
            when (it) {
                is AppState.SuccessProduct -> {
                    binding.mainView.visibility = View.VISIBLE
                    binding.productFragmentLoadingLayout.visibility = View.GONE
                    setProduct(product = it.productData)
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
                        getString(R.string.ok),
                        {

                        }
                    )
                }
                else -> {
                    // code
                }
            }
            // !!! Немного тупости - подписываемся и сами же кидаем продукт в аппстейт

        })
        viewModel.getProductDetails(productBundle)
    }

    private fun setupObserverHistory(){
        viewModel.getHistoryLD().observe(viewLifecycleOwner, {
            when (it) {
                is AppState.SuccessHistory -> {
                    historyViewedAdapter.setData(it.historyData)
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
        })
    }

    private fun setupObserverVariants(){
        viewModel.getVariantsLD().observe(viewLifecycleOwner, {
            when(it){
                is AppState.SuccessVariants ->{
                    val chipGroup = binding.sizeLayout
                    chipGroup.isSingleSelection = false
                    for (index in it.variantsData.indices){
                        val chip = layoutInflater.inflate(R.layout.single_chip_layout, chipGroup, false) as Chip
                        chip.isClickable = true
                        chip.text = it.variantsData[index].size
                        chipGroup.addView(chip)
                    }
                }
                is AppState.Loading -> {
                    //TODO
                }
                is AppState.ErrorVariants ->{
                    //TODO
                }
            }
        })
        viewModel.getVariants(productBundle.id_product)
    }

    private fun setProduct(product: ProductEntity) {
        picasso()
            .load(product.imgLoad)
            .into(binding.ivFirst)
        binding.tvName.text = product.name
        binding.tvDesc.text = product.description
        binding.supplier.text = product.supplier
        binding.tvPrice.text = product.prise
        binding.btnAdd.setOnClickListener {
            Toast.makeText(context, "Товар добавлен в избранное", Toast.LENGTH_LONG).show()
        }
        viewModel.saveHistoryProductToToDB(product)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}