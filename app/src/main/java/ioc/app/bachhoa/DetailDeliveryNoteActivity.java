package ioc.app.bachhoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ioc.app.bachhoa.Apdapter.DetailedDeliveryNoteAdapter;
import ioc.app.bachhoa.api.DetailedDeliveryNoteAPI;
import ioc.app.bachhoa.model.DetailedDeliveryNote;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailDeliveryNoteActivity extends AppCompatActivity {
    // Khai báo các biến ánh xạ giao diện
    private EditText txtProductId;
    private Button btnFind, btnViewResult;
    private ImageButton imbScan;
    private RecyclerView listView;
    // Khai báo Adapter -> Dùng để hiển thị nội dung RecycleView
    private DetailedDeliveryNoteAdapter deliveryNoteAdapter = new DetailedDeliveryNoteAdapter(this);
    // Khai báo danh danh sách DetailedDeeliveryNote
    List<DetailedDeliveryNote> detailedDeliveryNoteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_delivery_note);
        uiInit();
        addEvent();
    }

    private void uiInit() {
        txtProductId = findViewById(R.id.addn_txt_barcode);
        btnFind = findViewById(R.id.addn_btn_find);
        imbScan = findViewById(R.id.addn_imb_scan);
        btnViewResult = findViewById(R.id.addn_view_result);
        listView = findViewById(R.id.addn_list);
        // Tạo bố cục
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        // Gắng bố cục cho RecycleView
        listView.setLayoutManager(linearLayoutManager);
        // Gắng Adapter cho RecycleView
        listView.setAdapter(deliveryNoteAdapter);
        getDetailedDeliveryNote();
        deliveryNoteAdapter.setData(detailedDeliveryNoteList);
    }

    private void getDetailedDeliveryNote() {
        Intent intent = getIntent();
        if (intent != null) {
            String deliveryNoteId = intent.getStringExtra("deliveryNoteId");
            DetailedDeliveryNoteAPI.apiService.getByDeliveryNote(deliveryNoteId).enqueue(new Callback<List<DetailedDeliveryNote>>() {
                @Override
                public void onResponse(Call<List<DetailedDeliveryNote>> call, Response<List<DetailedDeliveryNote>> response) {
                    if (response.isSuccessful()) {
                        deliveryNoteAdapter.setData(response.body());
                        //setDataForList(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<DetailedDeliveryNote>> call, Throwable t) {

                }
            });
        }
    }

    private void setDataForList(List<DetailedDeliveryNote> list) {
        this.detailedDeliveryNoteList = list;

    }

    private void addEvent() {
        btnViewResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveResult();
                Intent intent = new Intent(DetailDeliveryNoteActivity.this, InputSlipDifferenceActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    private void saveResult() {
        detailedDeliveryNoteList = deliveryNoteAdapter.getList();
        SharedPreferences sharedPreferences = getSharedPreferences("detailedDeliveryNoteList", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String listSend = gson.toJson(detailedDeliveryNoteList);
        editor.putString("key", listSend);
        editor.apply();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        deliveryNoteAdapter.setData(detailedDeliveryNoteList);
    }
}