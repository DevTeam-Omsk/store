package Parsing

import Some_objects.Product
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Entities

class Parsing {
    val product_list = ArrayList<Product>()
    // var - потому что мб в будущем будем парсить другие категории
    var url = "https://www.citilink.ru/catalog/naushniki-s-mikrofonom/"

    val doc = Jsoup.connect(url).get()
    val html = Jsoup.parse(doc.toString())

    val settings = doc.outputSettings();



    fun parse(): ArrayList<Product> {

        val products = html.select("div.product_data__gtm-js")
        for (item in products){
            val curProduct = Product()
            var arr = JSONObject(item.attr("data-params").toString())
            curProduct.id = arr.get("id").toString()
            curProduct.name = item.select("a.ProductCardVertical__name").text()
            // у элемента ProductCardVertical__link есть ссылка на детальную карточку товара
            // нужно сохранить ее в классе или где-то еще
            // при нажатии на элемент gridView делать парсинг уже другого URL
            // и брать там описание
            //curProduct.description = "в разработке"
            curProduct.price = item.select("span.ProductCardVerticalPrice__price-current_current-price").first().text()
            curProduct.img = item.select("div.ProductCardVertical__picture-container img").attr("src")
            product_list.add(curProduct)
        }
        return product_list
    }
}