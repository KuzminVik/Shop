package ru.geekbrains.shopcatalog.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.viewmodel.AppState
import ru.geekbrains.shopcatalog.databinding.ProductFragmentBinding
import ru.geekbrains.shopcatalog.model.Product
import ru.geekbrains.shopcatalog.viewmodel.MainViewModel

class ProductFragment : Fragment() {
    private var _binding: ProductFragmentBinding? = null
    private val binding get() = _binding!!


    companion object {
        fun newInstance() = ProductFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = ProductFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getProduct()
//        val observer = Observer<Product> { renderData(it) }
//        viewModel.getData().observe(viewLifecycleOwner, observer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val productData = appState.productData
                binding.loadingLayout.visibility = View.GONE
                setData(productData)
                Snackbar.make(binding.mainView, "Загрузка данных завершена", Snackbar.LENGTH_LONG).show()
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.mainView, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getProduct() }
                    .show()
            }
        }
    }

    private fun setData(productData : List<Product>){
        binding.tvName.text = productData[0].name
        binding.tvDesc.text = productData[0].description
        binding.tvPrice.text = productData[0].salePrices.toString()
        binding.ivFirst.setImageResource(R.drawable.test_mini)
//        binding.btnAdd.setOnClickListener { context?.let { it1 -> viewModel.userClicked(it1) } }
        binding.btnAdd.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                Toast.makeText(context, "Товар добавлен в избранное", Toast.LENGTH_LONG).show()
            }
        })
    }

}