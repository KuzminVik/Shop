package ru.geekbrains.shopcatalog.view

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.model.Product

import ru.geekbrains.shopcatalog.view.dummy.DummyContent.DummyItem

class MainListRecyclerViewAdapter(
    private val values: List<Product>
) : RecyclerView.Adapter<MainListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_main_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.titleView.text = item.name
        holder.contentView.text = item.salePrices.toString()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val image: ImageView = view.findViewById(R.id.item_image)
        val titleView: TextView = view.findViewById(R.id.item_title)
        val contentView: TextView = view.findViewById(R.id.item_price)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}