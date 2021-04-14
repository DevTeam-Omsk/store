    package Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;


import Database.DBHelper;
import Some_objects.Product;
import space.dorzhu.store.R;

public class CartListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Product> data;
    ArrayAdapter <String>adapter;
    DBHelper dbHelper;
    int count=1;
    SQLiteDatabase db;
    public CartListAdapter(Context context, ArrayList<Product>data){
        this.mContext=context;
        this.data=data;
        dbHelper = new DBHelper(mContext);
        db = dbHelper.getWritableDatabase();

    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new View(mContext);
            //LayoutInflater inflater = getLayoutInflater();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        } else {
            convertView = (View) convertView;
        }

        ListView listView= convertView.findViewById(R.id.lv);
        ImageView plus  = convertView.findViewById(R.id.plus);
        ImageView minus = convertView.findViewById(R.id.minus);
        ImageView remove = convertView.findViewById(R.id.remove);
        TextView txtcount = convertView.findViewById(R.id.count);
        ImageView imageView = convertView.findViewById(R.id.photoInCard);
        TextView nameInCart = convertView.findViewById(R.id.nameInCard);
        TextView price = convertView.findViewById(R.id.priceInCard);


        Product curProduct = data.get(position);
        Picasso.get().load(curProduct.getImg()).into(imageView);
        nameInCart.setText(curProduct.getName());
        price.setText(curProduct.getPrice());


        String self =curProduct.getPrice();
        String res= self.replaceAll("[₽ ]","");

        final int[] kol = {Integer.parseInt(res)};
        final int []cena={Integer.parseInt(res)};
       plus.setOnClickListener(new View.OnClickListener() {
           @SuppressLint("SetTextI18n")
           @Override
           public void onClick(View v) {
                count++;
               kol[0] += cena[0];
               price.setText(kol[0] +" ₽");
               txtcount.setText(count+"");
               System.out.println(kol[0]);
           }
       });
       minus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if(kol[0]>cena[0]){
                   count--;
                   kol[0]-=cena[0];
                   price.setText(kol[0]+" ₽");
                   txtcount.setText(count+"");
               }
           }
       });

       remove.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
//               db.execSQL("DELETE FROM in_cart WHERE prod_id = " + curProduct.getId());
//               db.delete("in_cart","id = "+curProduct.getId(),null);
               db.delete("in_cart","id = "+curProduct.getId(), null);
               Toast.makeText(mContext,"КУ",Toast.LENGTH_SHORT).show();
           }
       });

     return convertView;
    }


}
