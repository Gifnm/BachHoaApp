package ioc.app.bachhoa.Apdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ioc.app.bachhoa.DTOEntity.ProductOnShelf;
import ioc.app.bachhoa.R;

public class OutOfExistenceAdapter extends RecyclerView.Adapter<OutOfExistenceAdapter.ViewHoler> {
    private Context context;
    private List<ProductOnShelf> list;

    public OutOfExistenceAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_out_of_existence, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        ProductOnShelf productOnShelf = list.get(position);
        if (productOnShelf != null) {
            holder.shelf.setText(productOnShelf.getNumberOnShelf() + "");
            holder.barcode.setText(productOnShelf.getProductPositioning().getProduct().getProductID());
            holder.systemExist.setText(productOnShelf.getProductPositioning().getProduct().getInventory() + "");

        }
    }

    @Override
    public int getItemCount() {

        if (!list.isEmpty()) {
            return list.size();
        }
        return 0;
    }

    public void setChangeData(List<ProductOnShelf> list) {
        this.list = list;
        notifyDataSetChanged();

    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        private TextView barcode, shelf, systemExist;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            barcode = itemView.findViewById(R.id.item_ooe_barcode);
            shelf = itemView.findViewById(R.id.item_ooe_shelf);
            systemExist = itemView.findViewById(R.id.item_ooe_system_exsits);
        }
    }
}
