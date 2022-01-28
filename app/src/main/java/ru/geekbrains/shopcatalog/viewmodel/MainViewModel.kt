package ru.geekbrains.shopcatalog.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.geekbrains.shopcatalog.apidata.ApiService
import ru.geekbrains.shopcatalog.apidata.ApiHelper
import ru.geekbrains.shopcatalog.apidata.ApiHelperImpl
import ru.geekbrains.shopcatalog.localdata.DatabaseHelper
import ru.geekbrains.shopcatalog.localdata.DatabaseHelperImpl
import ru.geekbrains.shopcatalog.utils.*

private const val DATA_ERROR = "Ошибка получения данных"
private const val TAG ="MainViewModel"
private const val PATH_PRODUCT_FOLDER = "productFolder=https://online.moysklad.ru/api/remap/1.2/entity/productfolder/"
private const val PATH_STOCK_STORE = "stockStore=https://online.moysklad.ru/api/remap/1.2/entity/store/b8f4a028-c124-11e2-b125-001b21d91495;"

class MainViewModel(
    private val apiHelper: ApiHelper = ApiHelperImpl(ApiService()),
    private val dbHelper: DatabaseHelper
) : ViewModel() {

    private val listProductsLiveData: MutableLiveData<AppState> = MutableLiveData()
    fun getLiveData(): MutableLiveData<AppState> = listProductsLiveData


    fun getMainListProducts(id: String){
        viewModelScope.launch {
            listProductsLiveData.postValue(AppState.Loading)
            try {
                val category = dbHelper.getOneCategory(id)
                if(loging) {
                    Log.d(TAG, "ВНИМАНИЕ ОТЛАДКА: dbHelper.getOneCategory(id) ${category.toString()} category.name = ${category?.name ?: "null"}")}
                val listProductsFromDb = dbHelper.getListProducts(category?.name ?: "null")

                if(listProductsFromDb.isEmpty()){
                    if(loging) {
                        Log.d(TAG, "ВНИМАНИЕ ОТЛАДКА: listProductsFromDb.isEmpty()")}
                    val productsFromApi = convertListProductDtoToEntity(apiHelper.getListProducts("$PATH_PRODUCT_FOLDER$id"))
                    listProductsLiveData.postValue(AppState.SuccessList(productsFromApi))
                    dbHelper.saveListProducts(productsFromApi)
                }else{
                    if(loging) {
                    Log.d(TAG, "ВНИМАНИЕ ОТЛАДКА: listProductsFromDb не пустой и уходит в SuccessList")}
                    listProductsLiveData.postValue(AppState.SuccessList(listProductsFromDb))
                    val productsFromApi = convertListProductDtoToEntity(apiHelper.getListProducts("$PATH_PRODUCT_FOLDER$id"))
                    dbHelper.deleteListProductsInCategory(id)
                    dbHelper.saveListProducts(productsFromApi)
                }
            }catch (e: Exception){
                AppState.ErrorList(Throwable(DATA_ERROR))
            }
        }
    }

}