package ioc.app.bachhoa.Apdapter;

import android.content.Context;
import android.util.Printer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ioc.app.bachhoa.R;
import ioc.app.bachhoa.model.Printers;

public class ChoosePrinterAdapter extends RecyclerView.Adapter<ChoosePrinterAdapter.ViewHoler> {
    private Context context;
    private List<Printers> list;

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_printer, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        Printers printer = list.get(position);
        if (printer != null) {
            holder.printerName.setText(printer.getNameOfPrinter());
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
            printerName = itemView.findViewById(R.id.icp_radio_button);
        }
    }
}
