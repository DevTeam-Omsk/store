package space.dorzhu.store

import Adapters.CatalogGridAdapter
import Parsing.JsonToArrayList
import Some_objects.Product
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_explore.*
import java.util.*
import kotlin.collections.ArrayList

class Explore : Fragment() {
    var products: ArrayList<Product>? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onResume() {
        super.onResume()
        btn_search.setOnClickListener {
            val query = et_search.text.toString()

            updateList(query)
        }
        btn_delete_all.setOnClickListener {
            et_search.setText("")

            val query = et_search.text.toString()
            updateList(query)
        }
    }

    private fun updateList(query: String) {
        filterCatalogFromDb(query)
        list.adapter = CatalogGridAdapter(context, products)
    }

    private fun filterCatalogFromDb(query: String) {
        products = JsonToArrayList(requireContext()).getCatalogFromDb()

        val iterator = products?.iterator()

        while (iterator!!.hasNext()){
            val element: Product = iterator.next()
            if (!element.name!!.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))) {
                iterator.remove()
            }
        }
    }
}