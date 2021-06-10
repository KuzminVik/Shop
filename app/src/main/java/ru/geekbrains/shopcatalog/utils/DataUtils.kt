package ru.geekbrains.shopcatalog.utils

import ru.geekbrains.shopcatalog.model.*
import ru.geekbrains.shopcatalog.room.ViewedRroductsEntity

fun convertProductDtoToModel(prod: ProductDTO): List<Product> {
    return listOf(Product(prod.id!!, prod.name!!, prod.description!!, prod.salePrices!![0].value.toString()))
}

fun convertImagesDtoToModel(img: ImagesFromProductDTO): List<Image>{
    val index = img.rows!![0]
    return listOf(Image(index.meta!!.downloadHref!!, index.miniature!!.href!!, index.tiny!!.href!!))
}

fun convertHistoryEntityToProduct(entityList: List<ViewedRroductsEntity>): List<Product> {
    return entityList.map {
        Product(it.id_product, it.name, it.description, it.prise)
    }
}

fun convertProductToEntity(product: Product): ViewedRroductsEntity {
    return ViewedRroductsEntity(0, product.id, product.name, product.description, product.salePrices)
}