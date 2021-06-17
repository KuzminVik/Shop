package ru.geekbrains.shopcatalog.app

import android.app.Application
import androidx.room.Room
import ru.geekbrains.shopcatalog.room.DataBase
import ru.geekbrains.shopcatalog.room.ViewedRroductsDAO

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: DataBase? = null
        private const val DB_NAME = "DataBase.db"

        fun getHistoryDao(): ViewedRroductsDAO {
            if (db == null) {
                synchronized(DataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            DataBase::class.java,
                            DB_NAME)
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return db!!.viewedProductsDAO()
        }
    }

}