package ru.geekbrains.shopcatalog.localdata

import ru.geekbrains.shopcatalog.model.Product
import ru.geekbrains.shopcatalog.room.CategoryEntity
import ru.geekbrains.shopcatalog.room.ProductEntity
import ru.geekbrains.shopcatalog.utils.convertHistoryEntityToProduct
import ru.geekbrains.shopcatalog.utils.convertProductEntityToViewedProductsEntity

class DatabaseHelperImpl(
private val appDatabase: AppDatabase
    ) : DatabaseHelper {

    override fun getAllHistoryViewed(): List<ProductEntity> {
        val res = appDatabase.viewedProductsDAO().getAllViewedProduct()
        if (res.size>6) appDatabase.viewedProductsDAO().deleteViewedProduct(res.first())
        return convertHistoryEntityToProduct(res)
    }

    override fun saveViewedProduct(product: ProductEntity) {
        appDatabase.viewedProductsDAO().insertViewedProduct(convertProductEntityToViewedProductsEntity(product))
    }

    override fun deleteViewedProduct(product: ProductEntity) {
        appDatabase.viewedProductsDAO().deleteViewedProduct(convertProductEntityToViewedProductsEntity(product))
    }

    override suspend fun saveListCategory(list: List<CategoryEntity>) {
        appDatabase.listCategoryDAO().insertListCategory(list)
    }

    override suspend fun getAllCategory(): List<CategoryEntity> {
        return appDatabase.listCategoryDAO().getAllCategory()
    }

    override suspend fun deleteAllCategory() {
        appDatabase.listCategoryDAO().deleteAllCategory()
    }

    override suspend fun saveListProduct(list: List<ProductEntity>) {
        appDatabase.listProductsDAO().insertListProducts(list)
    }

    override suspend fun getListProduct(nameCategory: String): List<Product> {
        return appDatabase.listProductsDAO().getProductsInCategoryWithName(nameCategory)
    }

    override suspend fun getProduct(idProduct: String): Product {
        return appDatabase.listProductsDAO().getProductWithId(idProduct)
    }

    override suspend fun deleteProduct(product: ProductEntity) {
        appDatabase.listProductsDAO().deleteProduct(product)
    }

    override suspend fun deleteListProductsInCategory(nameCategory: String) {
        appDatabase.listProductsDAO().deleteListProductsInCategory(nameCategory)
    }

    override suspend fun deleteAllProducts() {
        appDatabase.listProductsDAO().deleteAllProducts()
    }


}