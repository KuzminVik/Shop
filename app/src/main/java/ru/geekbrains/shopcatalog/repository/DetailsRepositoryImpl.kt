package ru.geekbrains.shopcatalog.repository

import retrofit2.Callback
import ru.geekbrains.shopcatalog.apidata.ApiService
import ru.geekbrains.shopcatalog.apidata.ImagesFromProductDTO
import ru.geekbrains.shopcatalog.apidata.ProductDTO

class DetailsRepositoryImpl(private val apiService: ApiService) : DetailsRepository {

//    override fun getProductFromServer(id: String, callback: Callback<ProductDTO>) {
//        apiService.getProductDetails(id, callback)
//    }
//
//    override fun getImagesFromServer(id: String, callback: Callback<ImagesFromProductDTO>) {
//        apiService.getImagesDetails(id, callback)
//    }

}