package ru.geekbrains.shopcatalog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.shopcatalog.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getProduct() = getDataFromLocalSource()

    /* метод getDataFromLocalSource, который имитирует запрос к БД или ещё какому-то источнику данных в приложении.
   Запрос осуществляется асинхронно в отдельном потоке. */
    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(2000)
            liveDataToObserve.postValue(AppState.Success(RepositoryImpl().getProductFromLocalStorage()))
        }.start()
    }
}