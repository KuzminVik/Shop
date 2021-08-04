package ru.geekbrains.shopcatalog.localdata

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private const val DB_NAME = "DataBase.db"
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DB_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

}