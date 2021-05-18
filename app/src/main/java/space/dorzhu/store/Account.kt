package space.dorzhu.store

import Adapters.AccountRecycleAdapter
import Database.DBHelper
import Some_objects.DiscountCalculator
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_account.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*


class Account : Fragment() {

    private val LOG_TAG: String = "TAG"
    private var layoutManager:RecyclerView.LayoutManager?=null
    private var adapter:RecyclerView.Adapter<AccountRecycleAdapter.ViewHolder>?=null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



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

        var dayOfWeek = Date().toString().substring(0, 3)


        System.out.println("dayOfWeek = $dayOfWeek")

        val discCalculator = DiscountCalculator()
        val curDayDiscount = discCalculator.calculate(dayOfWeek)


        val tvDisount = view.findViewById<TextView>(R.id.tvDisount)
        tvDisount.setText("Ваша скидка: $curDayDiscount%")


        val btn_cheat = view.findViewById<Button>(R.id.btn_cheat)
        btn_cheat.setOnClickListener {
            context?.deleteDatabase("db")
        }

        return view

    }
}