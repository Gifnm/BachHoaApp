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
import ioc.app.bachhoa.ultil.PrintPriceTag;

public class PostionViewAdapter extends RecyclerView.Adapter<PostionViewAdapter.PositionViewHolder> {
    // Khai bao list -> Danh sach vi tri
    private Context context;
    private List<ProductPositioning> list;

    public PostionViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PositionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_position_view, parent, false);
        return new PositionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PositionViewHolder holder, int position) {
        ProductPositioning proPos = list.get(position);
        if (proPos == null) {
            return;
        }

        holder.shelf.setText("Kệ: " + proPos.getDisplayShelves().getDisSheID());
        holder.location.setText("Vị trí: " + proPos.getId());
        holder.quantity.setText("SL: " + proPos.getDisplayQuantity());
        holder.inventory.setText("Tồn: " + proPos.getProduct().getInventory());
        holder.nameOfProduct.setText(proPos.getProduct().getProductName());
        holder.barcode.setText(proPos.getProduct().getProductID());
        holder.print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintPriceTag printPriceTag = new PrintPriceTag(context);
                printPriceTag.printOnetag(printPriceTag.generateOnePriceTag(proPos));
            }
        });

//        if(context != null){
//        Glide.with(context)
//                .load(proPos.getProduct().getImage()) // Đường dẫn của ảnh
//                .placeholder(R.drawable.ic_baseline_image_24) // Ảnh tạm thời hiển thị trong lúc đang tải (nếu cần)
//                .error(R.drawable.ic_baseline_cloud_download_24) // Ảnh .clouhiển thị khi có lỗi xảy ra trong quá trình tải (nếu cần)
//                .into(holder.img); }// ImageView mà bạn muốn hiển thị ảnh
    }

    @Override
    public int getItemCount() {
        if (this.list != null) {
            return list.size();

        }
        return 0;
    }

    public void setData(List<ProductPositioning> list1) {
        this.list = list1;
        notifyDataSetChanged();

    }

    public class PositionViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các biến ánh xạ trên ItemViewProduct
        TextView shelf, location, quantity, inventory, nameOfProduct, barcode;
        Button print;
        ImageView img;

        public PositionViewHolder(@NonNull View itemView) {
            super(itemView);
            shelf = itemView.findViewById(R.id.ivp_shelf);
            location = itemView.findViewById(R.id.ivp_location);
            quantity = itemView.findViewById(R.id.ivp_quantity);
            inventory = itemView.findViewById(R.id.ivp_inventory);
            nameOfProduct = itemView.findViewById(R.id.ivp_name);
            barcode = itemView.findViewById(R.id.ivp_barcode);
            img = (ImageView) itemView.findViewById(R.id.ip_img);
            print = itemView.findViewById(R.id.ivp_print);
        }
    }
}
