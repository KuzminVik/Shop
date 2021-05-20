package ru.geekbrains.shopcatalog.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.model.Product

class NewProductsRecyclerAdapter : RecyclerView.Adapter<NewProductsRecyclerAdapter.MyViewHolder>() {

    private var values: List<Product> = listOf()

    public fun setValues(v: List<Product>){
        values = v
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.newproduct_recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(values[position])
    }

    override fun getItemCount(): Int = values.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(product: Product) {
            itemView.findViewById<TextView>(R.id.item_title).text = product.name
            itemView.findViewById<TextView>(R.id.item_price).text = product.salePrices.toString()
        }
    }
}