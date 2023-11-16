package ioc.app.bachhoa;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ioc.app.bachhoa.Apdapter.PrintPriceTagAdapter;
import ioc.app.bachhoa.DTOEntity.PriceTag;
import ioc.app.bachhoa.api.ProductPositionAPI;
import ioc.app.bachhoa.ultil.CaptureAct;
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
    private RecyclerView listView;
    private PrintPriceTagAdapter printPriceTagAapter;
    private List<PriceTag> list = new ArrayList<>();

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

        // Tạo bố cục dọc
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        // Gắng bố cục cho list view
        listView.setLayoutManager(linearLayoutManager);
        printPriceTagAapter = new PrintPriceTagAdapter(this);
        listView.setAdapter(printPriceTagAapter);
    }

    private void addEvent() {
        // In tem giá
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintPriceTag printPriceTag = new PrintPriceTag(PrintPriceTagActivity.this);
                for (PriceTag priceTag : list){
                    printPriceTag.printOnetag(printPriceTag.generateOnePriceTagSalse(priceTag));
                }
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