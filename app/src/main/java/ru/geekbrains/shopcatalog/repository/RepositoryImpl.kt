package ru.geekbrains.shopcatalog.repository

import retrofit2.Callback
import ru.geekbrains.shopcatalog.model.Product
import ru.geekbrains.shopcatalog.model.ProductListDTO

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository {

    override fun getListProductsInTheCategory(id: String, callback: Callback<ProductListDTO>) {
        remoteDataSource.getListProductsInTheCategory(id, callback)
    }

    override fun getProductsFromLocalStorage(): MutableList<Product> {
        val listProduct : MutableList<Product> = ArrayList()
        for (i in 0..10){
            listProduct.add(
                Product(
                    "d90a534c-be28-11e7-7a6c-d2a9001ad6c2",
                    "Комплект 2в1 зимн. \"Ванкувер\" для беременных лайм",
                    "Тип: для беременных, обычная" +
                            "\n В комплекте: куртка, вставка на животик для беременных" +
                            "\n Утеплитель: Термофинн 250 гр/м (куртка), синтепон 150 гр/м (брюки)" +
                            "\n Капюшон: на молнии, отстегивается" +
                            "\n Карманы: боковые на молнии" +
                            "\n Кулиски по низу куртки: есть" +
                            "\n Длина рукава: 63 см (с митенками 68 см)" +
                            "\n Длина изделия по спине: 72 см" +
                            "\n Пояс брюк: трикотажная вставка на живот, пояс на резинке регулируется" +
                            "\n Длина брюк по внутреннему шву: 77 см" +
                            "\n Рекомендации по уходу: химчистка",
                        "8500"
                )
            )
        }
        return listProduct
    }

    override fun getNewProductsFromLocalStorage(): List<Product> {
        val listProduct : MutableList<Product> = ArrayList()
        for (i in 0..10){
            listProduct.add(
                    Product(
                            "d90a534c-be28-11e7-7a6c-d2a9001ad6c2",
                            "Комплект 2в1 зимн. \"Ванкувер\" для беременных лайм",
                            "Тип: для беременных, обычная" +
                                    "\n В комплекте: куртка, вставка на животик для беременных" +
                                    "\n Утеплитель: Термофинн 250 гр/м (куртка), синтепон 150 гр/м (брюки)" +
                                    "\n Капюшон: на молнии, отстегивается" +
                                    "\n Карманы: боковые на молнии" +
                                    "\n Кулиски по низу куртки: есть" +
                                    "\n Длина рукава: 63 см (с митенками 68 см)" +
                                    "\n Длина изделия по спине: 72 см" +
                                    "\n Пояс брюк: трикотажная вставка на живот, пояс на резинке регулируется" +
                                    "\n Длина брюк по внутреннему шву: 77 см" +
                                    "\n Рекомендации по уходу: химчистка",
                            "8500"
                    )
            )
        }
        return listProduct
    }
}