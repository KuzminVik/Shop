package ru.geekbrains.shopcatalog.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.shopcatalog.databinding.ItemRvLayoutBinding
import ru.geekbrains.shopcatalog.localdata.entity.CategoryEntity

class CategoriesAdapter(private var onItemViewClickListener: OnItemViewClickListenerProducts): RecyclerView.Adapter<CategoriesAdapter.DataViewHolder>() {

    private var categories: List<Pair<CategoryEntity, Int>> = listOf()

    fun addData(list: List<Pair<CategoryEntity, Int>>) {
        categories = list
        notifyDataSetChanged()
    }

    inner class DataViewHolder(private val binding: ItemRvLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Pair<CategoryEntity, Int>) {
            when(category.second){
                1 ->{
                    binding.tvName1.text = category.first.name
                    binding.tvName2.visibility = View.GONE
                    binding.tvName3.visibility = View.GONE
                }
                2 ->{
                    binding.tvName2.text = category.first.name
                    binding.tvName1.visibility = View.GONE
                    binding.tvName3.visibility = View.GONE
                    itemView.apply { setOnClickListener {
                        onItemViewClickListener.onItemViewClick(category.first.id_category)
                    } }
                }
                3 ->{
                    binding.tvName3.text = category.first.name
                    binding.tvName1.visibility = View.GONE
                    binding.tvName2.visibility = View.GONE
                    itemView.apply { setOnClickListener {
                        onItemViewClickListener.onItemViewClick(category.first.id_category)
                    } }
                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemRvLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

}