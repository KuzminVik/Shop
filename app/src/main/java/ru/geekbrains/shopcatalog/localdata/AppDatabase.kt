package ru.geekbrains.shopcatalog.localdata

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.geekbrains.shopcatalog.localdata.dao.ListCategoryDAO
import ru.geekbrains.shopcatalog.localdata.dao.ListProductsDAO
import ru.geekbrains.shopcatalog.localdata.dao.ViewedProductsDAO
import ru.geekbrains.shopcatalog.localdata.entity.CategoryEntity
import ru.geekbrains.shopcatalog.localdata.entity.FavoriteEntity
import ru.geekbrains.shopcatalog.localdata.entity.ProductEntity
import ru.geekbrains.shopcatalog.localdata.entity.ViewedProductsEntity

@Database(entities = [CategoryEntity::class, ProductEntity::class, ViewedProductsEntity::class, FavoriteEntity::class], version = 5, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun viewedProductsDAO(): ViewedProductsDAO

    abstract fun listProductsDAO(): ListProductsDAO

    abstract fun listCategoryDAO(): ListCategoryDAO

}