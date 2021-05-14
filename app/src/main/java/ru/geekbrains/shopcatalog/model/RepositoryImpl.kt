package ru.geekbrains.shopcatalog.model

class RepositoryImpl : Repository {

    override fun getProductFromServer(): List<Product> {
        val listProduct : MutableList<Product> = ArrayList()
        return listProduct
    }

    override fun getProductFromLocalStorage(): List<Product> {
        val listProduct : MutableList<Product> = ArrayList()
        for (i in 0..10){
            listProduct.add(Product(
                    "d90a534c-be28-11e7-7a6c-d2a9001ad6c2",
                    true,
                    "2017-10-31 13:47:27.986",
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
                    850000.0))
        }
        return listProduct
    }
}