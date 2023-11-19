package ioc.app.bachhoa.Apdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Printer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ioc.app.bachhoa.R;
import ioc.app.bachhoa.model.Printers;
import ioc.app.bachhoa.ultil.ItemClick;

public class ChoosePrinterAdapter extends RecyclerView.Adapter<ChoosePrinterAdapter.ViewHoler> {
    private List<Printers> list;
    private ItemClick imItemClick;
    private Context context;
    private SharedPreferences sharedPreferences;
    private String printerIp = "";

    public ChoosePrinterAdapter(Context context, List<Printers> list, ItemClick imItemClick) {
        this.context = context;
        this.list = list;
        this.imItemClick = imItemClick;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_printer, parent, false);
        sharedPreferences = context.getSharedPreferences("printerip", context.MODE_PRIVATE);
        printerIp = sharedPreferences.getString("printerip", "");
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        final Printers printer = list.get(position);
        if (printer != null) {
            if (printer.getIpAddress().equals(printerIp)) {
                holder.choose.setChecked(true);
            }
            holder.printerName.setText(printer.getNameOfPrinter());
            holder.choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imItemClick.clickItem(printer);
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

    class ViewHoler extends RecyclerView.ViewHolder {
        TextView printerName;
        RadioButton choose;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            printerName = itemView.findViewById(R.id.icp_printer_name);
            choose = itemView.findViewById(R.id.icp_radio_button);
        }
    }
}
