package ru.geekbrains.shopcatalog.view.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.room.ProductEntity
import ru.geekbrains.shopcatalog.utils.picasso
import ru.geekbrains.shopcatalog.utils.picassoViewHolder
import ru.geekbrains.shopcatalog.view.MainListFragment

class MainListRecyclerViewAdapter(private var onItemViewClickListener: OnItemViewClickListener) :
        RecyclerView.Adapter<MainListRecyclerViewAdapter.ViewHolder>() {

    private var values: List<ProductEntity> = listOf()

    fun setValues(v: List<ProductEntity>){
        values = v
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position])
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(product: ProductEntity){
            itemView.apply {
                findViewById<TextView>(R.id.item_title).text = product.name
                findViewById<TextView>(R.id.item_price).text = product.prise
                picassoViewHolder()
                    .load(product.imgMiniature)
                    .placeholder(R.drawable.logo_mini)
                    .into(findViewById<ImageView>(R.id.item_image))
                setOnClickListener {
                    onItemViewClickListener.onItemViewClick(product)
                }
            }
        }
    }
}