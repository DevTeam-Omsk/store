package Adapters

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import space.dorzhu.store.R

class AccountRecycleAdapter:RecyclerView.Adapter<AccountRecycleAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var itemImage:ImageView
        var itemTitle:TextView
        init {
            itemImage=itemView.findViewById(R.id.PhotoInAcc)
            itemTitle=itemView.findViewById(R.id.itemSettInAcc)
        }
    }

    private var title= arrayOf("Orders","My Detalis","Delivery Address","Payment Methods","Promo Cord","Notifecations","Help","About ")
    private var images = arrayOf(R.drawable.orders_icon,R.drawable.my_detalis,R.drawable.delicery_address,R.drawable.vector_icon,R.drawable.promo_cord_icon,R.drawable.bell_icon,R.drawable.help_icon,R.drawable.about_icon)



    override fun getItemCount(): Int {
        return title.size
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): AccountRecycleAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.acc_rec_item,
                parent,
                false
        )
        return AccountRecycleAdapter.ViewHolder(itemView)
    }
    var curSettingsItem: AccountRecycleAdapter? = null
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle?.text=title[position]
        holder.itemImage.setImageResource(images[position])

    }

}