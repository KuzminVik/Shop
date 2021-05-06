package ru.geekbrains.shopcatalog.model

class RepositoryImpl : Repository {

    override fun getProductFromServer(): Product {
        return Product()
    }

    override fun getProductFromLocalStorage(): Product {
        return Product()
    }
}