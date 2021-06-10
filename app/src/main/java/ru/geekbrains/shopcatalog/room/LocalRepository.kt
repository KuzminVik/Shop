package ru.geekbrains.shopcatalog.room

import ru.geekbrains.shopcatalog.model.Product

interface LocalRepository {
    fun getAllHistoryViewed(): List<Product>
    fun saveEntity(product: Product)

}