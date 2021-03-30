package Adapters;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Database.DBHelper;
import Some_objects.Product;
import space.dorzhu.store.ProductDetail;
import space.dorzhu.store.R;

public class CatalogGridAdapter extends BaseAdapter {
    private static final String LOG_TAG = "TAG";
    private Context mContext;
    private ArrayList<Product> data;
    boolean clicked = false;
    // создаем объект для данных
    ContentValues cv = new ContentValues();

    public CatalogGridAdapter(Context context, ArrayList<Product> data){
        this.mContext = context;
        this.data = data;
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
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate(R.layout.cardview, parent, false);
        } else {
            convertView = (View) convertView;
        }


        ImageView imageView = convertView.findViewById(R.id.photoprod);
        TextView  name = convertView.findViewById(R.id.nameproducts);
        TextView price = convertView.findViewById(R.id.price);



        ImageButton btnAddToCart = convertView.findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if (!clicked){
                    add2Cart();
                    btnAddToCart.setImageDrawable(mContext.getResources().getDrawable(R.drawable.btn_added));
                    clicked = true;
                }else {
                    btnAddToCart.setImageDrawable(mContext.getResources().getDrawable(R.drawable.button));
                    clicked = false;
                }
            }
        });

        Product curProduct = data.get(position);

        LinearLayout product_wrapper = convertView.findViewById(R.id.product_wrapper);
        product_wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetail.class);
                intent.putExtra("link2Detail", curProduct.getLink2detail());
                mContext.startActivity(intent);
            }
        });



        Picasso.get().load(curProduct.getImg()).placeholder(R.drawable.product_placeholder).into(imageView);
        name.setText(curProduct.getName());
        price.setText(curProduct.getPrice());

        cv.put("prod_id", curProduct.getId());

        return convertView;
    }

    private void add2Cart() {
        // создаем объект для создания и управления версиями БД
        DBHelper dbHelper = new DBHelper(mContext);

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // вставляем запись и получаем ее ID
        long rowID = db.insert("in_cart", null, cv);
        Log.d(LOG_TAG, "row inserted, ID = " + rowID);
        // закрываем подключение к БД
        dbHelper.close();

    }


}
