package ru.geekbrains.shopcatalog.utils

import ru.geekbrains.shopcatalog.model.Image
import ru.geekbrains.shopcatalog.localdata.entity.CategoryEntity
import ru.geekbrains.shopcatalog.localdata.entity.ProductEntity
import ru.geekbrains.shopcatalog.localdata.entity.VariantEntity
import ru.geekbrains.shopcatalog.localdata.entity.ViewedProductsEntity


sealed class AppState{
    data class SuccessCategory(val categoryData: List<CategoryEntity>) : AppState()
    data class SuccessProduct(val productData: ProductEntity) : AppState()
    data class SuccessList(val productListData: List<ProductEntity>) : AppState()
    data class SuccessHistory(val historyData: List<ProductEntity>) : AppState()
    data class SuccessVariants(val variantsData: List<VariantEntity>): AppState()
    data class SuccessStock(val stock: List<String>): AppState()
//    data class SuccessStock(val stockData: List<VariantEntity>): AppState()
    data class Error(val error: Throwable) : AppState()
    data class ErrorList(val error: Throwable) : AppState()
    data class ErrorCategory(val error: Throwable) : AppState()
    data class ErrorVariants(val error: Throwable) : AppState()
    object Loading : AppState()
}

