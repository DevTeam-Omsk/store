package space.dorzhu.store

import Adapters.CatalogGridAdapter
import android.os.Bundle
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
        gridView.adapter= CatalogGridAdapter()
        return view
    }


}

