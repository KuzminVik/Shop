package ru.geekbrains.shopcatalog.localdata

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.geekbrains.shopcatalog.localdata.dao.ListCategoryDAO
import ru.geekbrains.shopcatalog.localdata.dao.ListProductsDAO
import ru.geekbrains.shopcatalog.localdata.dao.ViewedProductsDAO
import ru.geekbrains.shopcatalog.localdata.entity.ViewedProductsEntity
import ru.geekbrains.shopcatalog.room.CategoryEntity
import ru.geekbrains.shopcatalog.room.ProductEntity

@Database(entities = [ViewedProductsEntity::class, CategoryEntity::class, ProductEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun viewedProductsDAO(): ViewedProductsDAO

    abstract fun listProductsDAO(): ListProductsDAO

    abstract fun listCategoryDAO(): ListCategoryDAO

}