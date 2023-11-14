package ioc.app.bachhoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import ioc.app.bachhoa.Apdapter.PrinterAdapter;
import ioc.app.bachhoa.api.PrinterAPI;
import ioc.app.bachhoa.model.Printers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrinterActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private Button addPrinter;
    private RecyclerView listView;
    private PrinterAdapter printerAdapter;
    private ImageView backActivity;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);
        uiInit();
        addEvent();
    }

    private void uiInit() {
        listView = findViewById(R.id.a_printer_list);
        addPrinter = findViewById(R.id.a_printer_btn_add);
        backActivity = findViewById(R.id.a_printer_back_button);
        swipeRefreshLayout = findViewById(R.id.a_printer_refesh);
        // Tạo bố cục cho listView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        // Gắng bố cục cho listView
        listView.setLayoutManager(linearLayoutManager);
        // Khởi tạo Adapter
        printerAdapter = new PrinterAdapter(this);
        // Gắng Adapter cho listView
        listView.setAdapter(printerAdapter);
        loadData();
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void addEvent() {
        addPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPrinter();
            }
        });
        backActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addPrinter() {
        Intent intent = new Intent(PrinterActivity.this, AddPrinterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void loadData() {
        PrinterAPI.apiService.getAll(1).enqueue(new Callback<List<Printers>>() {
            @Override
            public void onResponse(Call<List<Printers>> call, Response<List<Printers>> response) {
                if (response.isSuccessful()) {
                    printerAdapter.setChangeData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Printers>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadData();
    }

    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }
}