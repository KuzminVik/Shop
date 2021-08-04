package ru.geekbrains.shopcatalog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.geekbrains.shopcatalog.apidata.ApiHelper
import ru.geekbrains.shopcatalog.localdata.DatabaseHelperImpl

class ViewModelFactory(private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelperImpl) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(CategoriesViewModel::class.java)) {
            return CategoriesViewModel(apiHelper, dbHelper) as T
        }
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(apiHelper, dbHelper) as T
        }
        if(modelClass.isAssignableFrom(DetailsViewModel::class.java)){
            return DetailsViewModel(apiHelper, dbHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}