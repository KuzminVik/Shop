package ru.geekbrains.shopcatalog.localdata

import ru.geekbrains.shopcatalog.localdata.entity.CategoryEntity
import ru.geekbrains.shopcatalog.localdata.entity.FavoriteEntity
import ru.geekbrains.shopcatalog.localdata.entity.ProductEntity
import ru.geekbrains.shopcatalog.localdata.entity.ViewedProductsEntity

class DatabaseHelperImpl(
private val appDatabase: AppDatabase
    ) : DatabaseHelper {

    override fun getAllHistoryViewed(): List<ViewedProductsEntity> {
        val res = appDatabase.viewedProductsDAO().getAllViewedProduct()
        if(res.size>5) appDatabase.viewedProductsDAO().deleteViewedProduct(res.first())
        return res
    }

    override fun saveViewedProduct(product: ViewedProductsEntity) {
        appDatabase.viewedProductsDAO().insertViewedProduct(product)
    }

    override fun deleteViewedProduct(product: ViewedProductsEntity) {
        appDatabase.viewedProductsDAO().deleteViewedProduct(product)
    }

    override suspend fun saveListCategories(list: List<CategoryEntity>) {
        appDatabase.listCategoryDAO().insertListCategory(list)
    }

    override suspend fun getAllCategories(): List<CategoryEntity> {
        return appDatabase.listCategoryDAO().getAllCategory()
    }

    override suspend fun getOneCategory(id: String): CategoryEntity? {
        return appDatabase.listCategoryDAO().getCategoryWithId(id)
    }

    override suspend fun deleteAllCategories() {
        appDatabase.listCategoryDAO().deleteAllCategory()
    }

    override suspend fun saveListProducts(list: List<ProductEntity>) {
        appDatabase.listProductsDAO().insertListProducts(list)
    }

    override suspend fun getListProducts(nameCategory: String): List<ProductEntity> {
        return appDatabase.listProductsDAO().getListProductsWithNameCategory(nameCategory)
    }

    override fun getProduct(idProduct: String): ProductEntity {
        return appDatabase.listProductsDAO().getProductWithId(idProduct)
    }

    override fun getAllViewedProduct(list: List<String>): List<ProductEntity> {
        return appDatabase.listProductsDAO().getAllViewedProduct(list)    }

    override suspend fun saveFavoriteProduct(prod: FavoriteEntity) {
        appDatabase.viewedProductsDAO().insertFavoriteProduct(prod)
    }

    override suspend fun getAllFavorites(): List<FavoriteEntity> {
        return appDatabase.viewedProductsDAO().getAllFavorites()
    }

    override suspend fun deleteFavoriteEntity(id: Long) {
        appDatabase.viewedProductsDAO().deleteFavoriteEntity(id)
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