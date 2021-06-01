package ru.geekbrains.shopcatalog.model

interface Repository {
//    fun getProductFromServer(): List<Product>
    fun getProductsFromLocalStorage(): List<Product>
    fun getNewProductsFromLocalStorage(): List<Product>

}