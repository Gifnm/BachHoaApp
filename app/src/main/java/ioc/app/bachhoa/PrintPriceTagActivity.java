package ioc.app.bachhoa;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Printer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ioc.app.bachhoa.Apdapter.PrintPriceTagAdapter;
import ioc.app.bachhoa.DTOEntity.PriceTag;
import ioc.app.bachhoa.api.PrinterAPI;
import ioc.app.bachhoa.api.ProductPositionAPI;
import ioc.app.bachhoa.fm.PrinterBottomSheetFragment;
import ioc.app.bachhoa.model.Printers;
import ioc.app.bachhoa.ultil.CaptureAct;
import ioc.app.bachhoa.ultil.ItemClick;
import ioc.app.bachhoa.ultil.Message;
import ioc.app.bachhoa.ultil.PrintPriceTag;
import ioc.app.bachhoa.ultil.UserManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrintPriceTagActivity extends AppCompatActivity {
    // Khai báo các biến ánh xạ thành phần giao diện
    private EditText barcode;
    private Button btnFind, btnPrint;
    private ImageButton imbScan;
    private ImageView choosePrinter;
    private RecyclerView listView;
    private PrintPriceTagAdapter printPriceTagAapter;
    private List<PriceTag> list = new ArrayList<>();
    private List<Printer> printers = new ArrayList<>();
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_price_tag);
        uiInit();
        addEvent();
    }

    // Phương thức ánh xạ giao diện
    private void uiInit() {
        barcode = findViewById(R.id.appt_barcode);
        btnFind = findViewById(R.id.appt_btn_find);
        btnPrint = findViewById(R.id.appt_btn_print);
        imbScan = findViewById(R.id.appt_imb_scan);
        listView = findViewById(R.id.appt_list);
        choosePrinter = findViewById(R.id.appt_choose_printer);

        // Tạo bố cục dọc
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        // Gắng bố cục cho list view
        listView.setLayoutManager(linearLayoutManager);
        printPriceTagAapter = new PrintPriceTagAdapter(this);
        listView.setAdapter(printPriceTagAapter);
    }

    private void addEvent() {
        choosePrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrinterAPI.apiService.getAll(UserManager.getInstance().getUser().getStore().getStoreID()).enqueue(new Callback<List<Printers>>() {
                    @Override
                    public void onResponse(Call<List<Printers>> call, Response<List<Printers>> response) {
                        if (response.isSuccessful()) {
                            PrinterBottomSheetFragment printerBottomSheetFragment = new PrinterBottomSheetFragment(PrintPriceTagActivity.this, response.body(), new ItemClick() {
                                @Override
                                public void clickItem(Printers printers) {
                                    SharedPreferences sharedPreferences = PrintPriceTagActivity.this.getSharedPreferences("printerip", PrintPriceTagActivity.this.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("printerip", printers.getIpAddress());
                                    editor.apply();
                                }
                            });
                            printerBottomSheetFragment.show(getSupportFragmentManager(), printerBottomSheetFragment.getTag());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Printers>> call, Throwable t) {

                    }
                });

            }
        });
        // In tem giá
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintPriceTag printPriceTag = new PrintPriceTag(PrintPriceTagActivity.this);
                printPriceTag.setView(btnPrint);
                printPriceTag.printPriceTags(printPriceTag.generatePriceTags(list));
                list.clear();
                printPriceTagAapter.setChangeData(list);
            }
        });
        // Scan sản phẩm

        imbScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanCode();
            }
        });
        // btn Tìm
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(barcode.getText().toString().isEmpty())) {
                    getPriceTag(barcode.getText().toString());
                }
            }
        });
    }

    private void setDataForList(PriceTag priceTag) {
        list.add(priceTag);
        printPriceTagAapter.setChangeData(list);

    }

    private void getPriceTag(String productID) {
        ProductPositionAPI.apiService.getPriceTag(UserManager.getInstance().getUser().getStore().getStoreID(), productID).enqueue(new Callback<PriceTag>() {
            @Override
            public void onResponse(Call<PriceTag> call, Response<PriceTag> response) {
                if (response.isSuccessful()) {
                    setDataForList(response.body()
                    );
                } else if (response.code() == 204) {
                    Message message = new Message(PrintPriceTagActivity.this);
                    message.messageFailed(btnFind, "Sản phẩm không tồn tại!");
                }
            }

            @Override
            public void onFailure(Call<PriceTag> call, Throwable t) {

            }
        });

    }

    private void ScanCode() {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Quét mã Barcode và QR code");
        scanOptions.setBeepEnabled(true);
        scanOptions.setOrientationLocked(true);
        scanOptions.setCaptureActivity(CaptureAct.class);
        activityResultLauncher.launch(scanOptions);
    }

    ActivityResultLauncher<ScanOptions> activityResultLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            getPriceTag(result.getContents());
        }
    });
}