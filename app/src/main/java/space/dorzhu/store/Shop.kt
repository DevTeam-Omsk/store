package space.dorzhu.store

import Adapters.CatalogGridAdapter
import Database.DBHelper
import Parsing.JsonToArrayList
import Parsing.Parsing
import Some_objects.Product
import Some_objects.doAsync
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.GridView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.gson.Gson

// 1. Скачать каталог через парсинг ( have_catalog = false )
// 2. Сохранить каталог в бд ( have_catalog = true )
// 3. При последующем входе извлекать каталог изи бд
//
// Как сохранить каталог в бд?
// 1. Перебрать Array list из товаров, которые прилетели с парсинга
// 2. НА каждой итерации из этих товаров формировать json из этого объекта
// 3. Сохранить json в бд и id товара тоже
//
// Как изменять картинку в случае если товар уже добавлен в корзину, но мы перезапустили app?
// 1. Добавить в класс продукта столбец "В корзине?"
// 2. И в адаптере проверять, если поле true, то меняем свойство checked у кнопки "добавить в корзину"


@Suppress("UNREACHABLE_CODE")
class Shop : Fragment() {
    private val LOG_TAG: String = "TAG"

    private var gridView: GridView? = null

    private var main_wrapper : FrameLayout? = null
    private var bar_wrapper : LinearLayout? = null
    private var gridAdapter : CatalogGridAdapter? = null

    private var gson: Gson? = null

    private var dialog: AlertDialog? = null


    @SuppressLint("Recycle")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shop, container, false)

        gridView = view.findViewById(R.id.gridview)
        main_wrapper = view.findViewById(R.id.main_wrapper)
        bar_wrapper = view.findViewById(R.id.bar_wrapper)

        dialog = initErrorDialog()


        /*Если в бд нет каталога*/
        /*то парсим и сохраняем товары в бд*/
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase
        val c = db.query("catalog", null, null, null, null, null, null)

        return view
    }


    override fun onStart() {
        super.onStart()
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        gridAdapter?.notifyDataSetChanged()

        val products = JsonToArrayList(requireContext()).getCatalogFromDb()

        if (products.size != 0){
            gridView?.adapter = CatalogGridAdapter(context, products)
            disableProgressBar()
        } else parseFromInternet()
    }

    /*Парсинг каталога с сайта ситилинк*/
    private fun parseFromInternet() {
        doAsync {
            val parser = Parsing()
            try {
                val products = parser.parse()
                saveIntoDb(products)

                activity?.runOnUiThread {
                    disableProgressBar()
                    gridAdapter = CatalogGridAdapter(context, products)
                    gridView?.adapter= gridAdapter
                }

            }catch (e: Exception){
                activity?.runOnUiThread { dialog?.show() }
                Log.d(LOG_TAG, "Ошибка в блоке doAsync: ${e.stackTrace} ${e.message} ${e.cause}")
                return@doAsync
            }
        }.execute()
    }

    /*Формирую JSON из array list чтобы сохранить его в бд*/
    private fun saveIntoDb(products: ArrayList<Product>) {
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase
        products.forEach {
            val cv = ContentValues()
            gson = Gson()
            val string =  gson?.toJson(it)
            cv.put("json_data", string)
            db.insert("catalog", null, cv)
        }
        dbHelper.printCatalogInfo(db)
        db.close()
    }

    private fun disableProgressBar() {
        main_wrapper?.removeView(bar_wrapper)
    }

    private fun initErrorDialog(): AlertDialog? {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Произошла ошибка, проверьте подключение к Интернету и перезапустите приложение")
        builder.setPositiveButton("Ок") { _, _ -> requireActivity().finish() }
        dialog = builder.create()
        return dialog
    }
}

