package ioc.app.bachhoa.Apdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import ioc.app.bachhoa.DTOEntity.ProductOnShelf;
import ioc.app.bachhoa.R;

public class CanBePrickedAdapter extends RecyclerView.Adapter<CanBePrickedAdapter.ViewHoler> {
    private Context context;
    private List<ProductOnShelf> list;

    public CanBePrickedAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_can_be_pricked, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        ProductOnShelf productOnShelf = list.get(position);
        if (productOnShelf != null) {
            holder.barcode.setText(productOnShelf.getProductPositioning().getProduct().getProductID());
            holder.shelf.setText(productOnShelf.getNumberOnShelf() + "");
            holder.needSupplement.setText((productOnShelf.getProductPositioning().getProduct().getInventory() - productOnShelf.getNumberOnShelf()) + "");
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
        private TextView barcode, shelf, needSupplement;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            barcode = itemView.findViewById(R.id.item_cbp_barcode);
            shelf = itemView.findViewById(R.id.item_cbp_shelf);
            needSupplement = itemView.findViewById(R.id.item_cbp_need_supplements);
        }
    }
}
