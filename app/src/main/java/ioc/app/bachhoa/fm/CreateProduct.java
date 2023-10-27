package ioc.app.bachhoa.fm;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import ioc.app.bachhoa.Apdapter.CategoriesAdapter;
import ioc.app.bachhoa.R;
import ioc.app.bachhoa.api.APIService;
import ioc.app.bachhoa.model.Categories;
import ioc.app.bachhoa.model.Product;
import ioc.app.bachhoa.model.Store;
import ioc.app.bachhoa.ultil.CaptureAct;
import ioc.app.bachhoa.ultil.RealPathUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateProduct#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateProduct extends Fragment {
    private static final int MY_REQUEST_CODE = 10;
    View view;
    ImageButton chooseDate;
    TextView date_exp;
    ImageButton createProductScan;
    EditText barcode;
    EditText nameProduct;
    EditText price, priceSell;
    EditText quanity;
    Button save;
    Button addCategories;
    Button select_img;
    ImageView img_selected;
    RadioButton status;
    EditText vat;
    Uri muri;
    Spinner categories;
    Date exp;
    int categoryID;
    int idCategories;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateProduct() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateProduct.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateProduct newInstance(String param1, String param2) {
        CreateProduct fragment = new CreateProduct();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_product, container, false);
        anhxa();
        AddEvent();
        return view;
    }

    private void anhxa() {
        chooseDate = (ImageButton) view.findViewById(R.id.choose_date);
        createProductScan = (ImageButton) view.findViewById(R.id.create_product_scan);
        barcode = (EditText) view.findViewById(R.id.cp_barcode);
        nameProduct = (EditText) view.findViewById(R.id.cp_name_product);
        price = (EditText) view.findViewById(R.id.cp_price);
        quanity = (EditText) view.findViewById(R.id.cp_quanity);
        save = (Button) view.findViewById(R.id.cp_save);
        addCategories = (Button) view.findViewById(R.id.cp_btn_addCategories);
        date_exp = (EditText) view.findViewById(R.id.cp_date_exp);
        select_img = (Button) view.findViewById(R.id.cp_select_img);
        img_selected = (ImageView) view.findViewById(R.id.cp_img_selected);
        status = (RadioButton) view.findViewById(R.id.cp_status);
        vat = (EditText) view.findViewById(R.id.cp_vat);
        categories = (Spinner) view.findViewById(R.id.cp_categories);
        priceSell = (EditText) view.findViewById(R.id.cp_priceSell);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showBottomNavigationView();
    }

    private void showBottomNavigationView() {
        final BottomNavigationView navigationView = getActivity().findViewById(R.id.navigation);
        if (navigationView != null) {
            navigationView.setVisibility(View.VISIBLE);
        }
    }

    public void ScanCode() {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Quét mã Barcode và QR code");
        scanOptions.setBeepEnabled(true);
        scanOptions.setOrientationLocked(true);
        scanOptions.setCaptureActivity(CaptureAct.class);
        activityResultLauncher.launch(scanOptions);

    }

    private void AddEvent() {
        getCategories();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm()){
                Product product = new Product();
                product.setProductID(barcode.getText().toString());
                product.setProductName(nameProduct.getText().toString());
                product.setPrice(Float.parseFloat(price.getText().toString()));
                product.setImportPrice(Float.parseFloat(priceSell.getText().toString()));
                product.setStatus(status.isSelected());
                String vat2= vat.getText().toString();
                product.setVat(Integer.parseInt(vat2.equals("")?"0":vat2));
                product.setStore(new Store(1));
                String inventory2 = quanity.getText().toString();
                product.setInventory(Integer.parseInt(inventory2.equals("")?"0":inventory2));
//                product.setNearestExpDate(exp);
                product.setCategories(new Categories(categoryID));
                String realPath = RealPathUtil.getRealPath(getContext(), muri);
                File file = new File(realPath);
                RequestBody requestBodyavt = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part mPart = MultipartBody.Part.createFormData("image", file.getName(), requestBodyavt);
                APIService.apiService.uploadSanPhamWithImage(product, mPart).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        clearForm();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
                    }
                });

            }}
        });
        select_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPemission();
            }
        });
        createProductScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanCode();

            }
        });
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ngày hiện tại
                Calendar calendar = Calendar.getInstance();
                int initialYear = calendar.get(Calendar.YEAR);
                int initialMonth = calendar.get(Calendar.MONTH);
                int initialDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), // hoặc getActivity() nếu bạn đang ở trong Fragment
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Xử lý ngày đã chọn
                                String dateString = String.format("%04d-%02d-%02d", year, month, dayOfMonth);


                                try {
                                    // Bước 2: Chuyển đổi chuỗi thành đối tượng java.sql.Date
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    java.util.Date parsedDate = dateFormat.parse(dateString);
                                    exp = new java.sql.Date(parsedDate.getTime());
                                    date_exp.setText(dateString);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        initialYear, initialMonth, initialDay // Thiết lập ngày mặc định là ngày hiện tại
                );
                datePickerDialog.show();
            }
        });

    }

    ActivityResultLauncher<ScanOptions> activityResultLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            barcode.setText(result.getContents());
        }

    });

    private void requestPemission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();

        }
        if (PermissionChecker.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED) {
            // Ứng dụng có quyền truy cập vào READ_EXTERNAL_STORAGE
            // Thực hiện các hoạt động liên quan đến quyền ở đây
            openGallery();
        } else {
            // Ứng dụng không có quyền truy cập vào READ_EXTERNAL_STORAGE
            // Cần yêu cầu quyền từ người dùng
            String[] permisstion = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permisstion, MY_REQUEST_CODE);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private ActivityResultLauncher<Intent> mLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            , new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        muri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                            img_selected.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    private void setCategories(List<Categories> list) {
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getContext(), R.layout.item_categories2_selected, list);
        categories.setAdapter(categoriesAdapter);

        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý khi một mục được chọn
                categoryID = categoriesAdapter.getItem(position).getCategoriesID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có mục nào được chọn
            }
        });
    }


    public void getCategories() {

APIService.apiService.getCategories().enqueue(new Callback<List<Categories>>() {
    @Override
    public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
        List<Categories> categories = (List<Categories>) response.body();
setCategories(categories);
    }

    @Override
    public void onFailure(Call<List<Categories>> call, Throwable t) {

    }
});


    }
    private void clearForm(){

        barcode.setText("");
        price.setText("");
        vat.setText("");
        date_exp.setText("");
        status.setSelected(true);
        muri = null;
        nameProduct.setText("");
        price.setText("");
        muri = null;
        quanity.setText("");
        priceSell.setText("");
        img_selected.setBackgroundResource(R.drawable.ic_baseline_image_24);
    }
    private boolean validateForm(){
if(barcode.getText().toString().equals("")){
    Toast.makeText(getActivity(), "Nhập mã barcode", Toast.LENGTH_SHORT).show();
    return false;
}
else if(nameProduct.getText().toString().equals("")){
    Toast.makeText(getActivity(), "Nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
    return false;

}
else if(priceSell.getText().toString().equals("")){
    Toast.makeText(getActivity(), "Nhập giá bán", Toast.LENGTH_SHORT).show();
    return false;
}
else{

    return true;
}

    }
}