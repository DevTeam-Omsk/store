package space.dorzhu.store

import Adapters.AccountRecycleAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Account : Fragment() {

    private var layoutManager:RecyclerView.LayoutManager?=null
    private var adapter:RecyclerView.Adapter<AccountRecycleAdapter.ViewHolder>?=null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        var view= inflater.inflate(R.layout.fragment_account, container, false)

        val rwAccountList =view.findViewById<RecyclerView>(R.id.rwAccountList)

       // layoutManager = LinearLayoutManager(requireContext())
        layoutManager=LinearLayoutManager(requireContext())
        rwAccountList.layoutManager=layoutManager
        adapter=AccountRecycleAdapter()
        rwAccountList.adapter=adapter
//        val dividerItemDecoration: DividerItemDecoration = DividerItemDecoration(rwAccountList.getContext(),
//                (layoutManager as LinearLayoutManager).getOrientation())
//        rwAccountList.addItemDecoration(dividerItemDecoration)



        return view

    }



}