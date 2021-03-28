package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Some_objects.Product;
import space.dorzhu.store.R;

public class CatalogGridAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Product> data;
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
            //LayoutInflater inflater = getLayoutInflater();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate(R.layout.cardview, parent, false);
        } else {
            convertView = (View) convertView;
        }

        ImageView imageView = convertView.findViewById(R.id.photoprod);
        TextView  name = convertView.findViewById(R.id.nameproducts);
//        TextView desc= convertView.findViewById(R.id.description);
        TextView price = convertView.findViewById(R.id.price);

        Product curProduct = data.get(position);

//        imageView.setImageResource(curProduct.getImg());
        Picasso.get().load(curProduct.getImg()).into(imageView);
        name.setText(curProduct.getName());
        price.setText(curProduct.getPrice());
        return convertView;
    }


}
