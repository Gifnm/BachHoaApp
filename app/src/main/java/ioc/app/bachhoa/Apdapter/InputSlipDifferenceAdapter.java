package ioc.app.bachhoa.Apdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ioc.app.bachhoa.R;
import ioc.app.bachhoa.model.DetailedDeliveryNote;

public class InputSlipDifferenceAdapter extends RecyclerView.Adapter<InputSlipDifferenceAdapter.ViewHoler> {
    private Context context;
    private List<DetailedDeliveryNote> list;

    public InputSlipDifferenceAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_input_slip_difference, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        DetailedDeliveryNote deliveryNote = list.get(position);
        if (deliveryNote != null) {
            holder.numberOnList.setText("Phiếu: " + deliveryNote.getQuantity());
            holder.numberCount.setText("Kiểm: " + deliveryNote.getCount());
            holder.numberDifference.setText("Chênh lệch: " + (deliveryNote.getCount() - deliveryNote.getQuantity()));
            holder.nameOfProduct.setText(deliveryNote.getProduct().getProductName());
            holder.barcode.setText(deliveryNote.getProduct().getProductID());
            Glide.with(context).load(deliveryNote.getProduct().getImage())
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .error(R.drawable.ic_baseline_cloud_download_24)
                    .into(holder.img);

        }
    }

    @Override
    public int getItemCount() {
        if (!list.isEmpty()) {
            return this.list.size();
        }
        return 0;
    }

    public void setChangeData(List<DetailedDeliveryNote> list) {
        this.list = list;
    }

    class ViewHoler extends RecyclerView.ViewHolder {
        TextView numberOnList, numberCount, numberDifference, nameOfProduct, barcode;
        ImageView img;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            numberOnList = itemView.findViewById(R.id.iisd_number_in_list);
            numberCount = itemView.findViewById(R.id.iisd_number_count);
            numberDifference = itemView.findViewById(R.id.iisd_difference);
            nameOfProduct = itemView.findViewById(R.id.iisd_name);
            barcode = itemView.findViewById(R.id.iipsd_barcode);
            img = itemView.findViewById(R.id.iisd_img);

        }
    }
}
