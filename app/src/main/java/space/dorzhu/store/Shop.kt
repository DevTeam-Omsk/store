package space.dorzhu.store

import Adapters.CatalogGridAdapter
import Database.DBHelper
import Parsing.Parsing
import Some_objects.Product
import Some_objects.doAsync
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
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

        val gridView = view.findViewById<GridView>(R.id.gridview)

        val builder = AlertDialog.Builder(context)
        builder.setMessage("Произошла ошибка, проверьте подключение к Интернету и перезапустите приложение")
        builder.setPositiveButton("Ок", DialogInterface.OnClickListener { dialog, id -> requireActivity().finish() })
        val dialog: AlertDialog = builder.create()

        doAsync {
            val parser = Parsing()
            try {
                products_list = parser.parse()
            }catch (e: Exception){
                activity?.runOnUiThread { dialog.show() }
                Log.d(LOG_TAG, "Ошибка в блоке doAsync: ${e.stackTrace} ${e.message} ${e.cause}")
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

