package ru.geekbrains.shopcatalog.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.shopcatalog.apidata.CategoryListDTO
import ru.geekbrains.shopcatalog.apidata.ProductListDTO
import ru.geekbrains.shopcatalog.apidata.ApiService
import ru.geekbrains.shopcatalog.apidata.ApiHelper
import ru.geekbrains.shopcatalog.apidata.ApiHelperImpl
import ru.geekbrains.shopcatalog.localdata.DatabaseHelper
import ru.geekbrains.shopcatalog.localdata.DatabaseHelperImpl
import ru.geekbrains.shopcatalog.model.Category
import ru.geekbrains.shopcatalog.utils.*

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"
private const val TAG ="MainViewModel"

class MainViewModel(
    private val apiHelperImpl: ApiHelper = ApiHelperImpl(ApiService()),
    private val dbHelper: DatabaseHelperImpl
) : ViewModel() {

    private val listProductsLiveData: MutableLiveData<AppState> = MutableLiveData()
    fun getLiveData() = listProductsLiveData

    fun getListProductsFromApi(idCategory: String){
        listProductsLiveData.value = AppState.Loading
        apiHelperImpl.getListProductsInTheCategory(idCategory, callBackListProduct)
    }

    private val callBackListProduct = object: Callback<ProductListDTO> {
        override fun onResponse(call: Call<ProductListDTO>, response: Response<ProductListDTO>) {
            val serverResponseListProductDTO: ProductListDTO? = response.body()
            listProductsLiveData.postValue(
                if (response.isSuccessful && serverResponseListProductDTO != null) {
                    if(logTurnOn) {Log.d(TAG, "!!! callBackListProduct: fun onResponse: listProductLiveData.postValue")}
                    checkResponseListProductDTO(serverResponseListProductDTO)
                } else {
                    if(logTurnOn) {Log.d(TAG, "Ответа с сервера нет или он null")}
                    AppState.ErrorList(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<ProductListDTO>, t: Throwable) {
            if(logTurnOn) {Log.d(TAG, "onFailure ${t.message}")}
            listProductsLiveData.postValue(AppState.ErrorList(Throwable(t.message ?: REQUEST_ERROR)))
        }

        private fun checkResponseListProductDTO(serverResponse: ProductListDTO): AppState {
            val prod = serverResponse.rows
            return if (prod[0].id == ""
                || prod[0].name == ""
                || prod[0].description == ""
//                || prod[0].nameCategory.pathName == null
                || prod[0].salePrices[0].value.toString() == ""
                || prod[0].stock.toString() == "") {
                if(logTurnOn) {Log.d(TAG, "serverResponse: ProductListDTO ответ с сервера неполный")}
                AppState.ErrorList(Throwable(CORRUPTED_DATA))
            } else {
                if(logTurnOn) {Log.d(TAG, "List<ProductEntity> уходит в AppState.SuccessList")}
                AppState.SuccessList(convertListProductDtoToEntity(serverResponse))
            }
        }
    }

}