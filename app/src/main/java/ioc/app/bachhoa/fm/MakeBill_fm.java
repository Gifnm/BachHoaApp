package ioc.app.bachhoa.fm;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import ioc.app.bachhoa.Apdapter.ShellProductAdapter;
import ioc.app.bachhoa.R;
import ioc.app.bachhoa.api.APIService;
import ioc.app.bachhoa.model.Bill;
import ioc.app.bachhoa.model.BillDetail;
import ioc.app.bachhoa.model.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakeBill_fm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakeBill_fm extends Fragment {
    private static final String TAG = "MyActivity";
    CodeScanner codeScanner;
    CodeScannerView codeScannerView;
    View view;
    TextView resultData, totalAmountView;
    RecyclerView listProductView;
    ShellProductAdapter adapter = new ShellProductAdapter(getContext());
    List<BillDetail> billDetailList = new ArrayList<>();
    Bill bill = new Bill(System.currentTimeMillis() + "");
    float totalAmount = 0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MakeBill_fm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MakeBill_fm.
     */
    // TODO: Rename and change types and number of parameters
    public static MakeBill_fm newInstance(String param1, String param2) {
        MakeBill_fm fragment = new MakeBill_fm();
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
        view = inflater.inflate(R.layout.fragment_make_bill_fm, container, false);
        anhXa();
        setListProduct();
        return view;
    }

    private void anhXa() {
        totalAmountView = view.findViewById(R.id.mb_fm_totalAmount);
        listProductView = view.findViewById(R.id.mb_list_view);
        codeScannerView = view.findViewById(R.id.scanner_view);
        codeScannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();
            }
        });
        codeScanner = new CodeScanner(getContext(), codeScannerView);


        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
getProduct(result.getText());
//                        resultData.setText(result.getText());
                    }
                });
            }
        });

    }

    @Override
    public void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPermission();

    }

    private void checkPermission() {
        Dexter.withContext(getContext()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                codeScanner.startPreview();
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

    private void setListProduct() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        listProductView.setLayoutManager(layoutManager);
        listProductView.setAdapter(adapter);
    }

    private void addToListProduct(BillDetail billDetail) {
        billDetail.setBill(bill);
        totalAmount += billDetail.getProduct().getPrice();
        totalAmountView.setText(totalAmount+"");
        if (billDetailList.isEmpty()) {

            billDetailList.add(billDetail);
            adapter.setData(billDetailList);
            return;

        } else {
            for (BillDetail billDetail1 : billDetailList) {
                if (billDetail.getProduct().getProductID().equals(billDetail1.getProduct().getProductID())) {

                    billDetail1.setQuantity(billDetail1.getQuantity() + 1);
                    billDetail1.setTotalAmount(billDetail.countTotalAmount());
                    billDetail = billDetail1;
                    billDetailList.set(billDetailList.indexOf(billDetail1),billDetail);
                    adapter.setData(billDetailList);
                    return;
                }
            }

            billDetailList.add(billDetail);
            totalAmountView.setText(totalAmountView + "");

            adapter.setData(billDetailList);

        }

    }
private void getProduct(String id){
    APIService.apiService.getProductForBill(id).enqueue(new Callback<BillDetail>() {
        @Override
        public void onResponse(Call<BillDetail> call, Response<BillDetail> response) {
            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT);
            addToListProduct(response.body());
        }

        @Override
        public void onFailure(Call<BillDetail> call, Throwable t) {
            Log.d(TAG, "Đây là một thông điệp log DEBUG2.");
            Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
        }
    });

}
}