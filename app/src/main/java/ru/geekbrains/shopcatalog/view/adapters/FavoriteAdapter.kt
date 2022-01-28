package ru.geekbrains.shopcatalog.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.shopcatalog.R
import ru.geekbrains.shopcatalog.databinding.FavoriteRecyclerItemBinding
import ru.geekbrains.shopcatalog.localdata.entity.FavoriteEntity
import ru.geekbrains.shopcatalog.utils.picassoViewHolder

class FavoriteAdapter(
    private var onItemViewClickListener: OnItemViewClickListenerFavorite,
    private var onItemDelete: OnItemViewClickListenerDelete
): RecyclerView.Adapter<FavoriteAdapter.DataViewHolder>() {

    private var favorites: List<FavoriteEntity> = listOf()

    fun addData(list: List<FavoriteEntity>) {
        favorites = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.DataViewHolder {
        val binding = FavoriteRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.DataViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    override fun getItemCount(): Int = favorites.size

    inner class DataViewHolder(private val binding: FavoriteRecyclerItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(fav: FavoriteEntity) {
            binding.itemTitle.apply {
                text = fav.name
                setOnClickListener {
                    onItemViewClickListener.onItemViewClick(fav.id_product)
                }
            }
            binding.itemSize.text = fav.size
            binding.itemPrice.text = fav.prise.toString()
            itemView.apply {
                picassoViewHolder()
                    .load(fav.imgMiniature)
                    .placeholder(R.drawable.logo_mini)
                    .into(binding.itemImage)
            }
            binding.imgDelete.apply {
                setOnClickListener {
                    onItemDelete.onItemViewClick(fav.id)
                }
            }
            binding.ivAdd.setOnClickListener {
                val count = Integer.parseInt(binding.editText.text.toString())
                if (count < 9){
                    binding.editText.setText(Integer.toString(count+1))
                    val res = (fav.prise ?: 0) * (count+1)
                    binding.itemPrice.setText(Integer.toString(res))
                }
            }
            binding.ivRemove.setOnClickListener {
                val count = Integer.parseInt(binding.editText.text.toString())
                if (count>1){
                    binding.editText.setText(Integer.toString(count-1))
                    val res = (fav.prise ?: 0) * (count-1)
                    binding.itemPrice.setText(Integer.toString(res))
                }
            }


        }
    }

}