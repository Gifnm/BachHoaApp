package ioc.app.bachhoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ioc.app.bachhoa.Apdapter.DeliveryNoteAdapter;
import ioc.app.bachhoa.api.DeliveryNoteAPI;
import ioc.app.bachhoa.model.DeliveryNote;
import ioc.app.bachhoa.ultil.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImportGood extends AppCompatActivity {
    private TextView deliVeryNoteID;
    private Button btnFind;
    private ImageView scan;
    private RecyclerView listView;
    private List<DeliveryNote> deliveryNoteList = new ArrayList<>();
    private DeliveryNoteAdapter deliveryNoteAdapter = new DeliveryNoteAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_good);
        uiInit();
    }

    private void uiInit() {
        deliVeryNoteID = findViewById(R.id.aig_deli_very_note_id);
        btnFind = findViewById(R.id.aig_btn_find);
        scan = findViewById(R.id.aig_scan);
        listView = findViewById(R.id.aig_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        listView.setLayoutManager(linearLayoutManager);
        getDeliVeryNotes();
        listView.setAdapter(deliveryNoteAdapter);
        deliveryNoteAdapter.setData(deliveryNoteList);
    }

    private void getDeliVeryNotes() {
        DeliveryNoteAPI.apiService.getByStore(User.employee.getStore().getStoreID()).enqueue(new Callback<List<DeliveryNote>>() {
            @Override
            public void onResponse(Call<List<DeliveryNote>> call, Response<List<DeliveryNote>> response) {
                if (response.isSuccessful()) {
                    setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<DeliveryNote>> call, Throwable t) {

            }
        });

    }

    private void setData(List<DeliveryNote> list) {
        this.deliveryNoteList = list;

    }

    ;
}