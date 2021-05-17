package space.dorzhu.store

import Adapters.CatalogGridAdapter
import Parsing.JsonToArrayList
import Some_objects.Product
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_explore.*
import java.util.*

class Explore : Fragment() {
    var products: ArrayList<Product>? = null
//    private var et: EditText? = null
    //
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        Toast.makeText(requireContext(), "Re", Toast.LENGTH_SHORT).show()



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
        val et = view?.findViewById<EditText>(R.id.et_search)
        et?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (s.toString() == "") {
                    filterCatalogFromDb(query = et?.text.toString())
                } else {
                    updateList(query = et?.text.toString())
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
//                if (s.toString() == "") {
//                    updateList(query = et?.text.toString())
//
//                } else {
//                    filterCatalogFromDb(query = et?.text.toString())
//                }
            }
        })
    }

    private fun updateList(query: String) {
        filterCatalogFromDb(query)
        list.adapter = CatalogGridAdapter(context, products)

        if (products?.size == 0){
            tvNotFound.visibility = View.VISIBLE
        }else{
            tvNotFound.visibility = View.GONE
        }
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