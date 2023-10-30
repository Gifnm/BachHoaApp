package ioc.app.bachhoa.Apdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ioc.app.bachhoa.R;
import ioc.app.bachhoa.model.BillDetail;
import ioc.app.bachhoa.model.ProductPositioning;

public class ShellProductAdapter extends RecyclerView.Adapter<ShellProductAdapter.BillDetailHolder> {
    private Context context;
    private List<BillDetail> list;

    public ShellProductAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BillDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_sell, parent, false);
        return new BillDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillDetailHolder holder, int position) {
        BillDetail billDetail = list.get(position);
        if (billDetail == null) {
            return;
        }
        holder.finalPrice.setText("Thành tiền: "+billDetail.getTotalAmount());
        holder.price.setText(billDetail.getProduct().getPrice() + "");
        holder.nameOfProduct.setText(billDetail.getProduct().getProductName());
        holder.quantity.setText(billDetail.getQuantity()+"");
    }

    @Override
    public int getItemCount() {
        if (this.list != null) {
            return list.size();

        }
        return 0;
    }

    public void setData(List<BillDetail> list) {
        this.list = list;
        notifyDataSetChanged();

    }

    public class BillDetailHolder extends RecyclerView.ViewHolder {
        private TextView nameOfProduct, price, finalPrice;
        private EditText quantity;

        public BillDetailHolder(@NonNull View itemView) {
            super(itemView);
            nameOfProduct = itemView.findViewById(R.id.sell_name);
            quantity = (EditText) itemView.findViewById(R.id.sell_quantity_editText);
            price = itemView.findViewById(R.id.sell_price);
            finalPrice = itemView.findViewById(R.id.final_price);
        }

    }
}
