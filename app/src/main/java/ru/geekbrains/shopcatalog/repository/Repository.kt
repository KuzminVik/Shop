package ru.geekbrains.shopcatalog.repository

import retrofit2.Callback
import ru.geekbrains.shopcatalog.model.Product
import ru.geekbrains.shopcatalog.model.ProductListDTO

interface Repository {
    fun getProductsFromLocalStorage(): List<Product>
    fun getNewProductsFromLocalStorage(): List<Product>
    fun getListProductsInTheCategory(id: String, callback: Callback<ProductListDTO>)
}