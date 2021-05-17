package space.dorzhu.store

import Adapters.AccountRecycleAdapter
import Database.DBHelper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class Account : Fragment() {

    private val LOG_TAG: String = "TAG"
    private var layoutManager:RecyclerView.LayoutManager?=null
    private var adapter:RecyclerView.Adapter<AccountRecycleAdapter.ViewHolder>?=null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        context?.deleteDatabase("db")

        // создаем объект для создания и управления версиями БД
        val dbHelper = DBHelper(context)

        // подключаемся к БД
        val db = dbHelper.writableDatabase
        Log.d(LOG_TAG, "Fragment shop")
        dbHelper.printCartInfo(db)
        // закрываем подключение к БД
        dbHelper.close()

        var view= inflater.inflate(R.layout.fragment_account, container, false)

        val rwAccountList =view.findViewById<RecyclerView>(R.id.rwAccountList)

        layoutManager=LinearLayoutManager(requireContext())
        rwAccountList.layoutManager=layoutManager
        adapter=AccountRecycleAdapter()
        rwAccountList.adapter=adapter
        return view

    }



}