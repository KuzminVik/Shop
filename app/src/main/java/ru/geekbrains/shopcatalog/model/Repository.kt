package ru.geekbrains.shopcatalog.model

interface Repository {
    fun getProductFromServer(): List<Product>
    fun getProductFromLocalStorage(): List<Product>

}