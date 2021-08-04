package ru.geekbrains.shopcatalog.utils

import ru.geekbrains.shopcatalog.model.Image
import ru.geekbrains.shopcatalog.room.CategoryEntity
import ru.geekbrains.shopcatalog.room.ProductEntity


sealed class AppState{
    data class SuccessCategory(val categoryData: List<CategoryEntity>) : AppState()
    data class SuccessProduct(val productData: ProductEntity) : AppState()
    data class SuccessList(val productListData: List<ProductEntity>) : AppState()
    data class SuccessImage(val imageData: List<Image>) : AppState()
    data class SuccessHistory(val historyData: List<ProductEntity>) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class ErrorList(val error: Throwable) : AppState()
    data class ErrorCategory(val error: Throwable) : AppState()
    object Loading : AppState()
}

