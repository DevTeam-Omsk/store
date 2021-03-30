package Some_objects

import java.io.Serializable

class Product : Serializable {
    var id: String? = null
    var name: String? = null
    var price: String? = null
    var img: String? = null
    var description: String? = null
    var link2detail: String? = null

    fun printInfo(): String {
        return "\nid: ${id} | название: ${name} | цена: ${price} | описание: ${description} | изображение: ${img} | ссылка на детальную карточку: ${link2detail}\n"
    }
}