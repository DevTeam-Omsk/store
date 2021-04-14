package Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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
    private final Context mContext;
    private final ArrayList<Product> data;
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
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate(R.layout.cardview, parent, false);
        }

        Product curProduct = data.get(position);

        ImageView imageView = convertView.findViewById(R.id.photoprod);
        TextView  name = convertView.findViewById(R.id.nameproducts);
        TextView price = convertView.findViewById(R.id.price);



        CheckBox btnAddToCart = convertView.findViewById(R.id.btnAddToCart);
        btnAddToCart.setChecked(checkIsCartProduct(curProduct));


        btnAddToCart.setOnClickListener(v -> {
            if (btnAddToCart.isChecked()) add2Cart(curProduct);
            else removeFromCart(curProduct);
        });

        LinearLayout product_wrapper = convertView.findViewById(R.id.product_wrapper);
        product_wrapper.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ProductDetail.class);
            intent.putExtra("link2Detail", curProduct.getLink2detail());
            mContext.startActivity(intent);
        });


        Picasso.get().load(curProduct.getImg()).placeholder(R.drawable.product_placeholder).into(imageView);
        name.setText(curProduct.getName());
        price.setText(curProduct.getPrice());


        return convertView;
    }

    private boolean checkIsCartProduct(Product curProduct) {
        String query = "SELECT * FROM in_cart WHERE prod_id = " + curProduct.getId();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        } else return true;
    }


    public void add2Cart(Product curProduct) {
        Gson gson = new Gson();
        String inputString = gson.toJson(curProduct);

        cv.clear();
        cv.put("json_data", inputString);
        cv.put("prod_id", curProduct.getId());


        // вставляем запись и получаем ее ID
        db.insert("in_cart", null, cv);
        Log.d(LOG_TAG, "row inserted, product id = " + curProduct.getId());
        dbHelper.printCartInfo(db);
    }

    public void removeFromCart(Product curProduct) {
        db.execSQL("DELETE FROM in_cart WHERE prod_id = " + curProduct.getId());
        Log.d(LOG_TAG, "row deleted, product id = " + curProduct.getId());
        dbHelper.printCartInfo(db);
    }
}
