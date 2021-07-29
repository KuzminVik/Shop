package ru.geekbrains.shopcatalog.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ViewedProductsEntity::class, CategoryEntity::class, ProductEntity::class], version = 3, exportSchema = false)
abstract class DataBase: RoomDatabase() {

    abstract fun viewedProductsDAO(): ViewedProductsDAO

    abstract fun listProductsDao(): ListProductsDao

    abstract fun listCategoryDAO(): ListCategoryDAO

}