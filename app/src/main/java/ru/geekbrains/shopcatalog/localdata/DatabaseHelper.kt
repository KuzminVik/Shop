package ru.geekbrains.shopcatalog.localdata

import ru.geekbrains.shopcatalog.localdata.entity.CategoryEntity
import ru.geekbrains.shopcatalog.localdata.entity.FavoriteEntity
import ru.geekbrains.shopcatalog.localdata.entity.ProductEntity
import ru.geekbrains.shopcatalog.localdata.entity.ViewedProductsEntity

interface DatabaseHelper {
    fun getAllHistoryViewed(): List<ViewedProductsEntity>
    fun saveViewedProduct(product: ViewedProductsEntity)
    fun deleteViewedProduct(product: ViewedProductsEntity)

    suspend fun saveListCategories(list: List<CategoryEntity>)
    suspend fun getAllCategories(): List<CategoryEntity>
    suspend fun getOneCategory(id: String): CategoryEntity?
    suspend fun deleteAllCategories()

    suspend fun saveListProducts(list: List<ProductEntity>)
    suspend fun getListProducts(nameCategory: String): List<ProductEntity>
    fun getProduct(idProduct: String): ProductEntity
    fun getAllViewedProduct(list: List<String>): List<ProductEntity>

    suspend fun saveFavoriteProduct(prod: FavoriteEntity)
    suspend fun getAllFavorites(): List<FavoriteEntity>
    suspend fun deleteFavoriteEntity(id: Long)

    suspend fun deleteProduct(product: ProductEntity)
    suspend fun deleteListProductsInCategory(nameCategory: String)
    suspend fun deleteAllProducts()
}