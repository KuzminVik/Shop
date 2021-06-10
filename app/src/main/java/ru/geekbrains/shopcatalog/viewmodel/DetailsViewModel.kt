package ru.geekbrains.shopcatalog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import ru.geekbrains.shopcatalog.model.ProductDTO
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.shopcatalog.app.App.Companion.getHistoryDao
import ru.geekbrains.shopcatalog.model.ImagesFromProductDTO
import ru.geekbrains.shopcatalog.model.Product
import ru.geekbrains.shopcatalog.repository.*
import ru.geekbrains.shopcatalog.room.LocalRepository
import ru.geekbrains.shopcatalog.room.LocalRepositoryImpl
import ru.geekbrains.shopcatalog.utils.convertImagesDtoToModel
import ru.geekbrains.shopcatalog.utils.convertProductDtoToModel

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class DetailsViewModel(
        val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
        val imageLiveData: MutableLiveData<AppState> = MutableLiveData(),
        val historyLiveData: MutableLiveData<AppState> = MutableLiveData(),
        private val detailsRepositoryImpl: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource()),
        private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())
): ViewModel() {

    fun getProductLiveData() = detailsLiveData

    fun getProductFromRemoteSource(id: String) {
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.getProductFromServer(id, callBackProduct)
        detailsRepositoryImpl.getImagesFromServer(id, callBackImages)
    }

    fun saveHistoryProductToToDB(product: Product) {
        historyRepository.saveEntity(product)
    }

    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        historyLiveData.value = AppState.SuccessHistory(historyRepository.getAllHistoryViewed())
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
            return if (prod.id == null || prod.name == null || prod.description == null || prod.salePrices?.get(0)?.value == null) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppState.Success(convertProductDtoToModel(serverResponse))
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
            val index = img.rows?.get(0)
            return if (index!!.meta!!.downloadHref == null || index.miniature == null || index.tiny == null) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppState.SuccessImage(convertImagesDtoToModel(serverResponse))
            }
        }

    }

}
