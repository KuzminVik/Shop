package ru.geekbrains.shopcatalog.viewmodel

import ru.geekbrains.shopcatalog.model.Product

sealed class AppState{
    data class Success(val productData: Product) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}

