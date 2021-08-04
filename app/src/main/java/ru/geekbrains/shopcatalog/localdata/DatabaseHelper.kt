package ru.geekbrains.shopcatalog.localdata

import ru.geekbrains.shopcatalog.model.Product
import ru.geekbrains.shopcatalog.room.CategoryEntity
import ru.geekbrains.shopcatalog.room.ProductEntity

interface DatabaseHelper {
    fun getAllHistoryViewed(): List<ProductEntity>
    fun saveViewedProduct(product: ProductEntity)
    fun deleteViewedProduct(product: ProductEntity)

    suspend fun saveListCategory(list: List<CategoryEntity>)
    suspend fun getAllCategory(): List<CategoryEntity>
    suspend fun deleteAllCategory()

    suspend fun saveListProduct(list: List<ProductEntity>)
    suspend fun getListProduct(nameCategory: String): List<Product>
    suspend fun getProduct(idProduct: String): Product
    suspend fun deleteProduct(product: ProductEntity)
    suspend fun deleteListProductsInCategory(nameCategory: String)
    suspend fun deleteAllProducts()
}