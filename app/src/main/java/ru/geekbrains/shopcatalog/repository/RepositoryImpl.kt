package ru.geekbrains.shopcatalog.repository

import retrofit2.Callback
import ru.geekbrains.shopcatalog.model.Product
import ru.geekbrains.shopcatalog.model.ProductListDTO

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository {

    override fun getListProductsInTheCategory(id: String, callback: Callback<ProductListDTO>) {
        remoteDataSource.getListProductsInTheCategory(id, callback)
    }

    override fun getListCategory() {
        TODO("Not yet implemented")
    }

}