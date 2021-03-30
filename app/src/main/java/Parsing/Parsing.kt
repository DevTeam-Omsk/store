package Parsing

import Some_objects.Product
import android.text.Html
import android.util.Log
import org.json.JSONObject
import org.jsoup.Jsoup

class Parsing {
    private val LOG_TAG: String = "TAG"
    val product_list = ArrayList<Product>()

    fun parse(): ArrayList<Product> {
        val url = "https://www.citilink.ru/catalog/naushniki-s-mikrofonom/"

        val doc = Jsoup.connect(url).get()
        val html = Jsoup.parse(doc.toString())


        val products = html.select("div.product_data__gtm-js")
        for (item in products){
            val curProduct = Product()
            val arr = JSONObject(item.attr("data-params").toString())
            curProduct.id = arr.get("id").toString()
            curProduct.name = item.select("a.ProductCardVertical__name").text()
            curProduct.price = item.select("div.ProductCardVerticalLayout__wrapper-price span.ProductPrice__price.ProductCardVerticalPrice__price-current__price").text()
            curProduct.img = item.select("div.ProductCardVertical__picture-container img").attr("src")
            curProduct.link2detail = "https://www.citilink.ru" + item.select("a.ProductCardVertical__link").attr("href").toString()
            product_list.add(curProduct)
        }
        return product_list
    }

    fun parseDetail(link2Detail: String): Product {

        val doc = Jsoup.connect(link2Detail).get()
        val html = Jsoup.parse(doc.toString())

        val curProduct = Product()
        curProduct.img = html.select("li.PreviewList__li img").attr("src").toString()
        curProduct.name = html.select("h1.Heading.Heading_level_1").text()
        curProduct.description = html.select("div.AboutTab__description-text").text()
        curProduct.price = html.select("span.ProductHeader__price-default_current-price ").text() + Html.fromHtml(" &#x20bd")

        return curProduct
    }
}