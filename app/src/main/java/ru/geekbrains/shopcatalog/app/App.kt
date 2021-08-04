package ru.geekbrains.shopcatalog.app

import android.app.Application
import androidx.room.Room
import ru.geekbrains.shopcatalog.localdata.AppDatabase
import ru.geekbrains.shopcatalog.localdata.DatabaseBuilder
import ru.geekbrains.shopcatalog.localdata.dao.ListCategoryDAO
import ru.geekbrains.shopcatalog.localdata.dao.ListProductsDAO
import ru.geekbrains.shopcatalog.localdata.dao.ViewedProductsDAO

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: AppDatabase? = null
        private const val DB_NAME = "DataBase.db"

        fun getHistoryDao(): ViewedProductsDAO {
            if (db == null) {
                buildDb()
            }
            return db!!.viewedProductsDAO()
        }

        fun getListProductsDAO(): ListProductsDAO {
            if (db == null) {
                buildDb()
            }
            return db!!.listProductsDAO()
        }

        fun getListCategoryDAO(): ListCategoryDAO {
            if (db == null) {
                buildDb()
            }
            return db!!.listCategoryDAO()
        }

        private fun buildDb() {
            DatabaseBuilder.getInstance(appInstance!!.applicationContext)
        }

    }
}