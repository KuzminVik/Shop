package ru.geekbrains.shopcatalog.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.model.Product
import ru.geekbrains.shopcatalog.room.ProductEntity
import ru.geekbrains.shopcatalog.view.MainListFragment

class HistoryViewedAdapter(private var onItemViewClickListener: MainListFragment.OnItemViewClickListener?)
    : RecyclerView.Adapter<HistoryViewedAdapter.RecyclerItemViewHolder>() {
    private var data: List<ProductEntity> = arrayListOf()

    fun setData(data: List<ProductEntity>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_recycler_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(product: ProductEntity) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.apply {
                    findViewById<TextView>(R.id.item_title).text = product.name
                    findViewById<TextView>(R.id.item_price).text = product.prise
                    setOnClickListener {
                        onItemViewClickListener?.onItemViewClick(product)
                    }
                }
            }
        }

    }
}