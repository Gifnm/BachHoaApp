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

public class ResultScanHolder extends RecyclerView.Adapter<ResultScanHolder.viewHoler> {
    private List<ProductOnShelf> list;
    private Context context;

    public ResultScanHolder(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public viewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result_scan, parent, false);
        return new viewHoler(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHoler holder, int position) {
        ProductOnShelf productOnShelf = list.get(position);
        if (productOnShelf != null) {
            holder.productID.setText(productOnShelf.getProductPositioning().getProduct().getProductName());
            holder.numberOnShelf.setText(productOnShelf.getNumberOnShelf()+"");
        }
    }

    @Override
    public int getItemCount() {
        if (!list.isEmpty()) {
            return list.size();

        }
        return 0;
    }

    public void setData(List<ProductOnShelf> list) {
        this.list = list;
    }

    public class viewHoler extends RecyclerView.ViewHolder {
        private TextView productID, numberOnShelf;

        public viewHoler(@NonNull View itemView) {
            super(itemView);
            productID = itemView.findViewById(R.id.irs_producID);
            numberOnShelf = itemView.findViewById(R.id.irs_number_onShelf);
        }
    }
}
