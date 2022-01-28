package ru.geekbrains.shopcatalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.geekbrains.shopcatalog.apidata.ApiHelper
import ru.geekbrains.shopcatalog.apidata.ApiHelperImpl
import ru.geekbrains.shopcatalog.apidata.ApiService
import ru.geekbrains.shopcatalog.localdata.DatabaseHelper
import ru.geekbrains.shopcatalog.localdata.entity.FavoriteEntity
import ru.geekbrains.shopcatalog.utils.Resource
import ru.geekbrains.shopcatalog.utils.Status

private const val TAG ="FavoriteViewModel"

class FavoriteViewModel(private val apiHelper: ApiHelper = ApiHelperImpl(ApiService()),
                        private val dbHelper: DatabaseHelper
) : ViewModel() {

    private val listFavorites = MutableLiveData<Resource<List<FavoriteEntity>>>()
    fun getListFavorites(): LiveData<Resource<List<FavoriteEntity>>> {
        return listFavorites
    }

    init{
        fetchFavorites()
    }

    private fun fetchFavorites(){
        viewModelScope.launch {
            listFavorites.postValue(Resource.loading(null))
            try {
                val favorites = dbHelper.getAllFavorites()
                listFavorites.postValue(Resource.success(favorites))
            }catch (e: Exception){
                listFavorites.postValue(Resource.error("Exception private fun fetchFavorites()", null))
            }
        }
    }

    fun deleteFavoriteProduct(id: Long){
        viewModelScope.launch {
            try {
                dbHelper.deleteFavoriteEntity(id)
                val favorites = dbHelper.getAllFavorites()
                listFavorites.postValue(Resource.success(favorites))
            }catch (e: Exception){
                listFavorites.postValue(Resource.error("Exception private fun deleteFavoriteProduct()", null))
            }
        }
    }

}