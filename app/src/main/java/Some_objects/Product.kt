package Some_objects

class Product {
    var id: String? = null
    var name: String? = null
    var price: String? = null
    var img: String? = null
    var description: String? = null

    fun printInfo(): String {
        return "\nid: ${id} | название: ${name} | цена: ${price} | описание: ${description} | изображение: ${img}\n"
    }
}