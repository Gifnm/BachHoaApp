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
import ioc.app.bachhoa.model.DisplayShelves;

public class ShelfApdapter extends ArrayAdapter<DisplayShelves> {
List<DisplayShelves> list;
    public ShelfApdapter(@NonNull Context context, int resource, @NonNull List<DisplayShelves> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shelf_selected,parent,false);
        TextView shelf_selected = convertView.findViewById(R.id.shelf_item_selected);
        DisplayShelves displayShelves = this.getItem(position);
        if (displayShelves != null) {
            shelf_selected.setText(displayShelves.getShelfName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shelf,parent,false);
        TextView shelf = convertView.findViewById(R.id.shelf_item);
        DisplayShelves displayShelves = this.getItem(position);
        if(displayShelves != null){

            shelf.setText(displayShelves.getShelfName());
        }
        return convertView;
    }
    private void setChangeData(List<DisplayShelves> displayShelves){
        list = displayShelves;
        notifyDataSetChanged();
    }
}
