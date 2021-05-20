package ru.geekbrains.shopcatalog.view

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.model.Product

class MainListRecyclerViewAdapter(private var onItemViewClickListener: MainListFragment.OnItemViewClickListener?) : RecyclerView.Adapter<MainListRecyclerViewAdapter.ViewHolder>() {

    private var values: List<Product> = listOf()

    public fun setValues(v: List<Product>){
        values = v
        notifyDataSetChanged()
    }

//    fun removeListener() {
//        onItemViewClickListener = null
//    }

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
//        val image: ImageView = view.findViewById(R.id.item_image)
        fun bind(product: Product){
            itemView.findViewById<TextView>(R.id.item_title).text = product.name
            itemView.findViewById<TextView>(R.id.item_price).text = product.salePrices.toString()
            itemView.setOnClickListener {
            onItemViewClickListener?.onItemViewClick(product)
            }
        }

    }
}