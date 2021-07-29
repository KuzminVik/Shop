package ru.geekbrains.shopcatalog.app

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteDatabase.deleteDatabase
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteCompat.Api16Impl.deleteDatabase
import ru.geekbrains.shopcatalog.room.DataBase
import ru.geekbrains.shopcatalog.room.ListCategoryDAO
import ru.geekbrains.shopcatalog.room.ListProductsDao
import ru.geekbrains.shopcatalog.room.ViewedProductsDAO

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: DataBase? = null
        private const val DB_NAME = "DataBase.db"

        fun getHistoryDao(): ViewedProductsDAO {
            if (db == null) {
                buildDb()
            }
            return db!!.viewedProductsDAO()
        }

        fun getListProductsDAO(): ListProductsDao {
            if (db == null) {
                buildDb()
            }
            return db!!.listProductsDao()
        }

        fun getListCategoryDAO(): ListCategoryDAO {
            if (db == null) {
                buildDb()
            }
            return db!!.listCategoryDAO()
        }

        private fun buildDb() {
            synchronized(DataBase::class.java) {
                if (App.db == null) {
                    if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                    db = Room.databaseBuilder(
                        appInstance!!.applicationContext,
                        DataBase::class.java,
                        DB_NAME
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
        }

    }
}