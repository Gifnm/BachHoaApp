package ioc.app.bachhoa.Apdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ioc.app.bachhoa.R;
import ioc.app.bachhoa.model.ProductPositioning;

public class PositionAdapter extends RecyclerView.Adapter<PositionAdapter.PositionHolder>{
    private Context context;
    private List<ProductPositioning> list;

    public PositionAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PositionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_position,parent,false);
        return new PositionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PositionHolder holder, int position) {
ProductPositioning productPositioning = list.get(position);
if(productPositioning == null){
    return;
}
holder.shelf.setText(productPositioning.getDisplayShelves().getShelfName());
holder.barcode.setText(productPositioning.getProduct().getProductID());
holder.locaion.setText(productPositioning.getId());
holder.nameOfProduct.setText(productPositioning.getProduct().getProductName());
    }

    @Override
    public int getItemCount() {
        if(this.list != null){
            return list.size();

        }
        return 0;
    }
private void setData(List<ProductPositioning> list){
        this.list = list;
        notifyDataSetChanged();

}
    public class PositionHolder extends RecyclerView.ViewHolder {
private TextView shelf, locaion, quanity,exist,nameOfProduct,barcode;
private ImageView img;
        public PositionHolder(@NonNull View itemView) {
            super(itemView);
            shelf = itemView.findViewById(R.id.ip_shelf);
            locaion = itemView.findViewById(R.id.ip_locaion);
            quanity = itemView.findViewById(R.id.ip_quanity);
            exist = itemView.findViewById(R.id.ip_exist);
            nameOfProduct = itemView.findViewById(R.id.ip_name_of_product);
            barcode = itemView.findViewById(R.id.ip_barcode);
            img = itemView.findViewById(R.id.ip_img);
        }

    }
}
