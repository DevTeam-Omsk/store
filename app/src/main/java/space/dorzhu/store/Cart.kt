package space.dorzhu.store

import Adapters.CartListAdapter
import Database.DBHelper
import Some_objects.Product
import android.app.AlertDialog
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.buy_form.*
import org.json.JSONObject


@Suppress("UNREACHABLE_CODE")
class Cart : Fragment() {

    val cart_products = ArrayList<Product>()
    companion object{
        var listview : ListView? = null

    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        listview=view.findViewById(R.id.lv)
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        getJsonData(db)

        listview?.adapter = CartListAdapter(context, cart_products)

        val btn_buy = view.findViewById<Button>(R.id.btn_buy)

        btn_buy.setOnClickListener {
            showBuyForm(view)
        }
        dbHelper.printCartInfo(db)
       return view

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun showBuyForm(view: View) {
        val builder = AlertDialog.Builder(context)
        val customLayout = layoutInflater.inflate(R.layout.buy_form, null);
        builder.setView(customLayout)

        val edName = customLayout.findViewById<EditText>(R.id.edName)
        val edPhone = customLayout.findViewById<EditText>(R.id.edPhone)
        val edAddress = customLayout.findViewById<EditText>(R.id.edAddress)
        val edCreditCard = customLayout.findViewById<EditText>(R.id.edCreditCard)
        val btn_buy = customLayout.findViewById<Button>(R.id.button)

        val dialog = builder.create()
        dialog.show()

        btn_buy?.setOnClickListener {
            if (verifyOrderData(edName, edAddress, edPhone, edCreditCard)) showSuccessfulMessage(view)
            else Toast.makeText(context, "Ошибка! Проверьте правильность данных", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
    }

    private fun verifyOrderData(edName: EditText, edAddress: EditText, edPhone: EditText, edCreditCard: EditText): Boolean {
        if (edName.text.toString() != "" && edAddress.text.toString() != ""  && edPhone.text.toString() != ""  && edCreditCard.text.toString() != "" ) return true
        return false
    }

    private fun showSuccessfulMessage(view: View) {
        val builder = AlertDialog.Builder(context)
        val customLayout = layoutInflater.inflate(R.layout.order_successful, null)
        builder.setView(customLayout)

        val backToShop = customLayout.findViewById<Button>(R.id.backToShop)

        val dialog = builder.create()

        dialog.show()

        backToShop.setOnClickListener {
            dialog.dismiss()
            Navigation.findNavController(view).navigate(R.id.shop)
        }
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