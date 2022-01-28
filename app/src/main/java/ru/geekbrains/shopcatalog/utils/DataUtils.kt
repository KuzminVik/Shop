package ru.geekbrains.shopcatalog.utils

import ru.geekbrains.shopcatalog.apidata.*
import ru.geekbrains.shopcatalog.localdata.entity.CategoryEntity
import ru.geekbrains.shopcatalog.localdata.entity.ProductEntity
import ru.geekbrains.shopcatalog.localdata.entity.VariantEntity

fun convertListProductDtoToEntity(listProd: ProductsListDTO): List<ProductEntity> {
    val list: MutableList<ProductEntity> = mutableListOf()
    for (el in listProd.rows) {
//        val rubStock = el.stock/10000
        val product = ProductEntity(
            el.id,
            el.name,
            el.description,
            el.productFolder.name ?: "???",
            el.images.rows[0].meta.downloadHref,
            el.images.rows[0].miniature.href,
            el.images.rows[0].tiny.href,
            el.supplier?.name ?: "???",
            el.salePrices[0].value.toInt()/100,
            el.stock.toString()
        )
        list.add(product)
    }
    return list
}

fun convertListCategoryDtoToModel(listCat: List<ProductFolderDTO>): List<CategoryEntity> {
    val listModel: MutableList<CategoryEntity> = mutableListOf()
    for (el in listCat) {
        val category = CategoryEntity(
            el.id, el.name, el.pathName
        )
        listModel.add(category)
    }
    return listModel
}

fun convertVariantListDtoToEntity(listVariants: VariantsListDTO, resultStock: ListVariantIsStockDTO): List<VariantEntity>{
    val list: MutableList<VariantEntity> = mutableListOf()
    var stock: String = "0"
    for(el in listVariants.rows){
        resultStock.rows.forEach {
            if(el.externalCode == it.externalCode){
                stock = it.stock
                return@forEach
            }
        }
        val entity = VariantEntity(
            el.id, el.characteristics[0].value, stock)
        list.add(entity)
    }
    return list
}