package ioc.app.bachhoa.tapAdapter;

import android.Manifest;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import ioc.app.bachhoa.R;
import ioc.app.bachhoa.api.ProductPositionAPI;
import ioc.app.bachhoa.api.ProductAPI;
import ioc.app.bachhoa.fm.ProductInfo_fm;
import ioc.app.bachhoa.model.Product;
import ioc.app.bachhoa.model.ProductPositioning;
import ioc.app.bachhoa.ultil.CaptureAct;
import ioc.app.bachhoa.ultil.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link viewProduct_pm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class viewProduct_pm extends Fragment {
    // Khai bao cac bien dung de anh xa giao dien
    ImageButton scan;

    EditText editText;
    // Bien view dung de anh xa giao dien
    View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public viewProduct_pm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment viewProduct_pm.
     */
    // TODO: Rename and change types and number of parameters
    public static viewProduct_pm newInstance(String param1, String param2) {
        viewProduct_pm fragment = new viewProduct_pm();
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
        view = inflater.inflate(R.layout.fragment_view_product_pm, container, false);
        uiInit();
        AddEvent();
        return view;
    }

    // Phuong thưc anh xa giao dien va khoi tao giao dien ban dau
    private void uiInit() {

        scan = view.findViewById(R.id.vpo_scan);


    }

    private void AddEvent() {
        Scancode();
    }

    private void checkPermission() {
        Dexter.withContext(getContext()).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

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

    private void Scancode() {
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });

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
            findLocation(result.getContents());
        }
    });

    private void findLocation(String id) {
        //  ProductPositionService.apiService.f
        ProductPositionAPI.apiService.findByProductID(id, User.employee.getStore().getStoreID()).enqueue(new Callback<ProductPositioning>() {
            @Override
            public void onResponse(Call<ProductPositioning> call, Response<ProductPositioning> response) {
                if (response.body() != null) {
                    ProductPositioning proPosition = response.body();
                    // Gắn fragment để hiển thị thông tin sản phẩm
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ProductInfo_fm productInfo_fm = new ProductInfo_fm();
                    Bundle bundle = new Bundle();
                    Gson gson = new Gson();
                    String content = gson.toJson(proPosition);
                    bundle.putString("content",content);
                    bundle.putString("shelf","Kệ: " + proPosition.getDisplayShelves().getDisSheID());
                    bundle.putString("platter","Mâm: " + proPosition.getDisplayPlatter().getDisPlaID());
                    bundle.putString("location","Vị trí: " + proPosition.getId());
                    bundle.putString("quantity", "SL: " + proPosition.getDisplayQuantity());
                    bundle.putString("inventory","Tồn: " + proPosition.getProduct().getInventory() );
                    bundle.putString("name", proPosition.getProduct().getProductName());
                    bundle.putString("barcode","Barcode: " + proPosition.getProduct().getProductID() );
                    bundle.putString("status", proPosition.getProduct().getStatus() ? "KD bình thường" : "Ngưng kinh doanh");
                    bundle.putString("image",proPosition.getProduct().getImage() );
                    bundle.putString("form",proPosition.getForm()+"");
                    productInfo_fm.setArguments(bundle);
                    fragmentTransaction.replace(R.id.vpo_product_info,productInfo_fm);
                    fragmentTransaction.commit();
                } else {
                    findProduct(id);
                }
            }

            @Override
            public void onFailure(Call<ProductPositioning> call, Throwable t) {

            }
        });

    }

    private void findProduct(String id) {
        ProductAPI.apiService.findByID(id, 1).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Product product = response.body();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        ProductInfo_fm productInfo_fm = new ProductInfo_fm();
                        fragmentTransaction.replace(R.id.vpo_product_info,productInfo_fm);
                    } else {
                        Toast.makeText(getActivity(), "Sản phẩm không tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });

    }
}