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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ioc.app.bachhoa.Apdapter.InputSlipDifferenceAdapter;
import ioc.app.bachhoa.DTOEntity.ProductOnShelf;
import ioc.app.bachhoa.api.DetailedDeliveryNoteAPI;
import ioc.app.bachhoa.model.DetailedDeliveryNote;
import ioc.app.bachhoa.ultil.ALoadingDialog;
import ioc.app.bachhoa.ultil.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputSlipDifferenceActivity extends AppCompatActivity {
    // Khai báo các biến ánh xạ giao diện
    private TextView numberDiffernce;
    private RecyclerView listView;
    private Button saveResult;
    // Khai báo các danh sách dữ liệu
    private List<DetailedDeliveryNote> detailedDeliveryNoteList = new ArrayList<>();
    private List<DetailedDeliveryNote> differenceList = new ArrayList<>();
    // Khai báo Adapter cho listView
    InputSlipDifferenceAdapter inputSlipDifferenceAdapter = new InputSlipDifferenceAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_slip_difference);
        uiInit();
        addEvent();
    }

    private void uiInit() {
        numberDiffernce = findViewById(R.id.aisd_number_diffrence);
        listView = findViewById(R.id.aisd_list);
        saveResult = findViewById(R.id.aisd_save);
        // Tạo bố cục dao diện
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(inputSlipDifferenceAdapter);
        setViewForList();
    }

    private void setViewForList() {
        SharedPreferences sharedPreferences = getSharedPreferences("detailedDeliveryNoteList", this.MODE_PRIVATE);
        String json = sharedPreferences.getString("key", "");
        Gson gson = new Gson();
        if (json != null) {

            detailedDeliveryNoteList = new Gson().fromJson(json, new TypeToken<List<DetailedDeliveryNote>>() {
            }.getType());
            for (DetailedDeliveryNote deliveryNote : detailedDeliveryNoteList
            ) {
                if ((deliveryNote.getCount() - deliveryNote.getQuantity()) != 0) {
                    differenceList.add(deliveryNote);
                }
            }
            inputSlipDifferenceAdapter.setChangeData(differenceList);
            if (differenceList != null) {
                numberDiffernce.setText(differenceList.size() + "");
            }
        }
    }

    private void addEvent() {
        saveResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InputSlipDifferenceActivity.this);
                builder.setMessage(differenceList.isEmpty() ? "Lưu và bỏ qua chênh lệch?" : "Xác nhận liêu nhập hàng?");
                builder.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        save();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void save() {
        ALoadingDialog alertDialog = new ALoadingDialog(InputSlipDifferenceActivity.this);
        alertDialog.show();
        DetailedDeliveryNoteAPI.apiService.save(detailedDeliveryNoteList, User.employee.getEmployeeID()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(InputSlipDifferenceActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                    alertDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                alertDialog.cancel();
            }
        });

    }
}