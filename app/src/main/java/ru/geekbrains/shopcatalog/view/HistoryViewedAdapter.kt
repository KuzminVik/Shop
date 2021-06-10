package ru.geekbrains.shopcatalog.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.model.Product

class HistoryViewedAdapter(private var onItemViewClickListener: MainListFragment.OnItemViewClickListener?)
    : RecyclerView.Adapter<HistoryViewedAdapter.RecyclerItemViewHolder>() {
    private var data: List<Product> = arrayListOf()

    fun setData(data: List<Product>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewedAdapter.RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_recycler_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: HistoryViewedAdapter.RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(product: Product) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.apply {
                    findViewById<TextView>(R.id.item_title).text = product.name
                    findViewById<TextView>(R.id.item_price).text = product.salePrices
                    setOnClickListener {
                        onItemViewClickListener?.onItemViewClick(product)
                    }
                }
            }
        }

    }
}