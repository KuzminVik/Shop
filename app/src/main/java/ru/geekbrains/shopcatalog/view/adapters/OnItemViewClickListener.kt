package ru.geekbrains.shopcatalog.view.adapters

import ru.geekbrains.shopcatalog.localdata.entity.ProductEntity

//Используется в адаптере списка продуктов и адаптере просмотренных продуктов
interface OnItemViewClickListener {
    fun onItemViewClick(product: ProductEntity)
}

//Используется в адаптере категорий
interface OnItemViewClickListenerProducts {
    fun onItemViewClick(id: String)
}