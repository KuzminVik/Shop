package ru.geekbrains.shopcatalog.view.adapters

import ru.geekbrains.shopcatalog.room.ProductEntity

interface OnItemViewClickListener {
    fun onItemViewClick(product: ProductEntity)
}

interface OnItemViewClickListenerProducts {
    fun onItemViewClick(id: String)
}