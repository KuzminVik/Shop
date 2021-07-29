package ru.geekbrains.shopcatalog.repository

import retrofit2.Callback
import ru.geekbrains.shopcatalog.model.Product
import ru.geekbrains.shopcatalog.model.ProductListDTO

interface Repository {
    fun getListProductsInTheCategory(id: String, callback: Callback<ProductListDTO>)
    fun getListCategory()
}