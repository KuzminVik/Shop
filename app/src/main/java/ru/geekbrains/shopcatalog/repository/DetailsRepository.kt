package ru.geekbrains.shopcatalog.repository

import ru.geekbrains.shopcatalog.model.ImagesFromProductDTO
import ru.geekbrains.shopcatalog.model.ProductDTO

interface DetailsRepository {
    fun getProductFromServer(id: String, callback: retrofit2.Callback<ProductDTO>)
    fun getImagesFromServer(id: String, callback: retrofit2.Callback<ImagesFromProductDTO>)
}