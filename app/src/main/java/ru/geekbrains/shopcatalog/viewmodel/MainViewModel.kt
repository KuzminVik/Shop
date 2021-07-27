package ru.geekbrains.shopcatalog.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.shopcatalog.model.ProductListDTO
import ru.geekbrains.shopcatalog.repository.RemoteDataSource
import ru.geekbrains.shopcatalog.repository.Repository
import ru.geekbrains.shopcatalog.repository.RepositoryImpl
import ru.geekbrains.shopcatalog.utils.convertListProductDtoToModel
import ru.geekbrains.shopcatalog.utils.logTurnOn

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"
private const val TAG ="MainViewModel"

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    val listProductLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl(RemoteDataSource())
) : ViewModel() {

    fun getLiveData() = listProductLiveData

//    fun getProduct() {
//        liveDataToObserve.value = AppState.Loading
//        liveDataToObserve.postValue(AppState.Success(
//            repositoryImpl.getProductsFromLocalStorage()
//        ))
//    }

    fun getListProductsFromRemoteSource(id: String){
        listProductLiveData.value = AppState.Loading
        repositoryImpl.getListProductsInTheCategory(id, callBackListProduct)
    }

    private val callBackListProduct = object: Callback<ProductListDTO> {
        override fun onResponse(call: Call<ProductListDTO>, response: Response<ProductListDTO>) {
            val serverResponseListProductDTO: ProductListDTO? = response.body()
            listProductLiveData.postValue(
                if (response.isSuccessful && serverResponseListProductDTO != null) {
                    if(logTurnOn) {Log.d(TAG, "!!!!!!!!!!!!!!! callBackListProduct: fun onResponse: listProductLiveData.postValue")}
                    checkResponseListProductDTO(serverResponseListProductDTO)
                } else {
                    if(logTurnOn) {Log.d(TAG, "????????????????? ответа с сервера нет или он нулл")}
                    AppState.ErrorList(Throwable(SERVER_ERROR))

                }
            )
        }

        override fun onFailure(call: Call<ProductListDTO>, t: Throwable) {
            if(logTurnOn) {Log.d(TAG, "??????????????? onFailure ${t.message}")}
            listProductLiveData.postValue(AppState.ErrorList(Throwable(t.message ?: REQUEST_ERROR)))
        }

        private fun checkResponseListProductDTO(serverResponse: ProductListDTO): AppState {
            val prod = serverResponse.rows
            return if (prod!![0].id == null
                || prod[0].name == null
                || prod[0].description == null
                || prod[0].productFolder?.pathName == null
                || prod[0].salePrices?.get(0)?.value == null
                || prod[0].stock == null) {
                if(logTurnOn) {Log.d(TAG, "????????????????? ответа с сервера неполный")}
                AppState.ErrorList(Throwable(CORRUPTED_DATA))
            } else {
                if(logTurnOn) {Log.d(TAG, "!!!!!!!!!!!!!!! List<Product> уходит в AppState.SuccessList")}
                AppState.SuccessList(convertListProductDtoToModel(serverResponse))
            }
        }
    }
}