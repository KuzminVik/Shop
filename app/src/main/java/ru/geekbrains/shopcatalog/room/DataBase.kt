package ru.geekbrains.shopcatalog.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ViewedRroductsEntity::class), version = 1, exportSchema = false)
abstract class DataBase: RoomDatabase() {
    abstract fun viewedProductsDAO(): ViewedRroductsDAO
}