package ioc.app.bachhoa.Apdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ioc.app.bachhoa.R;
import ioc.app.bachhoa.model.ShipmentBatch;

public class ListShipmentBacthAdapter extends RecyclerView.Adapter<ListShipmentBacthAdapter.ListShipmentBacthHolder> {
    private Context context;
    private List<ShipmentBatch> list;

    public ListShipmentBacthAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ListShipmentBacthHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sipbatch_roduct, parent, false);
        return new ListShipmentBacthHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListShipmentBacthHolder holder, int position) {
        ShipmentBatch shipmentBatch = list.get(position);
        holder.nameOfEmployee.setText(shipmentBatch.getEmployee().getEmployeeName());
        holder.timeCreate.setText(shipmentBatch.getCreateTime() + "");
        holder.status.setText(shipmentBatch.isSituation() ? "Hoàn tất" : "Đang xử lý");

    }

    @Override
    public int getItemCount() {
        if (this.list != null) {
            return list.size();

        }
        return 0;
    }

    public void setData(List<ShipmentBatch> list) {
        this.list = list;
        notifyDataSetChanged();

    }

    public class ListShipmentBacthHolder extends RecyclerView.ViewHolder {
        private TextView nameOfEmployee, status, timeCreate;
        private Button viewShipmentBacth;

        public ListShipmentBacthHolder(@NonNull View itemView) {
            super(itemView);
            nameOfEmployee = itemView.findViewById(R.id.lsb_name_of_employee);
            status = itemView.findViewById(R.id.lsb_status);
            timeCreate= itemView.findViewById(R.id.lsb_time_create);
            viewShipmentBacth= itemView.findViewById(R.id.lsb_btn_view);
        }
    }
}
