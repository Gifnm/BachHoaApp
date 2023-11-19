package ioc.app.bachhoa.fm;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import ioc.app.bachhoa.Apdapter.ChoosePrinterAdapter;
import ioc.app.bachhoa.R;
import ioc.app.bachhoa.model.Printers;
import ioc.app.bachhoa.ultil.ItemClick;

public class PrinterBottomSheetFragment extends BottomSheetDialogFragment {
    private List<Printers> list;
    private ItemClick itemClick;
    private Context context;

    public PrinterBottomSheetFragment(Context context, List<Printers> list, ItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        ImageButton close;
        final BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.printer_bottom_sheet, null);
        bottomSheetDialog.setContentView(view);
        close = view.findViewById(R.id.pbs_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.cancel();
            }
        });
        RecyclerView listView = view.findViewById(R.id.pbs_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        listView.setLayoutManager(linearLayoutManager);

        ChoosePrinterAdapter choosePrinterAdapter = new ChoosePrinterAdapter(context, list, new ItemClick() {
            @Override
            public void clickItem(Printers printers) {
                itemClick.clickItem(printers);
                bottomSheetDialog.cancel();
            }
        });
        listView.setAdapter(choosePrinterAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        listView.addItemDecoration(dividerItemDecoration);
        return bottomSheetDialog;
    }
}
