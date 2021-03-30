package space.dorzhu.store

import Adapters.CatalogGridAdapter
import Database.DBHelper
import Parsing.Parsing
import Some_objects.Product
import Some_objects.doAsync
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment


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

        context?.deleteDatabase("myDB")

        val gridView = view.findViewById<GridView>(R.id.gridview)

        doAsync {
            val parser = Parsing()
            try {
                products_list = parser.parse()
            }catch (e: Exception){
                Log.d(LOG_TAG, e.message.toString())
            }

            activity?.runOnUiThread{
                gridAdapter = CatalogGridAdapter(context, products_list)
                gridView?.adapter= gridAdapter
            }
        }.execute()
        return view
    }

    override fun onResume() {
        super.onResume()
        gridAdapter?.notifyDataSetChanged()
    }

}

