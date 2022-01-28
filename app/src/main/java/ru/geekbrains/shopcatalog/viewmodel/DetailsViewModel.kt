package ru.geekbrains.shopcatalog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.geekbrains.shopcatalog.apidata.*
import ru.geekbrains.shopcatalog.localdata.DatabaseHelper
import ru.geekbrains.shopcatalog.localdata.entity.FavoriteEntity
import ru.geekbrains.shopcatalog.localdata.entity.ProductEntity
import ru.geekbrains.shopcatalog.localdata.entity.ViewedProductsEntity
import ru.geekbrains.shopcatalog.utils.*

class DetailsViewModel(
    private val apiHelper: ApiHelper = ApiHelperImpl(ApiService()),
    private val dbHelper: DatabaseHelper
): ViewModel() {

    private val historyLiveData: MutableLiveData<AppState> = MutableLiveData()
    fun getHistoryLD() = historyLiveData

    private val detailsLiveData: MutableLiveData<AppState> = MutableLiveData()
    fun getProductLD() = detailsLiveData

    private val variantsLiveData: MutableLiveData<AppState> = MutableLiveData()
    fun getVariantsLD() = variantsLiveData

    private val stockLiveData: MutableLiveData<AppState> = MutableLiveData()
    fun getStockLD() = stockLiveData

    init {
        fetchDetails()
    }

    //Only HistoryLD
    private fun fetchDetails(){
        viewModelScope.launch {
            historyLiveData.postValue(AppState.Loading)
            try {
                val history = dbHelper.getAllHistoryViewed()
                val historyIdList = mutableListOf<String>()
                for (el in history){
                    historyIdList.add(el.id_product)
                }
                historyLiveData.value = AppState.SuccessHistory(dbHelper.getAllViewedProduct(historyIdList))
            }catch (e: Exception){
                historyLiveData.postValue(AppState.Error(e))
            }

        }
    }

    fun getProductDetails(product: ProductEntity){
        detailsLiveData.value = AppState.Loading
        detailsLiveData.value = AppState.SuccessProduct(product)
    }

    fun saveHistoryProductToDB(product: ProductEntity) {
        dbHelper.saveViewedProduct(ViewedProductsEntity(product.id_product))
    }

    fun getVariants(id: String){
        viewModelScope.launch{
            try {
                variantsLiveData.postValue(AppState.Loading)
                val apiVariants = apiHelper.getListVariants(id)
                var resultString = ""
                apiVariants.rows.forEach {
                    resultString += "variant=https://online.moysklad.ru/api/remap/1.2/entity/variant/${it.id};"
                }
                stockLiveData.postValue(AppState.Loading)
                val resultStock = apiHelper.getVariantIsStock(resultString)
                val variants = convertVariantListDtoToEntity(apiVariants, resultStock)
                variantsLiveData.postValue(AppState.SuccessVariants(variants))
            }catch (e: Exception){
                variantsLiveData.postValue(AppState.ErrorVariants(e))
            }
        }
    }

    fun saveFavoriteProductToDB(prod: FavoriteEntity){
        viewModelScope.launch {
            dbHelper.saveFavoriteProduct(prod)
        }
    }
}
