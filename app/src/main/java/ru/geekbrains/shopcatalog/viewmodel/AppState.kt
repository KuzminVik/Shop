package ru.geekbrains.shopcatalog.viewmodel

import ru.geekbrains.shopcatalog.model.DeteilsEntity
import ru.geekbrains.shopcatalog.model.Product
import ru.geekbrains.shopcatalog.model.ProductDTO

sealed class AppState{
    data class Success(val productData: List<DeteilsEntity>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}

