package ru.geekbrains.shopcatalog.room

import ru.geekbrains.shopcatalog.model.Product
import ru.geekbrains.shopcatalog.utils.convertHistoryEntityToProduct
import ru.geekbrains.shopcatalog.utils.convertProductToEntity

class LocalRepositoryImpl(private val localDataSource: ViewedRroductsDAO) : LocalRepository {
    override fun getAllHistoryViewed(): List<Product> {
        val res = localDataSource.all()
        if (res.size>6) localDataSource.delete(res.first())
        return convertHistoryEntityToProduct(localDataSource.all())
    }

    override fun saveEntity(product: Product) {
        localDataSource.insert(convertProductToEntity(product))
    }
}