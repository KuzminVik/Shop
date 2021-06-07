package ru.geekbrains.shopcatalog.repository

import ru.geekbrains.shopcatalog.model.Product
import ru.geekbrains.shopcatalog.model.ProductDTO

interface Repository {
    fun getProductsFromLocalStorage(): List<Product>
    fun getNewProductsFromLocalStorage(): List<Product>

}