package ioc.app.bachhoa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ioc.app.bachhoa.Apdapter.ListShipmentBacthAdapter;
import ioc.app.bachhoa.DTOEntity.ProductOnShelf;
import ioc.app.bachhoa.api.ShipmentBatchAPI;
import ioc.app.bachhoa.model.ShipmentBatch;
import ioc.app.bachhoa.ultil.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReplenishmentPeriod extends AppCompatActivity {
    Button create_RP;
    RecyclerView recyclerView;
    List<ShipmentBatch> shipmentBatchList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replenishment_period);
        uiInit();
        getlistShipmentBatch();
        addEvent();
    }

    private void uiInit() {
        create_RP = (Button) findViewById(R.id.create_RP);
        recyclerView = (RecyclerView) findViewById(R.id.arp_listShipmentBatch);


    }

    private void addEvent() {
        create_RP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("rp", Context.MODE_PRIVATE);

                // Lấy chuỗi JSON từ SharedPreferences
                String json = sharedPreferences.getString("keyrp", null);

                if (json != null) {
                    // Chuyển chuỗi JSON thành danh sách chuỗi
                    List<ProductOnShelf> list = new Gson().fromJson(json, new TypeToken<List<ProductOnShelf>>() {
                    }.getType());
                    if (!list.isEmpty()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ReplenishmentPeriod.this);
                        // Thiết lập thông báo
                        builder.setMessage("Bạn có muốn tiếp tục danh sách đã quét");
                        // Thiết lập nút "HỦY" và xử lý sự kiện khi nút này được nhấn
                        builder.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Xử lý khi nút "HỦY" được nhấn
                                SharedPreferences sharedPreferences = getSharedPreferences("rp", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                // Xóa toàn bộ dữ liệu từ SharedPreferences
                                editor.clear();
                                editor.apply();
                                dialog.dismiss(); // Đóng AlertDialog
                                Intent intent = new Intent(ReplenishmentPeriod.this, ScanProductActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                            }
                        });

                        // Thiết lập nút "Tiếp tục" và xử lý sự kiện khi nút này được nhấn
                        builder.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Xử lý khi nút "Tiếp tục" được nhấn
                                // Thêm mã xử lý tại đây
                                dialog.dismiss();
                                Intent intent = new Intent(ReplenishmentPeriod.this, ScanProductActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                // Sau khi xử lý xong, bạn có thể đóng AlertDialog

                            }
                        });

                        // Tạo và hiển thị AlertDialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(ReplenishmentPeriod.this, ScanProductActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }
                } else {
                    Intent intent = new Intent(ReplenishmentPeriod.this, ScanProductActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
                // Sử dụng danh sách chuỗi
            }
        });
    }

    private void getlistShipmentBatch() {
        ShipmentBatchAPI.apiService.getByStoreID(User.employee.getStore().getStoreID()).enqueue(new Callback<List<ShipmentBatch>>() {
            @Override
            public void onResponse(Call<List<ShipmentBatch>> call, Response<List<ShipmentBatch>> response) {

                setData(response.body());


            }

            @Override
            public void onFailure(Call<List<ShipmentBatch>> call, Throwable t) {
                Toast.makeText(ReplenishmentPeriod.this, "ShipmentBatch Faild", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setData(List<ShipmentBatch> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        List<ShipmentBatch> list4 = new ArrayList<>();
        list4.add(new ShipmentBatch());
        ListShipmentBacthAdapter listShipmentBacthAdapter = new ListShipmentBacthAdapter(this);
        recyclerView.setAdapter(listShipmentBacthAdapter);
        listShipmentBacthAdapter.setData(list);

    }
}
