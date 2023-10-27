package ioc.app.bachhoa.Apdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ioc.app.bachhoa.R;
import ioc.app.bachhoa.model.ProductPositioning;

public class PositionAdapter extends RecyclerView.Adapter<PositionAdapter.PositionHolder> {
    private Context context;
    private List<ProductPositioning> list;

    public PositionAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PositionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_position, parent, false);
        return new PositionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PositionHolder holder, int position) {
        ProductPositioning productPositioning = list.get(position);
        if (productPositioning == null) {
            return;
        }
        holder.shelf.setText(productPositioning.getDisplayShelves().getDisSheID() + "");
        holder.barcode.setText(productPositioning.getProduct().getProductID());
        holder.locaion.setText(productPositioning.getId() + "");
        holder.nameOfProduct.setText(productPositioning.getProduct().getProductName());
        holder.quanity.setText(productPositioning.getDisplayQuantity() + "");
        holder.form.setText(productPositioning.getForm()+"");
        Glide.with(context)
                .load(productPositioning.getProduct().getImage()) // Đường dẫn của ảnh
                .placeholder(R.drawable.ic_baseline_image_24) // Ảnh tạm thời hiển thị trong lúc đang tải (nếu cần)
                .error(R.drawable.ic_baseline_cloud_download_24) // Ảnh .clouhiển thị khi có lỗi xảy ra trong quá trình tải (nếu cần)
                .into(holder.img); // ImageView mà bạn muốn hiển thị ảnh
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(holder.getAbsoluteAdapterPosition());
                setData(list);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (this.list != null) {
            return list.size();

        }
        return 0;
    }

    public void setData(List<ProductPositioning> list) {
        this.list = list;
        notifyDataSetChanged();

    }

    public class PositionHolder extends RecyclerView.ViewHolder {
        private TextView shelf, locaion, quanity, nameOfProduct, barcode, form;
        private ImageView img;
        private Button delete;

        public PositionHolder(@NonNull View itemView) {
            super(itemView);
            form = itemView.findViewById(R.id.ip_form);
            delete = itemView.findViewById(R.id.ip_delete);
            shelf = itemView.findViewById(R.id.ip_shelf);
            locaion = itemView.findViewById(R.id.ip_locaion);
            quanity = itemView.findViewById(R.id.ip_quanity);
            nameOfProduct = itemView.findViewById(R.id.ip_name_of_product);
            barcode = itemView.findViewById(R.id.ip_barcode);
            img = itemView.findViewById(R.id.ip_img);
        }

    }
}
