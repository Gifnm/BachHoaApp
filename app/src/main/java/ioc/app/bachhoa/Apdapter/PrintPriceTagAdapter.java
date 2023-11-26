package ioc.app.bachhoa.Apdapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import ioc.app.bachhoa.DTOEntity.PriceTag;
import ioc.app.bachhoa.R;

public class PrintPriceTagAdapter extends RecyclerView.Adapter<PrintPriceTagAdapter.ViewHolder> {
    // Khai báo biến môi trường
    private Context context;
    private List<PriceTag> list;
    private DecimalFormat decimalFormat = new DecimalFormat("#,###");

    public PrintPriceTagAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_price_tag, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PriceTag pricetag = list.get(position);
        if (pricetag != null) {
            holder.nameOfProduct.setText(pricetag.getProductPositioning().getProduct().getProductName());
            if (pricetag.getDiscountDetails() != null) {
                String price = String.valueOf(pricetag.getProductPositioning().getProduct().getPrice());
                if (pricetag.getDiscountDetails().getDisID().equals("2t1")) {
                    holder.price.setText(pricetag.getProductPositioning().getProduct().getPrice() + "");
                    holder.priceSale.setText("Mua 2 tặng 1");
                } else {
                    // Tạo một đối tượng SpannableString với nội dung bạn muốn
                    SpannableString spannableString = new SpannableString(price);
                    // Áp dụng StrikethroughSpan để tạo hiệu ứng gạch ngang
                    spannableString.setSpan(new StrikethroughSpan(), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    holder.price.setText(decimalFormat.format(pricetag.getProductPositioning().getProduct().getPrice()) + "VND");
                    holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.priceSale.setText(decimalFormat.format(pricetag.getProductPositioning().getProduct().getPrice() * ((100 - Double.parseDouble(pricetag.getDiscountDetails().getDisID())) / 100)) + " VND");
                }

            } else {
                holder.price.setText(decimalFormat.format(pricetag.getProductPositioning().getProduct().getPrice()) +"VND");
            }


        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    // Cập nhật nội danh cho Adapter
    public void setChangeData(List<PriceTag> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameOfProduct, price, priceSale;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ với item
            nameOfProduct = itemView.findViewById(R.id.ipt_name_product);
            price = itemView.findViewById(R.id.ipt_price);
            btnDelete = itemView.findViewById(R.id.ipt_imb_delete);
            priceSale = itemView.findViewById(R.id.ipt_price_sale);
        }
    }
}
