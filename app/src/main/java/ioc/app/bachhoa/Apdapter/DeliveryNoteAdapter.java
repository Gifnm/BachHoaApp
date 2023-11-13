package ioc.app.bachhoa.Apdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ioc.app.bachhoa.DetailDeliveryNoteActivity;
import ioc.app.bachhoa.R;
import ioc.app.bachhoa.model.DeliveryNote;
import ioc.app.bachhoa.model.DetailedDeliveryNote;

public class DeliveryNoteAdapter extends RecyclerView.Adapter<DeliveryNoteAdapter.ViewHolder> {
    private Context context;
    private List<DeliveryNote> list;

    public DeliveryNoteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delivery_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DeliveryNote deliveryNote = list.get(position);
        if (deliveryNote != null) {
            holder.deliveryID.setText(deliveryNote.getId());
            holder.nameOfUserCreate.setText(deliveryNote.getEmployee().getEmployeeName());
            holder.timeCreate.setText(deliveryNote.getTimeCreate() + "");
            holder.btnCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailDeliveryNoteActivity.class);
                    intent.putExtra("deliveryNoteId",deliveryNote.getId());
                    context.startActivity(intent);
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

    public void setData(List<DeliveryNote> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView deliveryID, nameOfUserCreate, timeCreate;
        private Button btnCheck;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deliveryID = itemView.findViewById(R.id.idn_delivery_note_id);
            nameOfUserCreate = itemView.findViewById(R.id.idn_user_name_create);
            timeCreate = itemView.findViewById(R.id.idn_time_create);
            btnCheck = itemView.findViewById(R.id.idn_btn_check);
        }
    }
}
