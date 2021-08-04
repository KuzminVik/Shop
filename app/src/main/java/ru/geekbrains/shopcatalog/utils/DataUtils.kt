package ru.geekbrains.shopcatalog.utils

import android.util.Log
import ru.geekbrains.shopcatalog.apidata.*
import ru.geekbrains.shopcatalog.model.*
import ru.geekbrains.shopcatalog.localdata.entity.ViewedProductsEntity
import ru.geekbrains.shopcatalog.room.CategoryEntity
import ru.geekbrains.shopcatalog.room.ProductEntity

fun convertProductDtoToEntity(el: ProductDTO): ProductEntity{
//    val stopper = el.productFolder.name ?: "???"
    return ProductEntity(
        el.id,
        el.name,
        el.description,
        el.productFolder.name ?: "???",
        if(el.images.rows.size >= 1){el.images.rows[0].meta.downloadHref}
            else{"error images.rows.size == 0"},
        if(el.images.rows.size >= 1){el.images.rows[0].miniature.href}
            else{ "error images.rows.size == 0"},
        if(el.images.rows.size >= 1){el.images.rows[0].tiny.href}
            else{ "error images.rows.size == 0"},
        el.supplier?.name ?: "???",
        el.salePrices[0].value.toString(),
        el.stock.toString()
    )
}

fun convertListProductDtoToEntity(listProd: ProductListDTO): List<ProductEntity>{
    val list : MutableList<ProductEntity> = mutableListOf()
        for(el in listProd.rows){
            val product = ProductEntity(
                el.id,
                el.name,
                el.description,
                el.productFolder.name ?: "???",
                if(el.images.rows.size >= 1){el.images.rows[0].meta.downloadHref}
                else{"error images.rows.size == 0"},
                if(el.images.rows.size >= 1){el.images.rows[0].miniature.href}
                else{ "error images.rows.size == 0"},
                if(el.images.rows.size >= 1){el.images.rows[0].tiny.href}
                else{ "error images.rows.size == 0"},
                el.supplier?.name ?: "???",
                el.salePrices[0].value.toString(),
                el.stock.toString()
            )
            list.add(product)
        }
    return list
}

fun convertImagesDtoToModel(img: ImagesFromProductDTO): List<Image>{
    val index = img.rows.get(0)
    return listOf(Image(index.meta.downloadHref, index.miniature.href, index.tiny.href))
}

fun convertHistoryEntityToProduct(entityList: List<ViewedProductsEntity>): List<ProductEntity> {
    return entityList.map {
        ProductEntity(it.id_product, it.name, it.description, " ", "", "", "", "", it.prise, "")
    }
}

fun convertProductEntityToViewedProductsEntity(pr: ProductEntity): ViewedProductsEntity{
    return ViewedProductsEntity(pr.id_product, pr.name!!, pr.description!!, pr.prise!!)
}

fun convertListCategoryDtoToModel(listCat: List<ProductFolderDTO>): List<CategoryEntity>{
    val listModel : MutableList<CategoryEntity> = mutableListOf()
for(el in listCat){
//        val stopperName = if(el.name == null){"???"}else{el.name}
//        val stopperPath = if(el.pathName == null){"???"}else{el.pathName}
        val category = CategoryEntity(
            el.id, el.name, el.pathName
        )
        listModel.add(category)
    }
    return listModel
}