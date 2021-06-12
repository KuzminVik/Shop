package ru.geekbrains.shopcatalog.viewmodel

import ru.geekbrains.shopcatalog.model.Image
import ru.geekbrains.shopcatalog.model.Product


sealed class AppState{
    data class Success(val productData: List<Product>) : AppState()
    data class SuccessImage(val imageData: List<Image>) : AppState()
    data class SuccessHistory(val historyData: List<Product>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}

