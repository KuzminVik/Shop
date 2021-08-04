package ru.geekbrains.shopcatalog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.shopcatalog.apidata.*
import ru.geekbrains.shopcatalog.model.Product
import ru.geekbrains.shopcatalog.repository.*
import ru.geekbrains.shopcatalog.localdata.DatabaseHelper
import ru.geekbrains.shopcatalog.localdata.DatabaseHelperImpl
import ru.geekbrains.shopcatalog.room.ProductEntity
import ru.geekbrains.shopcatalog.utils.*

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class DetailsViewModel(
    private val apiHelper: ApiHelper = ApiHelperImpl(ApiService()),
    private val dbHelper: DatabaseHelper
): ViewModel() {

    val imageLiveData: MutableLiveData<AppState> = MutableLiveData()
    val historyLiveData: MutableLiveData<AppState> = MutableLiveData()
    val detailsLiveData: MutableLiveData<AppState> = MutableLiveData()
    fun getProductLiveData() = detailsLiveData

    fun getProductFromApi(id: String) {
        detailsLiveData.value = AppState.Loading
        apiHelper.getProductFromServer(id, callBackProduct)
        apiHelper.getImagesFromServer(id, callBackImages)
    }

    fun saveHistoryProductToToDB(product: ProductEntity) {
        dbHelper.saveViewedProduct(product)
    }

    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        historyLiveData.value = AppState.SuccessHistory(dbHelper.getAllHistoryViewed())
    }

    private val callBackProduct = object:  Callback<ProductDTO> {
        override fun onResponse(call: Call<ProductDTO>, response: Response<ProductDTO>) {
            val serverResponseProductDTO: ProductDTO? = response.body()
            detailsLiveData.postValue(
                    if (response.isSuccessful && serverResponseProductDTO != null) {
                        checkResponseProductDTO(serverResponseProductDTO)
                    } else {
                        AppState.Error(Throwable(SERVER_ERROR))
                    }
            )
        }
        override fun onFailure(call: Call<ProductDTO>, t: Throwable) {
            detailsLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

        private fun checkResponseProductDTO(serverResponse: ProductDTO): AppState {
            val prod = serverResponse
            return if (prod.id == "" || prod.name == "" || prod.description == "" || prod.salePrices[0].value.toString() == "") {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppState.SuccessProduct(convertProductDtoToEntity(serverResponse))
            }
        }
    }

    private val callBackImages = object:  Callback<ImagesFromProductDTO>{
        override fun onResponse(call: Call<ImagesFromProductDTO>, response: Response<ImagesFromProductDTO>) {
            val serverResponseImages: ImagesFromProductDTO? = response.body()
            imageLiveData.postValue(
                if (response.isSuccessful && serverResponseImages != null) {
                    checkResponseImages(serverResponseImages)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<ImagesFromProductDTO>, t: Throwable) {
            imageLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

        private fun checkResponseImages(serverResponse: ImagesFromProductDTO): AppState {
            val img = serverResponse
            val index = img.rows[0]
            return if (index.meta.downloadHref == "" || index.miniature.href == "" || index.tiny.href == "") {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppState.SuccessImage(convertImagesDtoToModel(serverResponse))
            }
        }

    }
}
