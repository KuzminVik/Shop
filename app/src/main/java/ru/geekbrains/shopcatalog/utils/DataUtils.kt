package ru.geekbrains.shopcatalog.utils

import ru.geekbrains.shopcatalog.model.*

fun convertProductDtoToModel(prod: ProductDTO): List<Product> {
    return listOf(Product(prod.id!!, prod.name!!, prod.description!!, prod.salePrices!![0].value.toString()))
}

fun convertImagesDtoToModel(img: ImagesFromProductDTO): List<Image>{
    val index = img.rows!![0]
    return listOf(Image(index.meta!!.downloadHref!!, index.miniature!!.href!!, index.tiny!!.href!!))
}