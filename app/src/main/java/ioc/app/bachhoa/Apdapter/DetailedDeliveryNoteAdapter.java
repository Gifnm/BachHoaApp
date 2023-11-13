package ioc.app.bachhoa.Apdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ioc.app.bachhoa.R;
import ioc.app.bachhoa.model.DetailedDeliveryNote;

public class DetailedDeliveryNoteAdapter extends RecyclerView.Adapter<DetailedDeliveryNoteAdapter.ViewHolder> {
    private Context context;
    private List<DetailedDeliveryNote> list;

    public DetailedDeliveryNoteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delivery_note_detail, parent, false);

        return new ViewHolder(view);
    }

    public void setData(List<DetailedDeliveryNote> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetailedDeliveryNote deliveryNote = list.get(position);
        int indext = position;
        if (deliveryNote != null) {
            holder.nameProduct.setText(deliveryNote.getProduct().getProductName());
            holder.barcode.setText(deliveryNote.getProduct().getProductID());
            if (deliveryNote.getCount() == 0) {

            }
            Glide.with(context)
                    .load(deliveryNote.getProduct().getImage()) // Đường dẫn của ảnh
                    .placeholder(R.drawable.ic_baseline_image_24) // Ảnh tạm thời hiển thị trong lúc đang tải (nếu cần)
                    .error(R.drawable.ic_baseline_cloud_download_24) // Ảnh .clouhiển thị khi có lỗi xảy ra trong quá trình tải (nếu cần)
                    .into(holder.img); // ImageView mà bạn muốn hiển thị ảnh
            holder.reduce1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!holder.number0.getText().toString().isEmpty()) {
                        String numberS = holder.number0.getText().toString();
                        int number = Integer.parseInt(numberS);
                        if (number > 0) {
                            holder.number0.setText((--number) + "");
                            deliveryNote.setCount(number);
                        }
                    }
                }
            });
            holder.increasse1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder.number0.getText().toString().isEmpty()) {
                        holder.number0.setText(1 + "");
                        deliveryNote.setCount(1);
                    } else {
                        String numberS = holder.number0.getText().toString();
                        int number = Integer.parseInt(numberS);
                        holder.number0.setText((++number) + "");
                        deliveryNote.setCount(number);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (!list.isEmpty()) {
            return list.size();

        }
        return 0;
    }

    public List<DetailedDeliveryNote> getList() {
        return list;
    }

    public void setList(List<DetailedDeliveryNote> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView nameProduct, barcode;
        EditText number0, number10;
        ImageButton reduce1, increasse1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.idnd_img);
            nameProduct = itemView.findViewById(R.id.idnd_name_product);
            barcode = itemView.findViewById(R.id.idnd_barcode);
            number0 = itemView.findViewById(R.id.idnd_number0);
            number10 = itemView.findViewById(R.id.idnd_numberTen);
            reduce1 = itemView.findViewById(R.id.idnd_reduce1);
            increasse1 = itemView.findViewById(R.id.idnd_increase1);
        }
    }
}
