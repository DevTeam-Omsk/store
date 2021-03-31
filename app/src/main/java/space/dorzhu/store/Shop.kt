package space.dorzhu.store

import Adapters.CatalogGridAdapter
import Database.DBHelper
import Parsing.Parsing
import Some_objects.Product
import Some_objects.doAsync
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_shop.*


@Suppress("UNREACHABLE_CODE")
class Shop : Fragment() {
    private val LOG_TAG: String = "TAG"
    var gridView: GridView? = null
    var products_list : ArrayList<Product>? = null
    var gridAdapter : CatalogGridAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_shop, container, false)

        context?.deleteDatabase("db")

        // создаем объект для создания и управления версиями БД
        val dbHelper = DBHelper(context)

        // подключаемся к БД
        val db = dbHelper.writableDatabase
        Log.d(LOG_TAG, "Fragment shop")
        dbHelper.printCartInfo(db)
        // закрываем подключение к БД
        dbHelper.close()



        val gridView = view.findViewById<GridView>(R.id.gridview)

        doAsync {
            val parser = Parsing()
            try {
                products_list = parser.parse()
            }catch (e: Exception){
                Log.d(LOG_TAG, e.message.toString())
                return@doAsync
            }

            activity?.runOnUiThread{
                try {
                    gridAdapter = CatalogGridAdapter(context, products_list)
                    gridView?.adapter= gridAdapter
                }catch (e: Exception){
                    Log.d(LOG_TAG, e.toString())
                    return@runOnUiThread
                }

                disableProgressBar()
            }
        }.execute()
        return view
    }

    override fun onResume() {
        super.onResume()
        gridAdapter?.notifyDataSetChanged()
    }

    private fun disableProgressBar() {
        main_wrapper.removeView(bar_wrapper)
    }

}

