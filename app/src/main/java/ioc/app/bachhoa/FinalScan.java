package ioc.app.bachhoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import ioc.app.bachhoa.Apdapter.CanBePrickedAdapter;
import ioc.app.bachhoa.Apdapter.ExistIncorrectlyAdapter;
import ioc.app.bachhoa.Apdapter.OutOfExistenceAdapter;
import ioc.app.bachhoa.DTOEntity.ProductOnShelf;

public class FinalScan extends AppCompatActivity {
    RecyclerView viewListOutOfExistence, viewListExistIncorrectly, viewListCanBePricked;
    List outOfExistence, exitIncorrectly, canBePricked;
    // Biến ánh xạ view
    private Button create;
    private TextView outOfExistenceNumber, existIncorrectlyNumber, canBePrickedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_scan);
        uiInit();
    }

    private void uiInit() {
        outOfExistence = new ArrayList();
        exitIncorrectly = new ArrayList();
        canBePricked = new ArrayList();
        // Ánh xạ
        create = findViewById(R.id.afs_create);
        outOfExistenceNumber = findViewById(R.id.afs_clear);
        existIncorrectlyNumber = findViewById(R.id.afs_errol);
        canBePrickedNumber = findViewById(R.id.afs_access);
    }

    private void getAndSetData() {

    }

    private void setListForTable(List<ProductOnShelf> productOnShelfList) {
        for (ProductOnShelf productOnShelf :
                productOnShelfList) {
            if (productOnShelf.getStatus() == 2) {
                canBePricked.add(productOnShelf);
            } else if (productOnShelf.getStatus() == 1) {
                outOfExistence.add(productOnShelf);
            } else {
                exitIncorrectly.add(productOnShelf);
            }

        }
// Tạo và gắng bố cục dọc cho các table
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        viewListOutOfExistence.setLayoutManager(linearLayoutManager);
        viewListCanBePricked.setLayoutManager(linearLayoutManager);
        viewListExistIncorrectly.setLayoutManager(linearLayoutManager);
        // Tạo và gắng Adapter cho các RecycleView
        OutOfExistenceAdapter outOfExistenceAdapter = new OutOfExistenceAdapter(this);
        viewListOutOfExistence.setAdapter(outOfExistenceAdapter);
        outOfExistenceAdapter.setChangeData(outOfExistence);
        CanBePrickedAdapter canBePrickedAdapter = new CanBePrickedAdapter(this);
        viewListCanBePricked.setAdapter(canBePrickedAdapter);
        canBePrickedAdapter.setChangeData(canBePricked);
        ExistIncorrectlyAdapter existIncorrectlyAdapter = new ExistIncorrectlyAdapter(this);
        viewListExistIncorrectly.setAdapter(existIncorrectlyAdapter);
        existIncorrectlyAdapter.setChangeData(exitIncorrectly);

    }
}