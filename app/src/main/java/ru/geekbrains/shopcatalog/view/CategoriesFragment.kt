package ru.geekbrains.shopcatalog.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.apidata.ApiHelperImpl
import ru.geekbrains.shopcatalog.apidata.ApiService
import ru.geekbrains.shopcatalog.databinding.CategoriesFragmentBinding
import ru.geekbrains.shopcatalog.localdata.DatabaseBuilder
import ru.geekbrains.shopcatalog.localdata.DatabaseHelperImpl
import ru.geekbrains.shopcatalog.localdata.entity.CategoryEntity
import ru.geekbrains.shopcatalog.utils.COLUMN_COUNT_BASE
import ru.geekbrains.shopcatalog.utils.Status
import ru.geekbrains.shopcatalog.view.adapters.CategoriesAdapter
import ru.geekbrains.shopcatalog.view.adapters.OnItemViewClickListenerProducts
import ru.geekbrains.shopcatalog.viewmodel.CategoriesViewModel
import ru.geekbrains.shopcatalog.viewmodel.ViewModelFactory

private const val TAG = "CategoriesFragment"

class CategoriesFragment : Fragment() {

    private var _binding: CategoriesFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CategoriesAdapter
    private lateinit var viewModel: CategoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = CategoriesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupViewModel()
        setupObserver()

    }

    private fun setupUI() {
        val rv = binding.recyclerView
        rv.layoutManager = LinearLayoutManager(requireView().context)
        adapter = CategoriesAdapter(object : OnItemViewClickListenerProducts {
            override fun onItemViewClick(id: String) {
                parentFragmentManager.apply {
                    beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.container, MainListFragment.newInstance(COLUMN_COUNT_BASE, id))
                        .addToBackStack("MainListFragment.newInstance")
                        .commitAllowingStateLoss()
                }
            }
        })
        rv.addItemDecoration(
            DividerItemDecoration(rv.context, (rv.layoutManager as LinearLayoutManager).orientation)
        )
        rv.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.getListCategory().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { categories ->
                        renderList(categories)
                    }
                    binding.recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(ApiService()),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext()))
            )
        ).get(CategoriesViewModel::class.java)
    }

    private fun renderList(categories: List<CategoryEntity>) {
        val data: MutableList<Pair<CategoryEntity, Int>> = mutableListOf()
        val cat = categories.toMutableList()
        for (c1 in cat) {
            if (c1.pathName == "") {
                val p1 = Pair(c1, 1)
                data.add(p1)

                for (c2 in cat) {
                    if (c2.pathName == c1.name) {
                        val p2 = Pair(c2, 2)
                        data.add(p2)

                        for (c3 in cat) {
                            val temp = if(c3.pathName.split("/").size == 1){c3.pathName}else(c3.pathName.split("/")[1])
                            if (temp == c2.name) {
                                val p3 = Pair(c3, 3)
                                data.add(p3)
                            }
                        }
                    }
                }

            }
        }
        adapter.addData(data)
    }

}