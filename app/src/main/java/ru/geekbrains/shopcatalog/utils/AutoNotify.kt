package ru.geekbrains.shopcatalog.utils

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.shopcatalog.localdata.entity.ProductEntity

fun <R : RecyclerView.ViewHolder> RecyclerView.Adapter<R>.autoNotify(oldList: List<ProductEntity>, newList: List<ProductEntity>){
    val diffItemCallback = object : DiffUtil.Callback(){
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id_product == newList[newItemPosition].id_product

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]
            return old.name == new.name && old.description == new.description && old.imgLoad == new.imgLoad
                    && old.imgMiniature == new.imgMiniature && old.imgTiny == new.imgTiny
                    && old.prise == new.prise && old.supplier == new.supplier
        }
    }
    val diffResult = DiffUtil.calculateDiff(diffItemCallback)
    diffResult.dispatchUpdatesTo(this)
}