package ru.geekbrains.shopcatalog.repository

import retrofit2.Callback
import ru.geekbrains.shopcatalog.model.ImagesFromProductDTO
import ru.geekbrains.shopcatalog.model.ProductDTO

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {
    override fun getProductFromServer(id: String, callback: Callback<ProductDTO>) {
        remoteDataSource.getProductDetails(id, callback)
    }

    override fun getImagesFromServer(id: String, callback: Callback<ImagesFromProductDTO>) {
        remoteDataSource.getImagesDetails(id, callback)
    }
}