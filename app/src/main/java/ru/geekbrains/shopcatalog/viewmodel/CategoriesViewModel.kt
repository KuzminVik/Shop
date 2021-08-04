package ru.geekbrains.shopcatalog.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.geekbrains.shopcatalog.apidata.ApiHelper
import ru.geekbrains.shopcatalog.apidata.ApiHelperImpl
import ru.geekbrains.shopcatalog.apidata.ApiService
import ru.geekbrains.shopcatalog.localdata.DatabaseHelper
import ru.geekbrains.shopcatalog.room.CategoryEntity
import ru.geekbrains.shopcatalog.utils.Resource
import ru.geekbrains.shopcatalog.utils.convertListCategoryDtoToModel
import ru.geekbrains.shopcatalog.utils.logTurnOn
import ru.geekbrains.shopcatalog.utils.toast

private const val TAG ="CategoriesViewModel"

class CategoriesViewModel(
    private val apiHelper: ApiHelper = ApiHelperImpl(ApiService()),
    private val dbHelper: DatabaseHelper
) : ViewModel() {

    private val listCategory = MutableLiveData<Resource<List<CategoryEntity>>>()
    fun detListCategory(): LiveData<Resource<List<CategoryEntity>>>{
        return listCategory
    }

    init{
        fetchCategory()
    }

    private fun fetchCategory(){
        viewModelScope.launch{
            listCategory.postValue(Resource.loading(null))
            try{
                val categoryFromDb = dbHelper.getAllCategory()
                if(categoryFromDb.isEmpty()){
                    val categoryFromApi = apiHelper.getListCategory().rows
                    if(logTurnOn) {
                        Log.d(TAG, "Категории пришли из апи ${categoryFromApi.size}")}
                    dbHelper.saveListCategory(convertListCategoryDtoToModel(categoryFromApi))
                    listCategory.postValue(Resource.success(convertListCategoryDtoToModel(categoryFromApi)))
                }else{
                    if(logTurnOn) {
                        Log.d(TAG, "Категории пришли из db ${categoryFromDb.size}")}
                    listCategory.postValue(Resource.success(categoryFromDb))
                }
            }catch (e: Exception){
                listCategory.postValue(Resource.error("Exception private fun fetchCategory()", null))
            }
        }


    }


}