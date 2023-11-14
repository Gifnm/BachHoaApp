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

import java.util.List;

import ioc.app.bachhoa.R;
import ioc.app.bachhoa.model.Printers;

public class PrinterAdapter extends RecyclerView.Adapter<PrinterAdapter.ViewHoler> {
    private Context context;
    private List<Printers> list;

    public PrinterAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_printer, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        Printers printer = list.get(position);
        if (printer != null) {
            holder.nameOfPrinter.setText(printer.getNameOfPrinter());
            holder.ipAddress.setText("IP: "+printer.getIpAddress());
            holder.pageSize.setText("Khổ giấy: "+printer.getPageSize() +"mm");
            holder.removePrinter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void setChangeData(List<Printers> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHoler extends RecyclerView.ViewHolder {
        private TextView nameOfPrinter, pageSize, ipAddress;
        private Button removePrinter;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            nameOfPrinter = itemView.findViewById(R.id.iop_printer_name);
            pageSize = itemView.findViewById(R.id.iop_page_size);
            ipAddress = itemView.findViewById(R.id.iop_ip_address);
            removePrinter = itemView.findViewById(R.id.iop_remove);
        }
    }
}
