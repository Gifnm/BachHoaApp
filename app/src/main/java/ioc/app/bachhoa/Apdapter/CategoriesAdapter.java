package ioc.app.bachhoa.Apdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ioc.app.bachhoa.R;
import ioc.app.bachhoa.model.Categories;

public class CategoriesAdapter extends ArrayAdapter<Categories> {

    public CategoriesAdapter(@NonNull Context context, int resource, @NonNull List<Categories> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories2_selected, parent, false);
        TextView category_Selected = convertView.findViewById(R.id.ctg_item_selected);
        Categories categories = this.getItem(position);
        if (categories != null) {
            category_Selected.setText(categories.getCategoriesName());

        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false);
        TextView category = convertView.findViewById(R.id.ctg_item);
        Categories categories = this.getItem(position);
        if (categories != null) {
            category.setText(categories.getCategoriesName());

        }
        return convertView;
    }
}
