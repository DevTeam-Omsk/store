package space.dorzhu.store

import Adapters.CartListAdapter
import Parsing.Parsing
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView


@Suppress("UNREACHABLE_CODE")
class Card : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card, container, false)

        val listview = view?.findViewById<ListView>(R.id.lv)

        doAsync{
            val parser = Parsing()
            val output = parser.parse()
            activity?.runOnUiThread{
                for (product in output) {
                    Log.d("TAG", product.printInfo())
                }
                listview?.adapter= CartListAdapter(context, output)
            }
        }.execute()

       return view

    }

    class doAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            handler()
            return null
        }
    }
}