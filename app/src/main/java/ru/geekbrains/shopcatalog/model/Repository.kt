package ru.geekbrains.shopcatalog.model

interface Repository {
    fun getProductFromServer(): Product
    fun getProductFromLocalStorage(): Product

}