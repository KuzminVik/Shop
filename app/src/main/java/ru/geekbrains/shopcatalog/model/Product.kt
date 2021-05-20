package ru.geekbrains.shopcatalog.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id : String = "d90a534c-be28-11e7-7a6c-d2a9001ad6c2",
    val shared : Boolean = true,
    val updated : String = "2017-10-31 13:47:27.986",
    val name : String = "Комплект 2в1 зимн. \"Ванкувер\" для беременных лайм",
    val description : String = "Тип: для беременных, обычная\n В комплекте: куртка, вставка на животик для беременных\n Утеплитель: Термофинн 250 гр/м (куртка), синтепон 150 гр/м (брюки)\n Капюшон: на молнии, отстегивается\n Карманы: боковые на молнии\n Кулиски по низу куртки: есть\n Длина рукава: 63 см (с митенками 68 см)\n Длина изделия по спине: 72 см\n Пояс брюк: трикотажная вставка на живот, пояс на резинке регулируется\n Длина брюк по внутреннему шву: 77 см\n Рекомендации по уходу: химчистка",
    val salePrices : Double = 850000.0) : Parcelable




