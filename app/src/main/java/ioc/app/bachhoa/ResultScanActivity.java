package ioc.app.bachhoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ioc.app.bachhoa.Apdapter.ResultScanHolder;
import ioc.app.bachhoa.DTOEntity.ProductOnShelf;

public class ResultScanActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView arc_count;
    private Button ars_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_scan);
        recyclerView = findViewById(R.id.ars_list);
        arc_count = findViewById(R.id.arc_count);
        ars_btn = findViewById(R.id.ars_btn);
        ars_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultScanActivity.this, FinalScan.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        viewResult();
    }

    private void viewResult() {
        List<ProductOnShelf> list = new ArrayList<>();
        // Lấy chuỗi JSON từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("rp", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("keyrp", null);

        if (json != null) {
            // Chuyển chuỗi JSON thành danh sách chuỗi
            list = new Gson().fromJson(json, new TypeToken<List<ProductOnShelf>>() {
            }.getType());
            // Sử dụng danh sách chuỗi
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            ResultScanHolder resultScanHolder = new ResultScanHolder(this);
            recyclerView.setAdapter(resultScanHolder);
            resultScanHolder.setData(list);
            arc_count.setText(list.size() + "");

        }
    }


}