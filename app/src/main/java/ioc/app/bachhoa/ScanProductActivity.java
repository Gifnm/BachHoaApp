package ioc.app.bachhoa;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import ioc.app.bachhoa.DTOEntity.ProductOnShelf;
import ioc.app.bachhoa.api.ShipmentBatchDetailAPI;
import ioc.app.bachhoa.model.ProductPositioning;
import ioc.app.bachhoa.ultil.CaptureAct;
import ioc.app.bachhoa.ultil.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanProductActivity extends AppCompatActivity {
    // Khai báo các biến cần ánh xạ giao diện
    Button viewResult, nextNumber, prevousNumber, nb0, nb1, nb2, nb3, nb4, nb5, nb6, nb7, nb8, nb9, nb10, nb11, nb12, nb13, nb14;
    TextView barcode;
    LinearLayout linearLayout;
    ImageButton scan;
    int index = 15;
    int count = 15;
    private ProductPositioning productPositioning;
    List<ProductOnShelf> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_product);
        uiInit();
        addEvent();
        linearLayout.setVisibility(View.GONE);
    }

    private void uiInit() {
        scan = findViewById(R.id.rp_scan);
        linearLayout = findViewById(R.id.rp_enter_number);
        barcode = findViewById(R.id.rp_barcode);
        nextNumber = findViewById(R.id.rp_btn_more_number);
        prevousNumber = findViewById(R.id.rp_btn_prevous_number);

        viewResult = findViewById(R.id.asp_view_result);
        nb0 = findViewById(R.id.rp_nb0);
        nb1 = findViewById(R.id.rp_nb1);
        nb2 = findViewById(R.id.rp_nb2);
        nb3 = findViewById(R.id.rp_nb3);
        nb4 = findViewById(R.id.rp_nb4);
        nb5 = findViewById(R.id.rp_nb5);
        nb6 = findViewById(R.id.rp_nb6);
        nb7 = findViewById(R.id.rp_nb7);
        nb8 = findViewById(R.id.rp_nb8);
        nb9 = findViewById(R.id.rp_nb9);
        nb10 = findViewById(R.id.rp_nb10);
        nb11 = findViewById(R.id.rp_nb11);
        nb12 = findViewById(R.id.rp_nb12);
        nb13 = findViewById(R.id.rp_nb13);
        nb14 = findViewById(R.id.rp_nb14);


    }

    private void addEvent() {
        viewResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScanProductActivity.this, ResultScanActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
        prevousNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 15) {
                    for (int i = 0; i < 15; i++) {

                        switch (i) {
                            case 0:
                                nb0.setText((Integer.parseInt(nb0.getText().toString()) - index) + "");
                                break;
                            case 1:
                                nb1.setText((Integer.parseInt(nb1.getText().toString()) - index) + "");
                                break;
                            case 2:
                                nb2.setText((Integer.parseInt(nb2.getText().toString()) - index) + "");
                                break;
                            case 3:
                                nb3.setText((Integer.parseInt(nb3.getText().toString()) - index) + "");
                                break;
                            case 4:
                                nb4.setText((Integer.parseInt(nb4.getText().toString()) - index) + "");
                                break;
                            case 5:
                                nb5.setText((Integer.parseInt(nb5.getText().toString()) - index) + "");
                                break;
                            case 6:
                                nb6.setText((Integer.parseInt(nb6.getText().toString()) - index) + "");
                                break;
                            case 7:
                                nb7.setText((Integer.parseInt(nb7.getText().toString()) - index) + "");
                                break;
                            case 8:
                                nb8.setText((Integer.parseInt(nb8.getText().toString()) - index) + "");
                                break;
                            case 9:
                                nb9.setText((Integer.parseInt(nb9.getText().toString()) - index) + "");
                                break;
                            case 10:
                                nb10.setText((Integer.parseInt(nb10.getText().toString()) - index) + "");
                                break;

                            case 11:
                                nb11.setText((Integer.parseInt(nb11.getText().toString()) - index) + "");
                                break;
                            case 12:
                                nb12.setText((Integer.parseInt(nb12.getText().toString()) - index) + "");
                                break;
                            case 13:
                                nb13.setText((Integer.parseInt(nb13.getText().toString()) - index) + "");
                                break;
                            case 14:
                                nb14.setText((Integer.parseInt(nb14.getText().toString()) - index) + "");
                                break;
                        }

                    }
                    count -= 15;

                }
            }
        });
        nextNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 15; i++) {
                    switch (i) {
                        case 0:
                            nb0.setText((Integer.parseInt(nb0.getText().toString()) + index) + "");
                            break;
                        case 1:
                            nb1.setText((Integer.parseInt(nb1.getText().toString()) + index) + "");
                            break;
                        case 2:
                            nb2.setText((Integer.parseInt(nb2.getText().toString()) + index) + "");
                            break;
                        case 3:
                            nb3.setText((Integer.parseInt(nb3.getText().toString()) + index) + "");
                            break;
                        case 4:
                            nb4.setText((Integer.parseInt(nb4.getText().toString()) + index) + "");
                            break;
                        case 5:
                            nb5.setText((Integer.parseInt(nb5.getText().toString()) + index) + "");
                            break;
                        case 6:
                            nb6.setText((Integer.parseInt(nb6.getText().toString()) + index) + "");
                            break;
                        case 7:
                            nb7.setText((Integer.parseInt(nb7.getText().toString()) + index) + "");
                            break;
                        case 8:
                            nb8.setText((Integer.parseInt(nb8.getText().toString()) + index) + "");
                            break;
                        case 9:
                            nb9.setText((Integer.parseInt(nb9.getText().toString()) + index) + "");
                            break;
                        case 10:
                            nb10.setText((Integer.parseInt(nb10.getText().toString()) + index) + "");
                            break;

                        case 11:
                            nb11.setText((Integer.parseInt(nb11.getText().toString()) + index) + "");
                            break;
                        case 12:
                            nb12.setText((Integer.parseInt(nb12.getText().toString()) + index) + "");
                            break;
                        case 13:
                            nb13.setText((Integer.parseInt(nb13.getText().toString()) + index) + "");
                            break;
                        case 14:
                            nb14.setText((Integer.parseInt(nb14.getText().toString()) + index) + "");
                            break;
                    }


                }
                count += 15;

            }
        });
        nb0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();
            }
        });
        nb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();
            }
        });
        nb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();
            }
        });
        nb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();
            }
        });
        nb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();
            }
        });
        nb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();
            }
        });
        nb6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();
            }
        });
        nb7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();
            }
        });
        nb8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();
            }
        });
        nb9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();
            }
        });
        nb10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();
            }
        });
        nb11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();
            }
        });
        nb12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();
            }
        });
        nb13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();
            }
        });
        nb14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = getSharedPreferences("rp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(list);
        editor.putString("keyrp", json);
        editor.apply();
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("rp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(list);
        editor.putString("keyrp", json);
        editor.apply();
    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        // Lấy chuỗi JSON từ SharedPreferences
//        SharedPreferences sharedPreferences = getSharedPreferences("rp", Context.MODE_PRIVATE);
//        String json = sharedPreferences.getString("keyrp", null);
//
//        if (json != null) {
//            // Chuyển chuỗi JSON thành danh sách chuỗi
//            list = new Gson().fromJson(json, new TypeToken<List<ProductOnShelf>>() {
//            }.getType());
//
//            // Sử dụng danh sách chuỗi
//        }
//    }

    private void checkPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                scanCode();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();


    }

    private void scanCode() {
// Tạo đối tượng scanOptions
        ScanOptions scanOptions = new ScanOptions();
        // Thêm chữ muốn hiển thị trên màng hình quét
        scanOptions.setPrompt("Quét mã Barcode hoặc qr code");
        scanOptions.setBeepEnabled(true);
        // Khóa hướng màng hình quét
        scanOptions.setOrientationLocked(true);
        scanOptions.setCaptureActivity(CaptureAct.class);
        activityResultLauncher.launch(scanOptions);
    }

    ActivityResultLauncher<ScanOptions> activityResultLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
// Nhận được mã sản phẩm
            // Kiểm tra mã sản phẩm tồn tại
            // Kiểm tra mã sản phẩm đã có trong danh sách chưa
            for (ProductOnShelf productOnShelf : list) {
                if (productOnShelf.getProductPositioning().getProduct().getProductID().equals(result.getContents())) {
                    Toast.makeText(this, "Đã quét sản phẩm này", Toast.LENGTH_SHORT).show();
                    return;
                }
            }


            ShipmentBatchDetailAPI.apiService.checkScan(result.getContents(), User.employee.getStore().getStoreID()).enqueue(new Callback<ProductPositioning>() {
                @Override
                public void onResponse(Call<ProductPositioning> call, Response<ProductPositioning> response) {
                    if (response.isSuccessful()) {
                        barcode.setText(result.getContents());
                        setProductPosition(response.body());
                        linearLayout.setVisibility(View.VISIBLE);

                    } else if (response.code() == 422) {
                        Toast.makeText(ScanProductActivity.this, "Sản phẩm chưa có vị trí trưng bày", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ProductPositioning> call, Throwable t) {
                    Toast.makeText(ScanProductActivity.this, "Faild", Toast.LENGTH_SHORT).show();
                }
            });
        }
    });

    private void addToList() {
        list.add(new ProductOnShelf(productPositioning, Integer.parseInt(nb13.getText().toString())));
        linearLayout.setVisibility(View.GONE);

    }

    private void setProductPosition(ProductPositioning productPositioning) {
        this.productPositioning = productPositioning;
    }
}