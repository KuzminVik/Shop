package ru.geekbrains.shopcatalog.utils

import ru.geekbrains.shopcatalog.model.*
import ru.geekbrains.shopcatalog.room.ViewedProductsEntity

fun convertProductDtoToModel(prod: ProductDTO): List<Product> {
    return listOf(Product(prod.id!!, prod.name!!, prod.description!!, prod.salePrices!![0].value.toString()))
}

fun convertListProductDtoToModel(listProd: ProductListDTO): List<Product>{
    val list : MutableList<Product> = mutableListOf()

    for (el in listProd.rows!!){
        var pathRows: String
        if(el.images?.rows!!.size > 0){
            pathRows = el.images.rows.get(0).meta!!.downloadHref!!
        }else{
            pathRows = "путь к картинке с надписью фото отсутствует"
        }
        val p = Product(el.id!!, el.updated!!, el.name!!,
            el.description.let { "Нет описания" },
            el.productFolder!!.pathName!!,
            pathRows,
            el.salePrices!!.get(0).value.toString(),
            el.supplier?.name.let { "Нет имени производителя" },
            el.stock.toString())
        list.add(p)
    }
    return list
}

fun convertImagesDtoToModel(img: ImagesFromProductDTO): List<Image>{
    val index = img.rows!![0]
    return listOf(Image(index.meta!!.downloadHref!!, index.miniature!!.href!!, index.tiny!!.href!!))
}

fun convertHistoryEntityToProduct(entityList: List<ViewedProductsEntity>): List<Product> {
    return entityList.map {
        Product(it.id_product, it.name, it.description, it.prise)
    }
}

fun convertProductToEntity(product: Product): ViewedProductsEntity {
    return ViewedProductsEntity(0, product.id, product.name, product.description, product.salePrices)
}