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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
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
    private ArrayList<Product> cart_products = new ArrayList<Product>();
    boolean clicked = false;
    // создаем объект для данных
    ContentValues cv = new ContentValues();
    DBHelper dbHelper;
    SQLiteDatabase db;

    public CatalogGridAdapter(Context context, ArrayList<Product> data){
        this.mContext = context;
        this.data = data;
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
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate(R.layout.cardview, parent, false);
        } else {
            convertView = (View) convertView;
        }

        Product curProduct = data.get(position);

        ImageView imageView = convertView.findViewById(R.id.photoprod);
        TextView  name = convertView.findViewById(R.id.nameproducts);
        TextView price = convertView.findViewById(R.id.price);



        CheckBox btnAddToCart = convertView.findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) add2Cart(curProduct);
                else removeFromCart(curProduct.getId());
            }
        });

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


        return convertView;
    }



    private void add2Cart(Product curProduct) {
        Gson gson = new Gson();
        String inputString = gson.toJson(curProduct);

        // извлекаем значение, только не array list а Product.
        // нужно вставить этот код в фрагмент корзины
//        Type type = new TypeToken<ArrayList<String>>() {}.getType();
//
//        ArrayList<String> finalOutputString = gson.fromJson(outputarray, type);

        cv.clear();
        cv.put("json_data", inputString);
        cv.put("prod_id", curProduct.getId());

        // вставляем запись и получаем ее ID
        db.insert("in_cart", null, cv);
        Log.d(LOG_TAG, "row inserted, product id = " + curProduct.getId());
//        dbHelper.printCartInfo(db);
    }

    private void removeFromCart(String id) {
        db.execSQL("DELETE FROM in_cart WHERE prod_id = " + id);
        Log.d(LOG_TAG, "row deleted, product id = " + id);
//        dbHelper.printCartInfo(db);
    }
}
