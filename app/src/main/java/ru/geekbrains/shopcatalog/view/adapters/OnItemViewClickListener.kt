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

//Используется в адаптере корзины
interface OnItemViewClickListenerFavorite{
    fun onItemViewClick(id: String)
}

//Используется для удаления в адаптере корзины
interface OnItemViewClickListenerDelete{
    fun onItemViewClick(id: Long)
}