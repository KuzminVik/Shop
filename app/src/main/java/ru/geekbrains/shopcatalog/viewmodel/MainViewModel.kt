package ru.geekbrains.shopcatalog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.shopcatalog.repository.Repository
import ru.geekbrains.shopcatalog.repository.RepositoryImpl

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
)
    : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getProduct() {
        liveDataToObserve.value = AppState.Loading
        liveDataToObserve.postValue(AppState.Success(
            repositoryImpl.getProductsFromLocalStorage()
        ))
    }
}