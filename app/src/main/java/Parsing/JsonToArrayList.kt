package Parsing

import Database.DBHelper
import Some_objects.Product
import android.content.Context
import android.database.Cursor
import org.json.JSONObject

class JsonToArrayList(context: Context) {
    private val products = ArrayList<Product>()
    val dbHelper = DBHelper(context)
    val db = dbHelper.writableDatabase


    fun getCatalogFromDb(): ArrayList<Product> {
        val c: Cursor = db.query("catalog", null, null, null, null, null, null)

        if (c.moveToFirst()) {

            val idColJSON: Int = c.getColumnIndex("json_data")
            val prodId: Int = c.getColumnIndex("prod_id")
            do {
                val curProduct = Product()
                val json_object = JSONObject(c.getString(idColJSON))
                curProduct.id = json_object.get("id").toString()
                curProduct.name = json_object.get("name").toString()
                curProduct.price = json_object.get("price").toString()
                curProduct.img = json_object.get("img").toString()
                curProduct.description = null
                curProduct.link2detail = json_object.get("link2detail").toString()

                products.add(curProduct)
            } while (c.moveToNext())
        }
        c.close()

        return products
    }
}