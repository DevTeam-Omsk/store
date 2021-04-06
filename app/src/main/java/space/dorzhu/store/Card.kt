package space.dorzhu.store

import Adapters.CartListAdapter
import Database.DBHelper
import Some_objects.Product
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import org.json.JSONObject


@Suppress("UNREACHABLE_CODE")
class Card : Fragment() {

    val cart_products = ArrayList<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card, container, false)

        val listview = view?.findViewById<ListView>(R.id.lv)

        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        getJsonData(db)

        listview?.adapter = CartListAdapter(context, cart_products)

       return view

    }

    private fun getJsonData(db: SQLiteDatabase) {
        // делаем запрос всех данных из таблицы in_cart, получаем Cursor
        val c: Cursor = db.query("in_cart", null, null, null, null, null, null)

        if (c.moveToFirst()) {

            val jsonData: Int = c.getColumnIndex("json_data")
            val prodId: Int = c.getColumnIndex("prod_id")
            do {
                val product = Product()

                val json_product = c.getString(jsonData).toString()
                val jsonObject = JSONObject(json_product)

                product.id =  jsonObject.get("id").toString()
                product.name =  jsonObject.get("name").toString()
                product.img =  jsonObject.get("img").toString()
                product.price =  jsonObject.get("price").toString()
                cart_products.add(product)
            } while (c.moveToNext())
        }
        c.close()
    }


}