package ioc.app.bachhoa.tapAdapter;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import ioc.app.bachhoa.R;
import ioc.app.bachhoa.api.ProductPositionService;
import ioc.app.bachhoa.api.ProductService;
import ioc.app.bachhoa.model.Product;
import ioc.app.bachhoa.model.ProductPositioning;
import ioc.app.bachhoa.ultil.CaptureAct;
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
    ImageView img;
    TextView shelf, platter, location, quantity, inventory, status, nameOfProd, barcode;
    Button print;
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
        img = view.findViewById(R.id.vpo_img);
        scan = view.findViewById(R.id.vpo_scan);
        print = view.findViewById((R.id.vpo_print));
        shelf = view.findViewById(R.id.vpo_shelf);
        platter = view.findViewById(R.id.vpo_platter);
        location = view.findViewById(R.id.vpo_location);
        quantity = view.findViewById(R.id.vpo_quantity);
        inventory = view.findViewById(R.id.vpo_inventory);
        nameOfProd = view.findViewById(R.id.vpo_name);
        barcode = view.findViewById(R.id.vpo_barcode);

    }

    private void AddEvent() {
scanCode();

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

        findLocation(result.getContents());
    });

    private void findLocation(String id) {
        //  ProductPositionService.apiService.f
        ProductPositionService.apiService.findByProductID(id,1).enqueue(new Callback<ProductPositioning>() {
            @Override
            public void onResponse(Call<ProductPositioning> call, Response<ProductPositioning> response) {
                if (response.body() != null) {
                    ProductPositioning proPosition = response.body();
                    shelf.setText("Kệ: " + proPosition.getDisplayShelves().getDisSheID());
                    platter.setText("Mâm: " + proPosition.getDisplayPlatter().getDisPlaID());
                    location.setText("Vị trí: " + proPosition.getId());
                    quantity.setText("SL: " + proPosition.getDisplayQuantity());
                    inventory.setText("Tồn: " + proPosition.getProduct().getInventory());
                    nameOfProd.setText(proPosition.getProduct().getProductName());
                    barcode.setText("Barcode: " + proPosition.getProduct().getProductID());
                    status.setText(proPosition.getProduct().getStatus() ? "KD bình thường" : "Ngưng kinh doanh");
                    Glide.with(getContext())
                            .load(proPosition.getProduct().getImage())
                            .error(R.drawable.ic_baseline_cloud_download_24)
                            .into(img);
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
        ProductService.apiService.findByID(id, 1).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Product product = response.body();
                        nameOfProd.setText(product.getProductName());
                        barcode.setText("Barcode: " + product.getProductID());
                        inventory.setText("Tồn: " + product.getInventory());
                        status.setText(product.getStatus() ? "KD bình thường" : "Ngưng kinh doanh");
                        Glide.with(getContext())
                                .load(product.getImage())
                                .error(R.drawable.ic_baseline_cloud_download_24)
                                .into(img);
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