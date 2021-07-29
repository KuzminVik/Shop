package ru.geekbrains.shopcatalog.room

import ru.geekbrains.shopcatalog.model.Product
import ru.geekbrains.shopcatalog.utils.convertHistoryEntityToProduct
import ru.geekbrains.shopcatalog.utils.convertProductToEntity

class LocalRepositoryImpl(private val localDataSource: ViewedProductsDAO) : LocalRepository {
    override fun getAllHistoryViewed(): List<Product> {
        val res = localDataSource.detAllViewedProduct()
        if (res.size>6) localDataSource.deleteViewedProduct(res.first())
        return convertHistoryEntityToProduct(localDataSource.detAllViewedProduct())
    }

    override fun saveEntity(product: Product) {
        localDataSource.insertViewedProduct(convertProductToEntity(product))
    }
}