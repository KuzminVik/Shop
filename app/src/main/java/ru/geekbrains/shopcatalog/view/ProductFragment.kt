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
        val product = arguments?.getParcelable<Product>(BUNDLE_EXTRA)
        if(product!=null){
            binding.tvName.text = product.name
            binding.tvDesc.text = product.description
            binding.tvPrice.text = product.salePrices.toString()
            binding.ivFirst.setImageResource(R.drawable.test_mini)
            binding.btnAdd.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    Toast.makeText(context, "Товар добавлен в избранное", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}