package ru.geekbrains.shopcatalog.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ViewedProductsEntity::class), version = 2, exportSchema = false)
abstract class DataBase: RoomDatabase() {
    abstract fun viewedProductsDAO(): ViewedRroductsDAO
}