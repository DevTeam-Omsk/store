package space.dorzhu.store

import Adapters.CatalogGridAdapter
import Parsing.Parsing
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


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_shop, container, false)

        val gridView = view.findViewById<GridView>(R.id.gridview)

        doAsync {
            val parser = Parsing()
            val output = parser.parse()
            activity?.runOnUiThread{
                for (product in output) {
                    Log.d("TAG", product.printInfo())
                }
                gridView.adapter= CatalogGridAdapter(context, output)
            }
        }.execute()


        return view
    }


}

class doAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        handler()
        return null
    }
}

