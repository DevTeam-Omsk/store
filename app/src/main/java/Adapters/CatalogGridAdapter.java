package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import space.dorzhu.store.R;

public class CatalogGridAdapter extends BaseAdapter {
    private Context mContext;
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;

        if (convertView == null) {
            grid = new View(mContext);
            //LayoutInflater inflater = getLayoutInflater();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            grid = inflater.inflate(R.layout.cardview, parent, false);
        } else {
            grid = (View) convertView;
        }

        ImageView imageView = grid.findViewById(R.id.imageButton);
        TextView  name = grid.findViewById(R.id.nameproducts);
        TextView desc=grid.findViewById(R.id.description);
        TextView price = grid.findViewById(R.id.price);
        imageView.setImageResource(mThumbIds[position]);
        name.setText("Картинка "+String.valueOf(position));
        return grid;
    }

    public Integer[] mThumbIds={R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground};
}
