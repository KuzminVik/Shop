package ru.geekbrains.shopcatalog.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.apidata.ProductDTO

class NewProductsRecyclerAdapter : RecyclerView.Adapter<NewProductsRecyclerAdapter.MyViewHolder>() {

    private var values: List<ProductDTO> = listOf()

    public fun setValues(v: List<ProductDTO>){
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
        fun bind(productDTO: ProductDTO) {
            itemView.findViewById<TextView>(R.id.item_title).text = productDTO.name
            itemView.findViewById<TextView>(R.id.item_price).text = productDTO.salePrices.toString()
        }
    }
}