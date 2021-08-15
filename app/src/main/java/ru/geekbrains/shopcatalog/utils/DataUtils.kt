package ru.geekbrains.shopcatalog.utils

import ru.geekbrains.shopcatalog.apidata.*
import ru.geekbrains.shopcatalog.model.*
import ru.geekbrains.shopcatalog.localdata.entity.CategoryEntity
import ru.geekbrains.shopcatalog.localdata.entity.ProductEntity

fun convertProductDtoToEntity(el: ProductDTO): ProductEntity {
//    val stopper = el.productFolder.name ?: "???"
    return ProductEntity(
        el.id,
        el.name,
        el.description,
        el.productFolder.name ?: "???",
        if(el.images.rows == null){"error el.images.rows == null"}else{
            el.images.rows[0].meta.downloadHref},
        if(el.images.rows == null){"error el.images.rows == null"}else{
            el.images.rows[0].miniature.href},
        if(el.images.rows == null){"error el.images.rows == null"}else{
            el.images.rows[0].tiny.href},
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
                if(el.images.rows?.isEmpty()){"error el.images.rows == null"}else{
                    el.images.rows[0].meta.downloadHref},
                if(el.images.rows == null && el.images.rows.isEmpty()){"error el.images.rows == null"}else{
                    el.images.rows[0].miniature.href},
                if(el.images.rows == null && el.images.rows.isEmpty()){"error el.images.rows == null"}else{
                    el.images.rows[0].tiny.href},
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